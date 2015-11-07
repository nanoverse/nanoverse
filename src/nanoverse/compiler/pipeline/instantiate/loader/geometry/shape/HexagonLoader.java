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

package nanoverse.compiler.pipeline.instantiate.loader.geometry.shape;

import nanoverse.compiler.pipeline.instantiate.factory.geometry.shape.HexagonFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.geometry.lattice.Lattice;
import nanoverse.runtime.geometry.shape.*;

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

    public Shape instantiate(Lattice lattice, GeneralParameters p) {
        return instantiate(null, lattice, p);
    }

    @Override
    public Shape instantiate(MapObjectNode node, Lattice lattice, GeneralParameters p) {
        factory.setLattice(lattice);

        int radius = interpolator.radius(node, p.getRandom());
        factory.setRadius(radius);

        return factory.build();
    }
}
