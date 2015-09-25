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

package nanoverse.compiler.pipeline.instantiate.loader.agent.action;

import nanoverse.runtime.agent.action.*;
import nanoverse.runtime.agent.targets.TargetDescriptor;
import nanoverse.compiler.pipeline.instantiate.factory.agent.action.CloneToFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 8/3/2015.
 */
public class CloneToLoader extends ActionLoader<CloneToDescriptor> {

    private final CloneToFactory factory;
    private final CloneToInterpolator interpolator;

    public CloneToLoader() {
        factory = new CloneToFactory();
        interpolator = new CloneToInterpolator();
    }

    public CloneToLoader(CloneToFactory factory,
                         CloneToInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public CloneToDescriptor instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        factory.setRandom(p.getRandom());
        factory.setLayerManager(lm);

        boolean noReplace = interpolator.noReplacement(node, p.getRandom());
        factory.setNoReplace(noReplace);

        IntegerArgument selfHighlight = interpolator.selfHighlight(node, p.getRandom());
        factory.setSelfChannel(selfHighlight);

        IntegerArgument targetHighlight = interpolator.targetHighlight(node, p.getRandom());
        factory.setTargetChannel(targetHighlight);

        TargetDescriptor target = interpolator.target(node, lm, p);
        factory.setTargetDescriptor(target);

        return factory.build();
    }
}
