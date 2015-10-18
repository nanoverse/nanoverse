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

import java.util.function.*;
import java.util.stream.Stream;

/**
 * Flexible cell class capable of performing arbitrary
 * bottom-up (nanoverse.runtime.agent-driven) behaviors for nanoverse.runtime.agent-based
 * modeling.
 * <p>
 * Created by David B Borenstein on 1/25/14.
 */
public class Agent {

    private final CallbackManager callbackManager;
    private final AgentContinuumManager reactionManager;
    private final Supplier<Agent> supplier;

    private BehaviorDispatcher dispatcher;
    private String name;

    @Deprecated
    /**
     * This constructor exists to maintain legacy (pre-Mockito) mocks.
     */
    public Agent() {
        callbackManager = null;
        reactionManager = null;
        supplier = null;
    }

    /**
     * Main constructor
     */
    public Agent(LayerManager layerManager, String name, Supplier<Agent> supplier) throws HaltCondition {
        this.name = name;
        callbackManager = new CallbackManager(this, layerManager);
        this.supplier = supplier;

        Supplier<Coordinate> locator = () -> callbackManager.getMyLocation();
        Function<String, ContinuumAgentLinker> layerResolver =
            id -> layerManager.getContinuumLayer(id).getLinker();

        reactionManager = new AgentContinuumManager(this, locator, layerResolver);
    }

    /**
     * Constructor for testing
     */
    public Agent(String name, CallbackManager callbackManager, AgentContinuumManager reactionManager, Supplier<Agent> supplier) {
        this.name = name;
        this.callbackManager = callbackManager;
        this.reactionManager = reactionManager;
        this.supplier = supplier;
    }

    public Agent copy() throws HaltCondition {
        Agent child = supplier.get();
        child.setName(name);
        BehaviorDispatcher childDispatcher = dispatcher.clone(child);
        child.setDispatcher(childDispatcher);
        return child;
    }

    public void trigger(String behaviorName, Coordinate caller) throws HaltCondition {
        dispatcher.trigger(behaviorName, caller);
    }

    public void die() {
        reactionManager.removeFromAll();
        callbackManager.die();
    }

    public void setDispatcher(BehaviorDispatcher dispatcher) {
        this.dispatcher = dispatcher;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
