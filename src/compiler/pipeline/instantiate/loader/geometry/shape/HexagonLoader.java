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

package compiler.pipeline.instantiate.loader.geometry.shape;

import compiler.pipeline.instantiate.factory.geometry.shape.HexagonFactory;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import geometry.lattice.Lattice;
import geometry.shape.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by dbborens on 8/4/2015.
 */
public class HexagonLoader extends ShapeLoader<Hexagon> {
    private final HexagonFactory factory;
    private final HexagonInterpolator interpolator;

    public HexagonLoader() {
        factory = new HexagonFactory();
        interpolator = new HexagonInterpolator();
    }

    public HexagonLoader(HexagonFactory factory,
                         HexagonInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public Shape instantiate(MapObjectNode node, Lattice lattice, GeneralParameters p) {
        factory.setLattice(lattice);

        int radius = interpolator.radius(node, p.getRandom());
        factory.setRadius(radius);

        return factory.build();
    }

    public Shape instantiate(Lattice lattice, GeneralParameters p) {
        return instantiate(null, lattice, p);
    }
}
