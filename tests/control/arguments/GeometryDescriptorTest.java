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

package control.arguments;

import geometry.Geometry;
import geometry.boundaries.*;
import geometry.lattice.*;
import geometry.shape.*;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.*;

public class GeometryDescriptorTest extends LegacyTest {

    private Lattice lattice;
    private Shape shape;
    private GeometryDescriptor query;

    @Before
    public void setUp() throws Exception {
        lattice = new LinearLattice();
        shape = new Line(lattice, 10);
        query = new GeometryDescriptor(lattice, shape);
    }

    @Test
    public void testEquals() throws Exception {
        Lattice otherLattice = new LinearLattice();
        Shape otherShape = new Line(otherLattice, 10);
        GeometryDescriptor otherDescriptor = new GeometryDescriptor(otherLattice, otherShape);
        assertEquals(query, otherDescriptor);
        assertFalse(query == otherDescriptor);
    }

    @Test
    public void testMake() throws Exception {
        Boundary boundary = new Arena(shape, lattice);

        Geometry expected = new Geometry(lattice, shape, boundary);
        Geometry actual = query.make(boundary);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetLattice() throws Exception {
        assertTrue(lattice == query.getLattice());
    }

    @Test
    public void testGetShape() throws Exception {
        assertTrue(shape == query.getShape());
    }
}