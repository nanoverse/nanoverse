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

package factory.control.arguments;

import control.arguments.GeometryDescriptor;
import factory.geometry.lattice.LatticeFactory;
import factory.geometry.shape.ShapeFactory;
import geometry.lattice.Lattice;
import geometry.shape.Shape;
import org.dom4j.Element;

/**
 * Created by dbborens on 11/27/14.
 */
public abstract class GeometryDescriptorFactory {

    public static GeometryDescriptor instantiate(Element root) {
        Lattice lattice = makeLattice(root);
        Shape shape = makeShape(root, lattice);
        GeometryDescriptor geometryDescriptor = new GeometryDescriptor(lattice, shape);
        return geometryDescriptor;
    }

    private static Shape makeShape(Element root, Lattice lattice) {
        Element shapeElem = root.element("shape");
        Shape shape = ShapeFactory.instantiate(shapeElem, lattice);
        return shape;
    }

    private static Lattice makeLattice(Element root) {
        Element latticeElem = root.element("lattice");
        Lattice lattice = LatticeFactory.instantiate(latticeElem);
        return lattice;
    }
}
