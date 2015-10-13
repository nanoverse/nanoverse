/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein and the
 * Trustees of Princeton University.
 *
 * This file is part of the Nanoverse simulation framework
 * (patent pending).
 *
 * This program is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Affero General
 * Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package nanoverse.runtime.agent;

import nanoverse.runtime.agent.control.BehaviorDispatcher;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.continuum.*;
import nanoverse.runtime.structural.utilities.EpsilonUtil;

import java.util.function.*;
import java.util.stream.Stream;

/**
 * Flexible cell class capable of performing arbitrary
 * bottom-up (nanoverse.runtime.agent-driven) behaviors for nanoverse.runtime.agent-based
 * modeling.
 * <p>
 * Created by David B Borenstein on 1/25/14.
 */
public class BehaviorAgent extends AbstractAgent {

    // State
    private int considerCount;
    private double nextHealth;
    private double threshold;

    // Helpers
    private BehaviorDispatcher dispatcher;
    private CallbackManager callbackManager;
    private AgentContinuumManager reactionManager;

    private Supplier<BehaviorAgent> supplier;

    // Default constructor for testing
    @Deprecated
    public BehaviorAgent() {
    }

    public BehaviorAgent(LayerManager layerManager, int state, double initialHealth, double threshold, Supplier<BehaviorAgent> supplier) throws HaltCondition {
        this.threshold = threshold;
        callbackManager = new CallbackManager(this, layerManager);
        this.supplier = supplier;

        setState(state);

        // We use the superclass setHealth here so it doesn't try to update
        // the location, as the cell is usually created before being placed.
        setHealth(initialHealth);

        considerCount = 0;

        Supplier<Coordinate> locator = () -> callbackManager.getMyLocation();
        Function<String, ContinuumAgentLinker> layerResolver =
            id -> layerManager.getContinuumLayer(id).getLinker();

        reactionManager = new AgentContinuumManager(this, locator, layerResolver);
    }

    @Override
    public void setHealth(double health) throws HaltCondition {
        super.setHealth(health);
        checkDivisibility();
    }

    @Override
    protected void setDivisible(boolean divisible) throws HaltCondition {
        super.setDivisible(divisible);
        callbackManager.refreshDivisibility();
    }

    @Override
    public int consider() {
        considerCount++;
        return considerCount;
    }

    @Override
    public void apply() {
        considerCount = 0;
    }

    @Override
    public AbstractAgent divide() throws HaltCondition {
        if (!isDivisible()) {
            throw new IllegalStateException("Attempted to divide non-divisible cell.");
        }

        BehaviorAgent daughter = supplier.get();
        daughter.setHealth(daughter.getHealth() / 2.0);
        double halfHealth = getHealth() / 2.0D;
        setHealth(halfHealth);
        daughter.setDispatcher(dispatcher.clone(daughter));
        checkDivisibility();
        return daughter;
    }

    @Override
    public BehaviorAgent clone(int childState) throws HaltCondition {
        double health = getHealth();

        BehaviorAgent child = supplier.get();
        child.setHealth(health);
        child.considerCount = considerCount;
        child.nextHealth = nextHealth;
        child.setDispatcher(dispatcher.clone(child));
        child.setDivisible(isDivisible());

        return child;
    }

    @Override
    public double getProduction(String solute) {
        throw new UnsupportedOperationException("BehaviorAgent does not yet support diffusible solutes.");
    }

    @Override
    public void adjustHealth(double delta) throws HaltCondition {
        double current = getHealth();
        double next = current + delta;
        setHealth(next);
    }

    @Override
    public void trigger(String behaviorName, Coordinate caller) throws HaltCondition {
        dispatcher.trigger(behaviorName, caller);
    }

    @Override
    public void die() {
        reactionManager.removeFromAll();
        callbackManager.die();
    }

    public void setDispatcher(BehaviorDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    private void checkDivisibility() throws HaltCondition {
        //System.out.println("   " + getHealth() + " -- " + threshold);
        if (getHealth() > threshold) {
            setDivisible(true);
        } else {
            setDivisible(false);
        }
    }

    /**
     * A BehaviorAgent is equal to another Object if and only if:
     * - The other Object is a BehaviorAgent.
     * - The other Object has a division threshold equal to this one.
     * - The other Object has a state ID equal to this one.
     * - The other Object has a dispatcher that reports equality
     * with this cell's dispatcher.
     *
     * @see nanoverse.runtime.agent.control.BehaviorDispatcher
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof BehaviorAgent)) {
            return false;
        }

        BehaviorAgent other = (BehaviorAgent) obj;

        if (other.getState() != this.getState()) {
            return false;
        }

        if (!EpsilonUtil.epsilonEquals(this.threshold, other.threshold)) {
            return false;
        }

        return other.dispatcher.equals(this.dispatcher);
    }

    public double getThreshold() {
        return threshold;
    }

    public Stream<String> getReactionIds() {
        return reactionManager.getReactionIds();
    }

    public Stream<String> getBehaviorNames() {
        return dispatcher.getBehaviorNames();
    }

    public void load(Reaction reaction) {
        reactionManager.schedule(reaction);
    }
}
