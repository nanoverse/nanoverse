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
public class Agent extends AbstractAgent {

    // Helpers
    private BehaviorDispatcher dispatcher;
    private CallbackManager callbackManager;
    private AgentContinuumManager reactionManager;

    private Supplier<Agent> supplier;

    // Default constructor for testing
    @Deprecated
    public Agent() {
    }

    public Agent(LayerManager layerManager, String name, Supplier<Agent> supplier) throws HaltCondition {
        setName(name);
        callbackManager = new CallbackManager(this, layerManager);
        this.supplier = supplier;

        Supplier<Coordinate> locator = () -> callbackManager.getMyLocation();
        Function<String, ContinuumAgentLinker> layerResolver =
            id -> layerManager.getContinuumLayer(id).getLinker();

        reactionManager = new AgentContinuumManager(this, locator, layerResolver);
    }


    @Override
    public AbstractAgent divide() throws HaltCondition {

        Agent daughter = supplier.get();
        daughter.setDispatcher(dispatcher.clone(daughter));
        daughter.setName(getName());
        return daughter;
    }

    @Override
    public Agent clone(String childName) throws HaltCondition {
        Agent child = supplier.get();
        child.setName(childName);
        child.setDispatcher(dispatcher.clone(child));
        return child;
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

        if (!(obj instanceof Agent)) {
            return false;
        }

        Agent other = (Agent) obj;

        if (!other.getName().equals(getName())) {
            return false;
        }

        return other.dispatcher.equals(this.dispatcher);
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
