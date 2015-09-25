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

package nanoverse.runtime.geometry.boundaries;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;
import org.junit.*;
import test.TestBase;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Integration test of the TetrisBoundary. (Setting this
 * up as a true mock would be needlessly difficult.)
 */
public class TetrisBoundaryTest extends TestBase {

    private Shape shape;
    private Lattice lattice;
    private TetrisBoundary query;

    @Before
    public void init() {
        lattice = new RectangularLattice();
        shape = new Rectangle(lattice, 2, 2);
        query = new TetrisBoundary(shape, lattice);
    }

    @Test(expected = IllegalArgumentException.class)
    public void d1Throws() {
        Shape shape = mock(Shape.class);
        Lattice lattice = mock(Lattice.class);
        when(lattice.getDimensionality()).thenReturn(1);
        new TetrisBoundary(shape, lattice);
    }

    @Test(expected = IllegalArgumentException.class)
    public void d3Throws() {
        Shape shape = mock(Shape.class);
        Lattice lattice = mock(Lattice.class);
        when(lattice.getDimensionality()).thenReturn(3);
        new TetrisBoundary(shape, lattice);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nonRectangleThrows() {
        Shape shape = mock(Hexagon.class);
        Lattice lattice = mock(Lattice.class);
        when(lattice.getDimensionality()).thenReturn(2);
        new TetrisBoundary(shape, lattice);
    }

    @Test
    public void rightOverboundWraps() {
        Coordinate input = new Coordinate2D(2, 0, 0);
        Coordinate expected = new Coordinate2D(0, 0, Flags.BOUNDARY_APPLIED);
        doTest(input, expected);
    }

    private void doTest(Coordinate input, Coordinate expected) {
        Coordinate actual = query.apply(input);
        assertEquals(expected, actual);
    }

    @Test
    public void leftOverboundWraps() {
        Coordinate input = new Coordinate2D(-1, 0, 0);
        Coordinate expected = new Coordinate2D(1, 0, Flags.BOUNDARY_APPLIED);
        doTest(input, expected);
    }

    @Test
    public void bottomOverboundIsNull() {
        Coordinate input = new Coordinate2D(0, -1, 0);
        doNullTest(input);
    }

    private void doNullTest(Coordinate input) {
        Coordinate actual = query.apply(input);
        assertNull(actual);
    }

    @Test
    public void topOverboundEndOfWorld() {
        Coordinate input = new Coordinate2D(0, 2, 0);
        Coordinate expected = new Coordinate2D(0, 2, Flags.BOUNDARY_APPLIED | Flags.END_OF_WORLD);
        doTest(input, expected);
    }

    @Test
    public void lowerLeftIsNull() {
        Coordinate input = new Coordinate2D(-1, -1, 0);
        doNullTest(input);
    }

    @Test
    public void lowerRightIsNull() {
        Coordinate input = new Coordinate2D(2, -1, 0);
        doNullTest(input);
    }

    @Test
    public void upperLeftWrappedAndEndOfWorld() {
        Coordinate input = new Coordinate2D(-1, 2, 0);
        Coordinate expected = new Coordinate2D(1, 2, Flags.BOUNDARY_APPLIED | Flags.END_OF_WORLD);
        doTest(input, expected);
    }

    @Test
    public void upperRightWrappedAndEndOfWorld() {
        Coordinate input = new Coordinate2D(2, 2, 0);
        Coordinate expected = new Coordinate2D(0, 2, Flags.BOUNDARY_APPLIED | Flags.END_OF_WORLD);
        doTest(input, expected);
    }

    @Test
    public void inBoundsDoesNothing() {
        Coordinate input = new Coordinate2D(0, 0, 0);
        Coordinate expected = new Coordinate2D(0, 0, 0);
        doTest(input, expected);
    }

    @Test
    public void itCanCloneItself() {
        Lattice lattice = new RectangularLattice();
        Shape scaledShape = new Rectangle(lattice, 4, 4);
        Boundary clone = query.clone(scaledShape, lattice);

        // Boundaries are equal based on their class, not their dependencies
        assertEquals(clone, query);
        assertFalse(clone == query);
    }
}