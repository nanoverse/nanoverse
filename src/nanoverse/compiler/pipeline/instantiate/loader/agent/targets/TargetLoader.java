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

package nanoverse.compiler.pipeline.instantiate.loader.agent.targets;

import nanoverse.compiler.pipeline.instantiate.factory.agent.targets.TargetFactory;
import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.agent.targets.TargetDescriptor;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.discrete.filter.Filter;

/**
 * Created by dbborens on 8/4/2015.
 */
public abstract class TargetLoader<T extends TargetDescriptor> extends Loader<T> {

    private final TargetFactory<T> factory;
    private final TargetInterpolator interpolator;

    protected TargetLoader(TargetFactory<T> factory,
                           TargetInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    public T instantiate(MapObjectNode node,
                         LayerManager lm,
                         GeneralParameters p) {

        factory.setLayerManager(lm);
        factory.setRandom(p.getRandom());

        Filter filter = interpolator.filter(node, lm, p);
        factory.setFilter(filter);

        return factory.build();
    }
}
