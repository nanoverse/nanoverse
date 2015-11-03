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

package nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.filter;

import nanoverse.compiler.pipeline.instantiate.factory.processes.discrete.filter.SampleFilterFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.discrete.filter.SampleFilter;

/**
 * Created by dbborens on 8/24/2015.
 */
public class SampleFilterLoader extends FilterLoader<SampleFilter> {
    private final SampleFilterFactory factory;
    private final SampleFilterInterpolator interpolator;

    public SampleFilterLoader() {
        factory = new SampleFilterFactory();
        interpolator = new SampleFilterInterpolator();
    }

    public SampleFilterLoader(SampleFilterFactory factory,
                              SampleFilterInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public SampleFilter instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        factory.setRandom(p.getRandom());

        int maximum = interpolator.maximum(node, p.getRandom());
        factory.setMaximum(maximum);

        return factory.build();
    }
}
