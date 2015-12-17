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

import nanoverse.compiler.pipeline.instantiate.factory.processes.continuum.CompositeContinuumProcessFactory;
import nanoverse.compiler.pipeline.instantiate.loader.processes.ProcessLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.continuum.ContinuumLayerScheduler;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.runtime.processes.continuum.*;

import java.util.stream.Stream;

/**
 * Created by dbborens on 12/17/2015.
 */
public class CompositeContinuumProcessLoader extends ProcessLoader<CompositeContinuumProcess> {
    private final CompositeContinuumProcessFactory factory;
    private final CompositeContinuumProcessInterpolator interpolator;

    public CompositeContinuumProcessLoader() {
        factory = new CompositeContinuumProcessFactory();
        interpolator = new CompositeContinuumProcessInterpolator();
    }

    public CompositeContinuumProcessLoader(CompositeContinuumProcessFactory factory,
                                           CompositeContinuumProcessInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    public CompositeContinuumProcess instantiate(LayerManager lm, GeneralParameters p) {
        return instantiate(null, lm, p);
    }

    public CompositeContinuumProcess instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {

        Stream<ContinuumProcess> processes = interpolator.processes(node, lm, p);
        factory.setChildren(processes);

        BaseProcessArguments arguments = interpolator.arguments(node, lm, p);
        factory.setArguments(arguments);

        String layer = interpolator.layer(node);
        ContinuumLayerScheduler scheduler = interpolator.scheduler(lm, layer);
        factory.setScheduler(scheduler);
        return factory.build();
    }


}
