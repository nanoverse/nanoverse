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

package nanoverse.compiler.pipeline.instantiate.loader.processes.continuum;

import nanoverse.compiler.pipeline.instantiate.factory.processes.continuum.DiffusionProcessFactory;
import nanoverse.compiler.pipeline.instantiate.loader.processes.ProcessLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.runtime.processes.continuum.*;
import no.uib.cipr.matrix.sparse.CompDiagMatrix;

import java.util.function.Consumer;

/**
 * Created by dbborens on 8/3/2015.
 */
public class DiffusionProcessLoader extends ProcessLoader<DiffusionProcess> {
    private final DiffusionProcessFactory factory;
    private final DiffusionProcessInterpolator interpolator;

    public DiffusionProcessLoader() {
        factory = new DiffusionProcessFactory();
        interpolator = new DiffusionProcessInterpolator();
    }

    public DiffusionProcessLoader(DiffusionProcessFactory factory,
                                  DiffusionProcessInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public DiffusionProcess instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        BaseProcessArguments arguments = interpolator.arguments(node, lm, p);
        factory.setArguments(arguments);

        String layer = interpolator.layer(node);
        double constant = interpolator.constant(node, p.getRandom());
        DiffusionOperator operator = interpolator.operator(layer, constant, lm);
        factory.setOperator(operator);

        Consumer<CompDiagMatrix> target = interpolator.target(layer, lm);
        factory.setTarget(target);

        return factory.build();
    }
}
