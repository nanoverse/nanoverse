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

package nanoverse.compiler.pipeline.instantiate.loader.agent;

import nanoverse.compiler.pipeline.instantiate.factory.control.arguments.AgentDescriptorFactory;
import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.agent.action.ActionDescriptor;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.continuum.Reaction;

import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by dbborens on 8/3/2015.
 */
public class AgentDescriptorLoader extends Loader<AgentDescriptor> {

    private final AgentDescriptorFactory factory;
    private final AgentDescriptorInterpolator interpolator;

    public AgentDescriptorLoader() {
        factory = new AgentDescriptorFactory();
        interpolator = new AgentDescriptorInterpolator();
    }

    public AgentDescriptorLoader(AgentDescriptorFactory factory,
                                 AgentDescriptorInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    public AgentDescriptor instantiate(LayerManager lm, GeneralParameters p) {
        return instantiate(null, lm, p);
    }

    public AgentDescriptor instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        factory.setLayerManager(lm);

        Map<String, ActionDescriptor> behaviors = interpolator.behaviors(node, lm, p);
        factory.setBehaviorDescriptors(behaviors);

        String name = interpolator.name(node);
        factory.setName(name);
        Stream<Reaction> reactions = interpolator.reactions(node, p.getRandom());
        factory.setReactions(reactions);

        return factory.build();
    }
}
