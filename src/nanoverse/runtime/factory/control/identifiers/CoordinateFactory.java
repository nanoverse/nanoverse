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

package nanoverse.runtime.factory.control.identifiers;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.control.identifiers.Coordinate2D;
import nanoverse.runtime.control.identifiers.Coordinate3D;
import nanoverse.runtime.control.identifiers.Flags;
import nanoverse.runtime.geometry.Geometry;
import org.dom4j.Element;

/**
 * @author dbborens
 * @test CoordinateFactoryTest
 */
public abstract class CoordinateFactory {

    public static Coordinate instantiate(Element e) {
        // Coordinate or vector?
        int flags = processName(e);

        // All coordinates have x and y
        int x = Integer.valueOf(e.attribute("x").getValue());
        int y = Integer.valueOf(e.attribute("y").getValue());

        // Process flags, if any
        if (e.attribute("flags") != null) {
            flags |= Integer.valueOf(e.attribute("flags").getValue());
        }

        // If it has a z element, load 3D coordinate)
        if (e.attribute("z") != null) {
            int z = Integer.valueOf(e.attribute("z").getValue());
            Coordinate c = new Coordinate3D(x, y, z, flags);
            return c;

        } else {
            Coordinate c = new Coordinate2D(x, y, flags);
            return c;
        }
    }

    protected static int processName(Element e) {
        int flags;

        if (e.getName().equalsIgnoreCase("coordinate")) {
            flags = 0;

        } else if (e.getName().equalsIgnoreCase("vector")) {
            flags = Flags.VECTOR;

            // I kind of don't like having "displacement" defined as its own
            // "vector" flag, but I don't know how else to do it.
        } else if (e.getName().equalsIgnoreCase("displacement")) {
            flags = Flags.VECTOR;

            // "offset" tags are used to specify distances from the center
            // coordinate.
        } else if (e.getName().equalsIgnoreCase("offset")) {
            flags = Flags.VECTOR;

        } else {
            throw new IllegalArgumentException("Unrecognized coordinate tag '" +
                    e.getName() + "'.");
        }

        return flags;
    }

    public static Coordinate instantiate(Object o) {
        if (o instanceof Element) {
            Element e = (Element) o;
            return instantiate(e);
        } else {
            throw new IllegalArgumentException("Cannot build a coordinate from class "
                    + o.getClass().getSimpleName());
        }
    }

    public static Coordinate offset(Object o, Geometry geom) {
        Coordinate displacement;
        displacement = instantiate(o);
        Coordinate origin = geom.getCenter();
        return geom.rel2abs(origin, displacement, Geometry.APPLY_BOUNDARIES);
    }
}
