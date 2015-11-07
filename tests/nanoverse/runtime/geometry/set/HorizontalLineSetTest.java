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

package nanoverse.runtime.geometry.set;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;
import nanoverse.runtime.structural.NotYetImplementedException;
import org.junit.*;
import test.TestBase;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

public class HorizontalLineSetTest extends TestBase {

    private Coordinate start;

    @Before
    public void init() {
        start = new Coordinate2D(0, 0, 0);
    }

    @Test
    public void rectangularCase() throws Exception {
        Lattice lattice = new RectangularLattice();
        Coordinate[] expected = new Coordinate[]{
            new Coordinate2D(0, 0, 0),
            new Coordinate2D(1, 0, 0),
            new Coordinate2D(2, 0, 0)
        };
        doTest(lattice, expected);
    }

    private void doTest(Lattice lattice, Coordinate[] expectedArr) {
        doTestWithLength(lattice, expectedArr, 3);
    }

    private void doTestWithLength(Lattice lattice, Coordinate[] expectedArr, int length) {
        Shape shape = new Rectangle(lattice, 10, 10);
        Boundary boundary = new Absorbing(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        HorizontalLineSet actual = new HorizontalLineSet(geom, start, length);
        Set<Coordinate> expected = Arrays
            .asList(expectedArr)
            .stream()
            .collect(Collectors.toSet());

        assertCollectionsEqual(expected, actual);
    }

    @Test
    public void triangularCaseEven() throws Exception {
        Lattice lattice = new TriangularLattice();
        Coordinate[] expected = new Coordinate[]{
            new Coordinate2D(0, 0, 0),
            new Coordinate2D(1, 0, 0),
            new Coordinate2D(2, 1, 0)
        };
        doTest(lattice, expected);
    }

    @Test
    public void triangularCaseOdd() throws Exception {
        start = new Coordinate2D(1, 0, 0);
        Lattice lattice = new TriangularLattice();
        Coordinate[] expected = new Coordinate[]{
            new Coordinate2D(1, 0, 0),
            new Coordinate2D(2, 1, 0),
            new Coordinate2D(3, 1, 0)
        };
        doTest(lattice, expected);
    }

    @Test
    public void zeroLengthIsEmptySet() throws Exception {
        Lattice lattice = new RectangularLattice();
        Coordinate[] expected = new Coordinate[]{};
        doTestWithLength(lattice, expected, 0);
    }

    @Test(expected = NotYetImplementedException.class)
    public void geom1Dthrows() throws Exception {
        Geometry geom = mock(Geometry.class);
        when(geom.getDimensionality()).thenReturn(1);
        new HorizontalLineSet(geom, start, 3);
    }

    @Test(expected = NotYetImplementedException.class)
    public void geom3Dthrows() throws Exception {
        Geometry geom = mock(Geometry.class);
        when(geom.getDimensionality()).thenReturn(3);
        start = new Coordinate3D(0, 0, 0, 0);
        new HorizontalLineSet(geom, start, 3);
    }

    @Test(expected = NotYetImplementedException.class)
    public void negativeLengthThrows() throws Exception {
        Geometry geom = mock(Geometry.class);
        when(geom.getDimensionality()).thenReturn(2);
        new HorizontalLineSet(geom, start, -1);
    }
}