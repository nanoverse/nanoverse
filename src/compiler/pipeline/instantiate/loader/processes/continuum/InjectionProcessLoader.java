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

package compiler.pipeline.instantiate.loader.processes.continuum;

import compiler.pipeline.instantiate.factory.processes.continuum.InjectionProcessFactory;
import compiler.pipeline.instantiate.loader.processes.ProcessLoader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import control.arguments.DoubleArgument;
import geometry.set.CoordinateSet;
import layers.LayerManager;
import processes.BaseProcessArguments;
import processes.continuum.InjectionProcess;

/**
 * Created by dbborens on 8/3/2015.
 */
public class InjectionProcessLoader extends ProcessLoader<InjectionProcess> {
    private final InjectionProcessFactory factory;
    private final InjectionProcessInterpolator interpolator;

    public InjectionProcessLoader() {
        factory = new InjectionProcessFactory();
        interpolator = new InjectionProcessInterpolator();
    }

    public InjectionProcessLoader(InjectionProcessFactory factory,
                                  InjectionProcessInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public InjectionProcess instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        BaseProcessArguments base = interpolator.arguments(node, lm, p);
        factory.setArguments(base);

        CoordinateSet activeSites = interpolator.activeSites(node, lm, p);
        factory.setActiveSites(activeSites);

        String layer = interpolator.layer(node);
        factory.setLayerId(layer);

        DoubleArgument valueArg = interpolator.value(node, p.getRandom());
        factory.setValueArg(valueArg);

        return factory.build();
    }
}
