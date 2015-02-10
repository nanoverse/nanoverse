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

package factory.geometry.boundaries;

import control.arguments.GeometryDescriptor;
import geometry.boundaries.*;
import geometry.lattice.Lattice;
import geometry.shape.Shape;
import org.dom4j.Element;

/**
 * Created by dbborens on 7/31/14.
 */
public abstract class BoundaryFactory {
    public static Boundary instantiate(Element root, GeometryDescriptor geometryDescriptor) {

        Shape shape = geometryDescriptor.getShape();
        Lattice lattice = geometryDescriptor.getLattice();

        String className = root.element("class").getTextTrim();

        if (className.equalsIgnoreCase("Arena")) {
            return new Arena(shape, lattice);
        } else if (className.equalsIgnoreCase("PlaneRingHard")) {
            return new PlaneRingHard(shape, lattice);
        } else if (className.equalsIgnoreCase("PlaneRingReflecting")) {
            return new PlaneRingReflecting(shape, lattice);
        } else if (className.equalsIgnoreCase("Absorbing")) {
            return new Absorbing(shape, lattice);
        } else if (className.equalsIgnoreCase("periodic")) {
            return new Periodic(shape, lattice);
        } else if (className.equalsIgnoreCase("halt")) {
            return new HaltArena(shape, lattice);
        } else if (className.equalsIgnoreCase("tetris")) {
            return new TetrisBoundary(shape, lattice);
        } else {
            String msg = "Unrecognized boundary class '" +
                    className + "'.";
            throw new IllegalArgumentException(msg);
        }
    }
}
