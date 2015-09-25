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

package nanoverse.runtime.factory.geometry.shape;

import nanoverse.runtime.geometry.lattice.Lattice;
import nanoverse.runtime.geometry.shape.*;
import org.dom4j.Element;

/**
 * Created by dbborens on 7/31/14.
 */
public abstract class ShapeFactory {

    public static Shape instantiate(Element root, Lattice lattice) {

        String className = root.element("class").getTextTrim();

        if (className.equalsIgnoreCase("Line")) {
            return new Line(lattice, root);
        }
        if (className.equalsIgnoreCase("Rectangle")) {
            return new Rectangle(lattice, root);
        } else if (className.equalsIgnoreCase("Hexagon")) {
            return new Hexagon(lattice, root);
        } else if (className.equalsIgnoreCase("Cuboid")) {
            return new Cuboid(lattice, root);
        } else {
            String msg = "Unrecognized shape class '" +
                className + "'.";
            throw new IllegalArgumentException(msg);
        }

    }
}
