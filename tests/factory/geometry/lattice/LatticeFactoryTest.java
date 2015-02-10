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

package factory.geometry.lattice;//import junit.framework.TestCase;

import geometry.lattice.CubicLattice;
import geometry.lattice.LinearLattice;
import geometry.lattice.RectangularLattice;
import geometry.lattice.TriangularLattice;
import org.dom4j.Element;
import test.EslimeTestCase;

public class LatticeFactoryTest extends EslimeTestCase {

    private Element root;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        root = readXmlFile("factories/geometry/lattice/LatticeFactoryTest.xml");
    }

    public void testLinearCase() {
        doTest("linear-case", LinearLattice.class);
    }

    public void testRectangularCase() {
        doTest("rectangular-case", RectangularLattice.class);
    }

    public void testTriangularCase() {
        doTest("triangular-case", TriangularLattice.class);
    }

    public void testCubicCase() {
        doTest("cubic-case", CubicLattice.class);
    }

    private void doTest(String eName, Class expected) {
        Element e = root.element(eName);
        Class actual = LatticeFactory.instantiate(e).getClass();
        assertEquals(expected, actual);
    }
}