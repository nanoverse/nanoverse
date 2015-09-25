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

package nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.filter;

import nanoverse.compiler.pipeline.instantiate.factory.processes.discrete.filter.CellStateFilterFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.discrete.filter.CellStateFilter;

/**
 * Created by dbborens on 8/24/2015.
 */
public class CellStateFilterLoader extends FilterLoader<CellStateFilter> {

    private final CellStateFilterFactory factory;
    private final CellStateFilterInterpolator interpolator;

    public CellStateFilterLoader() {
        factory = new CellStateFilterFactory();
        interpolator = new CellStateFilterInterpolator();
    }

    public CellStateFilterLoader(CellStateFilterFactory factory,
                                 CellStateFilterInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public CellStateFilter instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        factory.setLayer(lm.getCellLayer());

        IntegerArgument state = interpolator.state(node, p.getRandom());
        factory.setToChoose(state);

        return factory.build();
    }
}
