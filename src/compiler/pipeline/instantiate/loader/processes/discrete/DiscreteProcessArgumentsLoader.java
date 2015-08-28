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

package compiler.pipeline.instantiate.loader.processes.discrete;

import compiler.pipeline.instantiate.factory.processes.discrete.DiscreteProcessArgumentsFactory;
import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import control.arguments.IntegerArgument;
import geometry.set.CoordinateSet;
import layers.LayerManager;
import processes.discrete.CellProcessArguments;

/**
 * Created by dbborens on 8/12/15.
 */
public class DiscreteProcessArgumentsLoader extends Loader<CellProcessArguments> {

    private final DiscreteProcessArgumentsFactory factory;
    private final DiscreteProcessArgumentsInterpolator interpolator;

    public DiscreteProcessArgumentsLoader() {
        factory = new DiscreteProcessArgumentsFactory();
        interpolator = new DiscreteProcessArgumentsInterpolator();
    }

    public DiscreteProcessArgumentsLoader(DiscreteProcessArgumentsFactory factory,
                                          DiscreteProcessArgumentsInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    public CellProcessArguments instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        CoordinateSet activeSites = interpolator.activeSites(node, lm, p);
        factory.setActiveSites(activeSites);

        IntegerArgument maxTargets = interpolator.maxTargets(node, p.getRandom());
        factory.setMaxTargets(maxTargets);

        return factory.build();
    }


}
