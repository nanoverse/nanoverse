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

package compiler.pipeline.instantiate.loader.processes;

import compiler.pipeline.instantiate.factory.processes.BaseProcessArgumentsFactory;
import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import control.arguments.IntegerArgument;
import layers.LayerManager;
import processes.BaseProcessArguments;

/**
 * Created by dbborens on 8/12/15.
 */
public class BaseProcessArgumentsLoader extends Loader<BaseProcessArguments> {

    private final BaseProcessArgumentsFactory factory;
    private final BaseProcessArgumentsInterpolator interpolator;

    public BaseProcessArgumentsLoader() {
        factory = new BaseProcessArgumentsFactory();
        interpolator = new BaseProcessArgumentsInterpolator();
    }

    public BaseProcessArgumentsLoader(BaseProcessArgumentsFactory factory,
                                      BaseProcessArgumentsInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    public BaseProcessArguments instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        factory.setLayerManager(lm);
        factory.setGeneralParameters(p);
        factory.setId(0);

        IntegerArgument period = interpolator.period(node, p.getRandom());
        factory.setPeriod(period);

        IntegerArgument start = interpolator.start(node, p.getRandom());
        factory.setStart(start);

        return factory.build();
    }
}
