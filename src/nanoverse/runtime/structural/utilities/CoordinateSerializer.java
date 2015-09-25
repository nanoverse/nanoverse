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

package nanoverse.runtime.structural.utilities;

import nanoverse.runtime.control.identifiers.*;
import org.dom4j.Element;
import org.dom4j.tree.BaseElement;

/**
 * @author dbborens
 * @test CoordinateSerializerTest
 */
public abstract class CoordinateSerializer {

    public static Element serialize(Coordinate coord) {
        String name;

        if (coord.hasFlag(Flags.VECTOR)) {
            name = "vector";
        } else {
            name = "coordinate";
        }

        return serialize(coord, name);
    }

    /**
     * Note that only some tag names are legal for deserialization.
     * Use caution when specifying custom tag names.
     *
     * @param coord
     * @param name
     * @return
     */
    public static Element serialize(Coordinate coord, String name) {
        Element e = new BaseElement(name);

        // All coordinates have these
        String x = Integer.toString(coord.x());
        String y = Integer.toString(coord.y());
        String f = Integer.toString(coord.flags());

        e.addAttribute("x", x);
        e.addAttribute("y", y);
        e.addAttribute("flags", f);

        if (!coord.hasFlag(Flags.PLANAR)) {
            String z = Integer.toString(coord.z());
            e.addAttribute("z", z);
        }

        return e;
    }
}
