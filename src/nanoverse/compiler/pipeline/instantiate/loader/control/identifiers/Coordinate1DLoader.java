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

package nanoverse.compiler.pipeline.instantiate.loader.control.identifiers;

import nanoverse.compiler.pipeline.instantiate.factory.control.identifiers.Coordinate1DFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.identifiers.Coordinate1D;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 8/12/15.
 */
public class Coordinate1DLoader extends CoordinateSubclassLoader<Coordinate1D> {

    private final Coordinate1DFactory factory;
    private final Coordinate1DInterpolator interpolator;

    public Coordinate1DLoader() {
        factory = new Coordinate1DFactory();
        interpolator = new Coordinate1DInterpolator();
    }

    public Coordinate1DLoader(Coordinate1DFactory factory,
                              Coordinate1DInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public Coordinate1D instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        int y = interpolator.y(node, p.getRandom());
        factory.setY(y);

        int flags = interpolator.flags(node, p.getRandom());
        factory.setFlags(flags);

        return factory.build();
    }
}
