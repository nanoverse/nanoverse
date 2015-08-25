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
import compiler.pipeline.instantiate.factory.agent.action.DieFactory;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import control.arguments.IntegerArgument;
import layers.LayerManager;

/**
 * Created by dbborens on 8/3/2015.
 */
public class DieLoader extends ActionLoader<DieDescriptor> {

    private final DieFactory factory;
    private final DieInterpolator interpolator;

    public DieLoader() {
        factory = new DieFactory();
        interpolator = new DieInterpolator();
    }

    public DieLoader(DieFactory factory,
                     DieInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public DieDescriptor instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        factory.setLayerManager(lm);

        IntegerArgument highlight = interpolator.highlight(node, p.getRandom());
        factory.setChannel(highlight);

        return factory.build();
    }
}
