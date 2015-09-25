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

package nanoverse.runtime.factory.geometry.lattice;

import nanoverse.runtime.geometry.lattice.*;
import org.dom4j.Element;

/**
 * Created by dbborens on 7/31/14.
 */
public abstract class LatticeFactory {

    public static Lattice instantiate(Element root) {
        String className = root.element("class").getTextTrim();

        if (className.equalsIgnoreCase("Linear")) {
            return new LinearLattice();
        } else if (className.equalsIgnoreCase("Rectangular")) {
            return new RectangularLattice();
        } else if (className.equalsIgnoreCase("Triangular")) {
            return new TriangularLattice();
        } else if (className.equalsIgnoreCase("Cubic")) {
            return new CubicLattice();
        } else {
            String msg = "Unrecognized lattice class '" +
                    className + "'.";
            throw new IllegalArgumentException(msg);
        }
    }
}
