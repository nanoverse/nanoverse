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

package cells;

import agent.control.BehaviorDispatcher;
import control.halt.HaltCondition;
import control.identifiers.Coordinate;
import layers.continuum.Reaction;
import layers.LayerManager;
import layers.continuum.ContinuumAgentLinker;
import structural.utilities.EpsilonUtil;

import java.util.HashSet;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Flexible cell class capable of performing arbitrary
 * bottom-up (agent-driven) behaviors for agent-based
 * modeling.
 * <p>
 * Created by David B Borenstein on 1/25/14.
 */
public class BehaviorCell extends Cell {

    // State
    private int considerCount;
    private double nextHealth;
    private double threshold;

    // Helpers
    private BehaviorDispatcher dispatcher;
    private CallbackManager callbackManager;
    private AgentContinuumManager reactionManager;

    private Supplier<BehaviorCell> supplier;

    // Default constructor for testing
    @Deprecated
    public BehaviorCell() {
    }

    public BehaviorCell(LayerManager layerManager, int state, double initialHealth, double threshold, Supplier<BehaviorCell> supplier) throws HaltCondition {
        this.threshold = threshold;
        callbackManager = new CallbackManager(this, layerManager);
        this.supplier = supplier;

        setState(state);

        // We use the superclass setHealth here so it doesn't try to update
        // the location, as the cell is usually created before being placed.
        setHealth(initialHealth);

        considerCount = 0;

        Supplier<Coordinate> locator = () -> callbackManager.getMyLocation();
        Function<String, ContinuumAgentLinker> retrieveLinker =
                id -> layerManager.getContinuumLayer(id).getLinker();

        HashSet<Runnable> removers = new HashSet<>();
        RemoverIndex removerIndex = new RemoverIndex(removers);

        reactionManager = new AgentContinuumManager(this, removerIndex, locator, retrieveLinker);
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
    public Cell divide() throws HaltCondition {
        if (!isDivisible()) {
            throw new IllegalStateException("Attempted to divide non-divisible cell.");
        }

        BehaviorCell daughter = supplier.get();
        daughter.setHealth(daughter.getHealth() / 2.0);
        double halfHealth = getHealth() / 2.0D;
        setHealth(halfHealth);
        daughter.setDispatcher(dispatcher.clone(daughter));
        checkDivisibility();
        return daughter;
    }

    @Override
    public BehaviorCell clone(int childState) throws HaltCondition {
        double health = getHealth();

        BehaviorCell child = supplier.get();
        child.setHealth(health);
        child.considerCount = considerCount;
        child.nextHealth = nextHealth;
        child.setDispatcher(dispatcher.clone(child));
        child.setDivisible(isDivisible());

        return child;
    }

    @Override
    public double getProduction(String solute) {
        throw new UnsupportedOperationException("BehaviorCell does not yet support diffusible solutes.");
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

    @Override
    protected void setDivisible(boolean divisible) throws HaltCondition {
        super.setDivisible(divisible);
        callbackManager.refreshDivisibility();
    }

    private void checkDivisibility() throws HaltCondition {
        //System.out.println("   " + getHealth() + " -- " + threshold);
        if (getHealth() > threshold) {
            setDivisible(true);
        } else {
            setDivisible(false);
        }
    }

    public void setDispatcher(BehaviorDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void setHealth(double health) throws HaltCondition {
        super.setHealth(health);
        checkDivisibility();
    }

    /**
     * A BehaviorCell is equal to another Object if and only if:
     * - The other Object is a BehaviorCell.
     * - The other Object has a division threshold equal to this one.
     * - The other Object has a state ID equal to this one.
     * - The other Object has a dispatcher that reports equality
     * with this cell's dispatcher.
     *
     * @see agent.control.BehaviorDispatcher
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof BehaviorCell)) {
            return false;
        }

        BehaviorCell other = (BehaviorCell) obj;

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
