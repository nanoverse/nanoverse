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

package factory.geometry.boundaries;//import junit.framework.TestCase;

import control.arguments.GeometryDescriptor;
import geometry.boundaries.*;
import geometry.lattice.Lattice;
import geometry.lattice.RectangularLattice;
import geometry.shape.Rectangle;
import geometry.shape.Shape;
import org.dom4j.Element;
import test.EslimeTestCase;

public class BoundaryFactoryTest extends EslimeTestCase {

    private Element root;
    private GeometryDescriptor geometryDescriptor;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        root = readXmlFile("factories/geometry/boundary/BoundaryFactoryTest.xml");
        Lattice lattice = new RectangularLattice();
        Shape shape = new Rectangle(lattice, 1, 1);
        geometryDescriptor = new GeometryDescriptor(lattice, shape);
    }

    public void testArenaCase() {
        doTest("arena-case", Arena.class);
    }

    public void testPlaneRingHardCase() {
        doTest("plane-ring-hard-case", PlaneRingHard.class);
    }

    public void testPlaneRingReflectingCase() {
        doTest("plane-ring-reflecting-case", PlaneRingReflecting.class);
    }

    public void testAbsorbingCase() {
        doTest("absorbing-case", Absorbing.class);
    }

    public void testPeriodicCase() {
        doTest("periodic-case", Periodic.class);
    }

    public void testHaltCase() {
        doTest("halt-case", HaltArena.class);
    }

    public void testTetrisCase() {
        doTest("tetris-case", TetrisBoundary.class);
    }

    public void testReflectingTetrisCase() {
        doTest("reflecting-tetris-case", TetrisReflectingBoundary.class);
    }

    private void doTest(String eName, Class expected) {
        Element e = root.element(eName);
        Class actual = BoundaryFactory.instantiate(e, geometryDescriptor).getClass();
        assertEquals(expected, actual);
    }
}