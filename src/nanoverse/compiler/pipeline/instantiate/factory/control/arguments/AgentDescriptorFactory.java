/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package nanoverse.compiler.pipeline.instantiate.factory.control.arguments;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.agent.action.ActionDescriptor;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.continuum.Reaction;

import java.util.Map;
import java.util.stream.Stream;

public class AgentDescriptorFactory implements Factory<AgentDescriptor> {

    private final AgentDescriptorFactoryHelper helper;

    private LayerManager layerManager;
    private String name;
    private Stream<Reaction> reactions;
    private Map<String, ActionDescriptor> behaviorDescriptors;

    public AgentDescriptorFactory() {
        helper = new AgentDescriptorFactoryHelper();
    }

    public AgentDescriptorFactory(AgentDescriptorFactoryHelper helper) {
        this.helper = helper;
    }

    public void setLayerManager(LayerManager layerManager) {
        this.layerManager = layerManager;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReactions(Stream<Reaction> reactions) {
        this.reactions = reactions;
    }

    public void setBehaviorDescriptors(Map<String, ActionDescriptor> behaviorDescriptors) {
        this.behaviorDescriptors = behaviorDescriptors;
    }

    @Override
    public AgentDescriptor build() {
        return helper.build(layerManager, name, reactions, behaviorDescriptors);
    }
}