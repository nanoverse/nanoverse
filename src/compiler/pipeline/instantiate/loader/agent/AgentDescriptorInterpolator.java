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

package compiler.pipeline.instantiate.loader.agent;

import agent.action.*;
import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.instantiate.loader.layers.continuum.ReactionStreamLoader;
import compiler.pipeline.translate.nodes.*;
import control.GeneralParameters;
import control.arguments.*;
import layers.LayerManager;
import layers.continuum.Reaction;

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
        BehaviorMapLoader loader = (BehaviorMapLoader) load.getLoader(node, "behaviors", false);

        if (loader == null) {
            return defaults.behaviors();
        }

        DictionaryObjectNode cNode = (DictionaryObjectNode) node.getMember("behaviors");

        return loader.instantiate(cNode, lm, p);
    }

    public IntegerArgument clazz(MapObjectNode node, Random random) {
        return load.anIntegerArgument(node, "class", random, defaults::clazz);
    }

    public DoubleArgument initialHealth(MapObjectNode node, Random random) {
        return load.aDoubleArgument(node, "initialHealth", random, defaults::initialHealth);
    }
    public DoubleArgument threshold(MapObjectNode node, Random random) {
        return load.aDoubleArgument(node, "threshold", random, defaults::threshold);
    }

    public Stream<Reaction> reactions(MapObjectNode node, Random random) {
        ReactionStreamLoader loader = (ReactionStreamLoader) load.getLoader(node, "reactions", false);

        if (loader == null) {
            return defaults.reactions();
        }

        ListObjectNode cNode = (ListObjectNode) node.getMember("reactions");

        return loader.instantiate(cNode, random);
    }
}
