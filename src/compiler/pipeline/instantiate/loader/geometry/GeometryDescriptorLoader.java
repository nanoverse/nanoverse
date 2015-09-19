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

package compiler.pipeline.instantiate.loader.geometry;

import compiler.pipeline.instantiate.factory.geometry.GeometryDescriptorFactory;
import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.translate.nodes.*;
import control.arguments.GeometryDescriptor;
import geometry.lattice.Lattice;
import geometry.shape.Shape;

/**
 * Created by dbborens on 8/4/2015.
 */
public class GeometryDescriptorLoader extends Loader<GeometryDescriptor> {
    private final GeometryDescriptorFactory factory;
    private final GeometryDescriptorInterpolator interpolator;

    public GeometryDescriptorLoader() {
        factory = new GeometryDescriptorFactory();
        interpolator = new GeometryDescriptorInterpolator();
    }

    public GeometryDescriptorLoader(GeometryDescriptorFactory factory,
                                    GeometryDescriptorInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    public GeometryDescriptor instantiate(MapObjectNode node) {
        Lattice lattice = interpolator.lattice(node);
        factory.setLattice(lattice);

        Shape shape = interpolator.shape(node, lattice);
        factory.setShape(shape);

        return factory.build();
    }

    public GeometryDescriptor instantiate() {
        return instantiate(null);
    }
}
