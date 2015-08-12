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

package io.serialize;

import control.identifiers.Coordinate;
import control.identifiers.Coordinate2D;
import control.identifiers.Coordinate3D;
import control.identifiers.Flags;
import org.dom4j.Element;
import structural.utilities.CoordinateSerializer;
import test.EslimeTestCase;

public class CoordinateSerializerTest extends EslimeTestCase {

    public void testCoordinate() {
        Coordinate c2 = new Coordinate2D(1, 2, Flags.PLANAR);
        Coordinate c3 = new Coordinate3D(1, 2, 3, 4);

        Element e2 = CoordinateSerializer.serialize(c2);
        Element e3 = CoordinateSerializer.serialize(c3);

        doTest(c2, e2, "coordinate");
        doTest(c3, e3, "coordinate");
    }


    public void testVector() {
        Coordinate v2 = new Coordinate2D(1, 2, Flags.VECTOR | Flags.PLANAR);
        Coordinate v3 = new Coordinate3D(1, 2, 3, 4 | Flags.VECTOR);

        Element e2 = CoordinateSerializer.serialize(v2);
        Element e3 = CoordinateSerializer.serialize(v3);

        doTest(v2, e2, "vector");
        doTest(v3, e3, "vector");
    }

    /**
     * Test the ability to specify arbitrary tag names. Note that only
     * some custom tag names are legal for deserialization.
     */
    public void testCustom() {
        Coordinate v2 = new Coordinate2D(1, 2, Flags.VECTOR | Flags.PLANAR);
        Coordinate v3 = new Coordinate3D(1, 2, 3, 4 | Flags.VECTOR);

        Element e2 = CoordinateSerializer.serialize(v2, "displacement");
        Element e3 = CoordinateSerializer.serialize(v3, "displacement");

        doTest(v2, e2, "displacement");
        doTest(v3, e3, "displacement");
    }

    private void doTest(Coordinate c, Element e, String name) {
        assertEquals(Integer.toString(c.x()), e.attribute("x").getValue());
        assertEquals(Integer.toString(c.y()), e.attribute("y").getValue());
        assertEquals(Integer.toString(c.flags()), e.attribute("flags").getValue());

        if (!c.hasFlag(Flags.PLANAR)) {
            assertEquals(Integer.toString(c.z()), e.attribute("z").getValue());
        }

        assertEquals(name, e.getName());
    }
}
