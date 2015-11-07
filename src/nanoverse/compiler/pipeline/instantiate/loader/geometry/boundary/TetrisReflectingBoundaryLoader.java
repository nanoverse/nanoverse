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

package nanoverse.compiler.pipeline.instantiate.loader.geometry.boundary;

import nanoverse.compiler.pipeline.instantiate.factory.geometry.boundaries.TetrisReflectingBoundaryFactory;
import nanoverse.runtime.control.arguments.GeometryDescriptor;
import nanoverse.runtime.geometry.boundaries.Boundary;
import nanoverse.runtime.geometry.boundaries.TetrisReflectingBoundary;

/**
 * Created by dbborens on 8/4/2015.
 */
public class TetrisReflectingBoundaryLoader extends BoundaryLoader<TetrisReflectingBoundary> {
    private final TetrisReflectingBoundaryFactory factory;

    public TetrisReflectingBoundaryLoader() {
        factory = new TetrisReflectingBoundaryFactory();
    }

    public TetrisReflectingBoundaryLoader(TetrisReflectingBoundaryFactory factory) {
        this.factory = factory;
    }

    @Override
    public Boundary instantiate(GeometryDescriptor geom) {
        factory.setLattice(geom.getLattice());
        factory.setShape(geom.getShape());
        return factory.build();
    }
}
