/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package nanoverse.runtime.geometry.boundaries;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.control.identifiers.Coordinate2D;
import nanoverse.runtime.control.identifiers.Flags;
import nanoverse.runtime.geometry.lattice.Lattice;
import nanoverse.runtime.geometry.lattice.RectangularLattice;
import nanoverse.runtime.geometry.lattice.TriangularLattice;
import nanoverse.runtime.geometry.shape.Hexagon;
import nanoverse.runtime.geometry.shape.Rectangle;
import nanoverse.runtime.geometry.shape.Shape;
import org.junit.Before;
import org.junit.Test;
import test.TestBase;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created on 7/22/15.
 *
 * @author Daniel Greenidge
 */
public class TetrisReflectingRectFunctionalTest extends TestBase {

    private Shape shape;
    private Lattice lattice;
    private TetrisReflectingBoundary query;

    @Before
    public void init() {
        lattice = new RectangularLattice();
        shape = new Rectangle(lattice, 2, 2);
        query = new TetrisReflectingBoundary(shape, lattice);
    }

    @Test(expected = IllegalArgumentException.class)
    public void d1Throws() {
        Shape shape = mock(Rectangle.class);
        Lattice lattice = mock(RectangularLattice.class);
        when(lattice.getDimensionality()).thenReturn(1);
        new TetrisBoundary(shape, lattice);
    }

    @Test(expected = IllegalArgumentException.class)
    public void d3Throws() {
        Shape shape = mock(Rectangle.class);
        Lattice lattice = mock(RectangularLattice.class);
        when(lattice.getDimensionality()).thenReturn(3);
        new TetrisBoundary(shape, lattice);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nonRectangleThrows() {
        Shape shape = mock(Hexagon.class);
        Lattice lattice = mock(RectangularLattice.class);
        when(lattice.getDimensionality()).thenReturn(2);
        new TetrisReflectingBoundary(shape, lattice);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nonRectangularLatticeThrows() {
        Shape shape = mock(Rectangle.class);
        Lattice lattice = mock(TriangularLattice.class);
        when(lattice.getDimensionality()).thenReturn(2);
        new TetrisReflectingBoundary(shape, lattice);
    }

    @Test
    public void rightOverboundWraps() {
        Coordinate input = new Coordinate2D(2, 0, 0);
        Coordinate expected = new Coordinate2D(0, 0, 0);
        doTest(input, expected);

    }

    private void doTest(Coordinate input, Coordinate expected) {
        Coordinate actual = query.apply(input);
        assertEquals(expected, actual);
    }

    @Test
    public void leftOverBoundWraps() {
        Coordinate input = new Coordinate2D(-1, 0, 0);
        Coordinate expected = new Coordinate2D(1, 0, 0);
        doTest(input, expected);
    }

    @Test
    public void belowWorldReflects() {
        Coordinate input = new Coordinate2D(1, -2, 0);
        Coordinate expected = new Coordinate2D(1, 1, Flags.BOUNDARY_APPLIED);
        doTest(input, expected);
    }

    @Test
    public void aboveWorldIsNull() {
        Coordinate input = new Coordinate2D(1, 3, 0);
        doNullTest(input);
    }

    private void doNullTest(Coordinate input) {
        Coordinate actual = query.apply(input);
        assertNull(actual);
    }

    @Test
    public void inBoundsDoesNothing() {
        Coordinate input = new Coordinate2D(1, 1, 0);
        Coordinate expected = new Coordinate2D(1, 1, 0);
        doTest(input, expected);
    }

    @Test
    public void upperRightIsNull() {
        Coordinate input = new Coordinate2D(2, 2, 0);
        doNullTest(input);
    }

    @Test
    public void upperLeftIsNull() {
        Coordinate input = new Coordinate2D(-1, 2, 0);
        doNullTest(input);
    }

    @Test
    public void lowerLeftWrappedAndReflected() {
        Coordinate input = new Coordinate2D(-1, -1, 0);
        Coordinate expected = new Coordinate2D(1, 0, Flags.BOUNDARY_APPLIED);
        doTest(input, expected);
    }

    @Test
    public void lowerRightWrappedAndReflected() {
        Coordinate input = new Coordinate2D(2, -1, 0);
        Coordinate expected = new Coordinate2D(0, 0, Flags.BOUNDARY_APPLIED);
        doTest(input, expected);
    }

    @Test
    public void itCanCloneItself() {
        Shape scaledShape = new Rectangle(lattice, 4, 4);
        Boundary clone = query.clone(scaledShape, lattice);

        // Boundaries are equal based on their class, not their dependencies
        assertEquals(clone, query);
        assertFalse(clone == query);
    }
}