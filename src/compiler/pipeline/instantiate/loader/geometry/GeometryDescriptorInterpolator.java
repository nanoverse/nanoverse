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

import compiler.pipeline.instantiate.factory.geometry.GeometryDescriptorDefaults;
import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.instantiate.loader.geometry.lattice.LatticeLoader;
import compiler.pipeline.instantiate.loader.geometry.shape.ShapeLoader;
import compiler.pipeline.translate.nodes.*;
import control.arguments.GeometryDescriptor;
import geometry.lattice.Lattice;
import geometry.shape.Shape;

/**
 * Created by dbborens on 8/19/2015.
 */
public class GeometryDescriptorInterpolator {

    private final LoadHelper load;
    private final GeometryDescriptorDefaults defaults;

    public GeometryDescriptorInterpolator() {
        load = new LoadHelper();
        defaults = new GeometryDescriptorDefaults();
    }

    public GeometryDescriptorInterpolator(LoadHelper load, GeometryDescriptorDefaults defaults) {
        this.load = load;
        this.defaults = defaults;
    }

    public Lattice lattice(MapObjectNode node) {
        LatticeLoader loader = (LatticeLoader)
                load.getLoader(node, "lattice", false);

        if (loader == null) {
            return defaults.lattice();
        }

        Lattice lattice = loader.instantiate();
        return lattice;
    }

    public Shape shape(MapObjectNode node, Lattice lattice) {
        MapObjectNode childNode = (MapObjectNode) node.getMember("shape");
        ShapeLoader loader = (ShapeLoader)
                load.getLoader(node, "shape", false);

        if (loader == null) {
            return defaults.shape(lattice);
        }

        Shape shape = loader.instantiate(childNode, lattice);
        return shape;
    }
}
