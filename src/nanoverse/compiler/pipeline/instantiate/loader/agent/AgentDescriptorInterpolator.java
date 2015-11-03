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

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.instantiate.loader.layers.continuum.ReactionStreamLoader;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.runtime.agent.action.ActionDescriptor;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.continuum.Reaction;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by dbborens on 8/25/2015.
 */
public class AgentDescriptorInterpolator {

    private final LoadHelper load;
    private final AgentDescriptorDefaults defaults;

    public AgentDescriptorInterpolator() {
        load = new LoadHelper();
        defaults = new AgentDescriptorDefaults();
    }

    public AgentDescriptorInterpolator(LoadHelper load,
                                       AgentDescriptorDefaults defaults) {
        this.load = load;
        this.defaults = defaults;
    }

    public Map<String, ActionDescriptor> behaviors(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        if (node == null) {
            return defaults.behaviors();
        }

        BehaviorMapLoader loader = (BehaviorMapLoader) load.getLoader(node, "behaviors", false);

        if (loader == null) {
            return defaults.behaviors();
        }

        DictionaryObjectNode cNode = (DictionaryObjectNode) node.getMember("behaviors");

        return loader.instantiate(cNode, lm, p);
    }

    public Stream<Reaction> reactions(MapObjectNode node, Random random) {
        if (node == null) {
            return defaults.reactions();
        }

        ReactionStreamLoader loader = (ReactionStreamLoader) load.getLoader(node, "reactions", false);

        if (loader == null) {
            return defaults.reactions();
        }

        ListObjectNode cNode = (ListObjectNode) node.getMember("reactions");

        return loader.instantiate(cNode, random);
    }

    public String name(MapObjectNode node) {
        return load.aString(node, "name");
    }
}
