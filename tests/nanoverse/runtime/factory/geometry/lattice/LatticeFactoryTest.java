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

package nanoverse.runtime.factory.geometry.lattice;//import junit.framework.TestCase;

import nanoverse.runtime.geometry.lattice.*;
import org.dom4j.Element;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.assertEquals;

public class LatticeFactoryTest extends LegacyTest {

    private Element root;

    @Before
    public void setUp() throws Exception {
        root = readXmlFile("factories/geometry/lattice/LatticeFactoryTest.xml");
    }

    @Test
    public void testLinearCase() {
        doTest("linear-case", LinearLattice.class);
    }

    private void doTest(String eName, Class expected) {
        Element e = root.element(eName);
        Class actual = LatticeFactory.instantiate(e).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void testRectangularCase() {
        doTest("rectangular-case", RectangularLattice.class);
    }

    @Test
    public void testTriangularCase() {
        doTest("triangular-case", TriangularLattice.class);
    }

    @Test
    public void testCubicCase() {
        doTest("cubic-case", CubicLattice.class);
    }
}