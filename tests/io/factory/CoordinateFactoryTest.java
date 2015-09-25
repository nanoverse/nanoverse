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

package io.factory;

import control.identifiers.*;
import factory.control.identifiers.CoordinateFactory;
import geometry.*;
import org.dom4j.Element;
import org.dom4j.tree.BaseElement;
import org.junit.Test;
import test.LegacyTest;

import static org.junit.Assert.assertEquals;

public class CoordinateFactoryTest extends LegacyTest {

    @Test
    public void testInstantiateFromElement() {
        Element ce = new BaseElement("coordinate");
        Element ve = new BaseElement("vector");
        Element de = new BaseElement("displacement");

        addAttributes(ce, 1, 2, 3);
        addAttributes(ve, 2, 3, 4, 5);
        addAttributes(de, 4, 5, 6);

        doTest(ce, 1, 2, 3 | Flags.PLANAR);
        doTest(ve, 2, 3, 4, 5 | Flags.VECTOR);
        doTest(de, 4, 5, 6 | Flags.VECTOR | Flags.PLANAR);
    }

    private void doTest(Element o, int x, int y, int z, int flags) {
        Coordinate c = CoordinateFactory.instantiate(o);
        assertEquals(x, c.x());
        assertEquals(y, c.y());
        assertEquals(z, c.z());
        assertEquals(flags, c.flags());
    }

    private void doTest(Element e, int x, int y, int flags) {
        Coordinate c = CoordinateFactory.instantiate(e);
        assertEquals(x, c.x());
        assertEquals(y, c.y());
        assertEquals(flags, c.flags());
    }

    private void addAttributes(Element e, int x, int y, int flags) {
        e.addAttribute("x", Integer.toString(x));
        e.addAttribute("y", Integer.toString(y));
        e.addAttribute("flags", Integer.toString(flags));
    }

    private void addAttributes(Element e, int x, int y, int z, int flags) {
        addAttributes(e, x, y, flags);
        e.addAttribute("z", Integer.toString(z));
    }

    @Test
    public void testOffset2D() {
        Element o1 = new BaseElement("offset");
        Element o2 = new BaseElement("offset");

        MockGeometry geom = new SquareMockGeometry();
        geom.setCenter(new Coordinate2D(1, 2, 0));
        addAttributes(o1, 0, 0, 0);
        addAttributes(o2, 1, 0, 0);

        Coordinate actual, expected;

        expected = new Coordinate2D(1, 2, 0);
        actual = CoordinateFactory.offset(o1, geom);
        assertEquals(expected, actual);

        expected = new Coordinate2D(2, 2, 0);
        actual = CoordinateFactory.offset(o2, geom);
        assertEquals(expected, actual);
    }

    @Test
    public void testOffset3D() {
        Element o1 = new BaseElement("offset");
        Element o2 = new BaseElement("offset");

        MockGeometry geom = new CubicMockGeometry();
        geom.setCenter(new Coordinate3D(1, 2, 3, 0));
        addAttributes(o1, 0, 0, 0, 0);
        addAttributes(o2, 1, 0, 0, 0);

        Coordinate actual, expected;

        expected = new Coordinate3D(1, 2, 3, 0);
        actual = CoordinateFactory.offset(o1, geom);
        assertEquals(expected, actual);

        expected = new Coordinate3D(2, 2, 3, 0);
        actual = CoordinateFactory.offset(o2, geom);
        assertEquals(expected, actual);
    }

    @Test
    public void testInstantiateFromObject() {
        Element ce = new BaseElement("coordinate");
        Element ve = new BaseElement("vector");
        Element de = new BaseElement("displacement");

        addAttributes(ce, 1, 2, 3);
        addAttributes(ve, 2, 3, 4, 5);
        addAttributes(de, 4, 5, 6);

        doTest((Object) ce, 1, 2, 3 | Flags.PLANAR);
        doTest((Object) ve, 2, 3, 4, 5 | Flags.VECTOR);
        doTest((Object) de, 4, 5, 6 | Flags.PLANAR | Flags.VECTOR);
    }

    private void doTest(Object e, int x, int y, int z, int flags) {
        Coordinate c = CoordinateFactory.instantiate(e);
        assertEquals(x, c.x());
        assertEquals(y, c.y());
        assertEquals(z, c.z());
        assertEquals(flags, c.flags());
    }

    private void doTest(Object o, int x, int y, int flags) {
        Coordinate c = CoordinateFactory.instantiate(o);
        assertEquals(x, c.x());
        assertEquals(y, c.y());
        assertEquals(flags, c.flags());
    }

}
