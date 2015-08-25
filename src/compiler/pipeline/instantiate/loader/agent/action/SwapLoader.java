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

package compiler.pipeline.instantiate.loader.agent.action;

import agent.action.*;
import agent.targets.TargetDescriptor;
import compiler.pipeline.instantiate.factory.agent.action.SwapFactory;
import compiler.pipeline.instantiate.loader.agent.targets.TargetDefaults;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import control.arguments.IntegerArgument;
import layers.LayerManager;

/**
 * Created by dbborens on 8/3/2015.
 */
public class SwapLoader extends ActionLoader<SwapDescriptor> {

    private final SwapFactory factory;
    private final SwapInterpolator interpolator;

    public SwapLoader() {
        factory = new SwapFactory();
        interpolator = new SwapInterpolator();
    }

    public SwapLoader(SwapFactory factory,
                      SwapInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public SwapDescriptor instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        factory.setLayerManager(lm);

        TargetDescriptor target = interpolator.target(node, lm, p);
        factory.setRuleDescriptor(target);

        IntegerArgument selfHighlight = interpolator.selfHighlight(node, p.getRandom());
        factory.setSelfChannel(selfHighlight);

        IntegerArgument targetHighlight = interpolator.targetHighlight(node, p.getRandom());
        factory.setTargetChannel(targetHighlight);

        return factory.build();
    }
}
