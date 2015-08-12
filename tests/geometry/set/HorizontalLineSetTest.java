/*
 *  Copyright (c) 2014 David Bruce Borenstein and the Trustees of
 *  Princeton University. All rights reserved.
 */

package geometry.set;

import control.identifiers.Coordinate;
import control.identifiers.Coordinate2D;
import control.identifiers.Coordinate3D;
import geometry.Geometry;
import geometry.boundaries.Absorbing;
import geometry.boundaries.Boundary;
import geometry.lattice.Lattice;
import geometry.lattice.RectangularLattice;
import geometry.lattice.TriangularLattice;
import geometry.shape.Rectangle;
import geometry.shape.Shape;
import org.junit.Before;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import test.TestBase;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    @Test(expected = NotImplementedException.class)
    public void geom1Dthrows() throws Exception {
        Geometry geom = mock(Geometry.class);
        when(geom.getDimensionality()).thenReturn(1);
        new HorizontalLineSet(geom, start, 3);
    }

    @Test(expected = NotImplementedException.class)
    public void geom3Dthrows() throws Exception {
        Geometry geom = mock(Geometry.class);
        when(geom.getDimensionality()).thenReturn(3);
        start = new Coordinate3D(0, 0, 0, 0);
        new HorizontalLineSet(geom, start, 3);
    }

    @Test(expected = NotImplementedException.class)
    public void negativeLengthThrows() throws Exception {
        Geometry geom = mock(Geometry.class);
        when(geom.getDimensionality()).thenReturn(2);
        new HorizontalLineSet(geom, start, -1);
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
}