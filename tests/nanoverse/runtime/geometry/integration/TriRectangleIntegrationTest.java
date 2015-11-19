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

package nanoverse.runtime.geometry.integration;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;
import org.junit.*;
import test.TestBase;

import java.util.*;
import java.util.stream.*;

import static org.junit.Assert.*;

/**
 * Regression/integration tests from earliest version of Nanoverse.
 * Originally ported from C++ as "HexArenaTest". Refactored in 2015.
 *
 * @author dbborens
 */
public class TriRectangleIntegrationTest extends TestBase {

    private static final int HEIGHT = 6;
    private static final int WIDTH = 6;

    private Lattice lattice;
    private Shape shape;
    private Boundary boundary;
    private Geometry geometry;

    @Before
    public void before() throws Exception {
        initializeGeometry(HEIGHT, WIDTH);
    }

    private void initializeGeometry(int height, int width) {
        lattice = new TriangularLattice();
        shape = new Rectangle(lattice, width, height);
        boundary = new Arena(shape, lattice);
        geometry = new Geometry(lattice, shape, boundary);
    }

    @Test
    public void testCanonicalSites() {
        // Create unordered set of all expected coordinates
        HashSet<Coordinate> expected = new HashSet<>();
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                int yAdj = y + (x / 2);
                Coordinate c = new Coordinate2D(x, yAdj, 0);
                expected.add(c);
            }
        }

        Coordinate[] sites = geometry.getCanonicalSites();
        Set<Coordinate> actual = toSet(sites);

        assertSetsEqual(expected, actual);
    }

    public Set<Coordinate> toSet(Coordinate[] array) {
        HashSet<Coordinate> ret = new HashSet<>();
        for (Coordinate site : array) {
            ret.add(site);
        }
        return ret;
    }
    @Test
    public void testPositiveDisplacement() throws Exception {
        Coordinate p = new Coordinate2D(1, 1, 0);
        Coordinate q = new Coordinate2D(2, 4, 0);

        Coordinate disp = geometry.getDisplacement(p, q, Geometry.APPLY_BOUNDARIES);
        Coordinate expected = new Coordinate2D(1, 3, Flags.VECTOR);
        assertEquals(expected, disp);

        assertEquals(3, geometry.getNeighborhoodDistance(p, q, Geometry.APPLY_BOUNDARIES));
    }

    @Test
    public void testNegativeDisplacement() throws Exception {
        Coordinate p = new Coordinate2D(1, 2, 0);
        Coordinate q = new Coordinate2D(0, 0, 0);
        Coordinate disp = geometry.getDisplacement(p, q, Geometry.APPLY_BOUNDARIES);
        Coordinate expected = new Coordinate2D(-1, -2, Flags.VECTOR);
        assertEquals(expected, disp);

        assertEquals(2, geometry.getNeighborhoodDistance(p, q, Geometry.APPLY_BOUNDARIES));

    }

    @Test
    public void testZeroDisplacement() {
        Coordinate p = new Coordinate2D(0, 0, 0);
        Coordinate q = new Coordinate2D(0, 0, 0);
        Coordinate disp = geometry.getDisplacement(p, q, Geometry.APPLY_BOUNDARIES);
        Coordinate expected = new Coordinate2D(0, 0, Flags.VECTOR);
        assertEquals(expected, disp);

        assertEquals(0, geometry.getNeighborhoodDistance(p, q, Geometry.APPLY_BOUNDARIES));
    }

    @Test
    public void wrapOverRightEdge() throws Exception {
        initializeGeometry(4, 4);
        Coordinate actual, expected, initial;
        initial = new Coordinate2D(4, 2, 0);
        actual = geometry.apply(initial, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate2D(4, 2, Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        assertEquals(actual, expected);
    }

    @Test
    public void wrapOverLeftEdge() throws Exception {
        initializeGeometry(4, 4);
        Coordinate actual, expected, initial;
        initial = new Coordinate2D(-1, 0, 0);
        actual = geometry.apply(initial, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate2D(-1, 0, Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        assertEquals(actual, expected);
    }

    @Test
    public void wrapOverTop() throws Exception {
        initializeGeometry(4, 4);
        Coordinate actual, expected, initial;
        initial = new Coordinate2D(4, 0, 0);
        actual = geometry.apply(initial, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate2D(4, 0, Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        assertEquals(actual, expected);
    }

    @Test
    public void inBoundsDoesntWrap() throws Exception {
        initializeGeometry(4, 4);
        Coordinate actual, expected, initial;
        initial = new Coordinate2D(2, 3, 0);
        actual = geometry.apply(initial, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate2D(2, 3, 0);
        assertEquals(actual, expected);
    }

    @Test
    public void extremeWrap() throws Exception {
        initializeGeometry(4, 4);
        Coordinate actual, expected, initial;
        initial = new Coordinate2D(9, 6, 0);
        actual = geometry.apply(initial, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate2D(9, 6, Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        assertEquals(actual, expected);
    }

    @Test
    public void interiorNeighborhood() throws Exception {
        initializeGeometry(6, 6);
        Coordinate initial = new Coordinate2D(3, 4, 0);
        Coordinate coord = geometry.apply(initial, Geometry.APPLY_BOUNDARIES);

        Set<Coordinate> expected = Stream.of(
                new Coordinate2D(3, 5, 0),
                new Coordinate2D(4, 5, 0),
                new Coordinate2D(4, 4, 0),
                new Coordinate2D(3, 3, 0),
                new Coordinate2D(2, 3, 0),
                new Coordinate2D(2, 4, 0))
            .collect(Collectors.toSet());

        Coordinate[] neighbors = geometry.getNeighbors(coord, Geometry.APPLY_BOUNDARIES);
        Set<Coordinate> actual = toSet(neighbors);
        assertSetsEqual(expected, actual);
    }

    @Test
    public void sideWrapNeighborhood() throws Exception {
        Coordinate initial = new Coordinate2D(5, 5, 0);
        Coordinate coord = geometry.apply(initial, Geometry.APPLY_BOUNDARIES);

        Set<Coordinate> expected = Stream.of(
                new Coordinate2D(5, 6, 0),
                new Coordinate2D(6, 6, Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED),
                new Coordinate2D(6, 5, Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED),
                new Coordinate2D(5, 4, 0),
                new Coordinate2D(4, 4, 0),
                new Coordinate2D(4, 5, 0))
            .collect(Collectors.toSet());

        Coordinate[] neighbors = geometry.getNeighbors(coord, Geometry.APPLY_BOUNDARIES);
        Set<Coordinate> actual = toSet(neighbors);
        assertSetsEqual(expected, actual);
    }

    @Test
    public void bottomNeighbors() throws Exception {
        initializeGeometry(6, 6);
        Coordinate initial = new Coordinate2D(2, 1, 0);
        Coordinate coord = geometry.apply(initial, Geometry.APPLY_BOUNDARIES);

        Set<Coordinate> expected = Stream.of(
                new Coordinate2D(2, 0, Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED),
                new Coordinate2D(1, 0, 0),
                new Coordinate2D(1, 1, 0),
                new Coordinate2D(2, 2, 0),
                new Coordinate2D(3, 2, 0),
                new Coordinate2D(3, 1, 0))
            .collect(Collectors.toSet());

        Coordinate[] neighbors = geometry.getNeighbors(coord, Geometry.APPLY_BOUNDARIES);
        Set<Coordinate> actual = toSet(neighbors);
        assertSetsEqual(expected, actual);
    }

    @Test
    public void testR0Annulus() throws Exception {
        initializeGeometry(6, 6);
        Coordinate coord = new Coordinate2D(0, 2, 0);
        Coordinate[] result = geometry.getAnnulus(coord, 0, Geometry.APPLY_BOUNDARIES);
        assertEquals(1, result.length);
        assertEquals(coord, result[0]);
    }

    @Test
    public void testR1Annulus() throws Exception {
        initializeGeometry(6, 6);
        Coordinate coord = new Coordinate2D(0, 2, 0);
        Coordinate[] result = geometry.getAnnulus(coord, 1, Geometry.APPLY_BOUNDARIES);
        assertEquals(6, result.length);
    }

    @Test
    public void testR2Annulus() throws Exception {
        initializeGeometry(6, 6);
        Coordinate coord = new Coordinate2D(0, 2, 0);
        Coordinate[] result = geometry.getAnnulus(coord, 2, Geometry.APPLY_BOUNDARIES);
        assertEquals(12, result.length);

    }

    @Test
    public void wrapBehavesCorrectlyAroundOrigin() {
        initializeGeometry(6, 6);

        // (1, 0) stays (1, 0)
        Coordinate initial, actual, expected;
        initial = new Coordinate2D(1, 0, 0);
        expected = new Coordinate2D(1, 0, 0);
        actual = geometry.apply(initial, Geometry.APPLY_BOUNDARIES);
        assertEquals(expected, actual);

        // (1, 1) stays (1, 1)
        initial = new Coordinate2D(1, 1, 0);
        expected = new Coordinate2D(1, 1, 0);
        actual = geometry.apply(initial, Geometry.APPLY_BOUNDARIES);
        assertEquals(expected, actual);

        // (0, 1) stays (0, 1)
        initial = new Coordinate2D(0, 1, 0);
        expected = new Coordinate2D(0, 1, 0);
        actual = geometry.apply(initial, Geometry.APPLY_BOUNDARIES);
        assertEquals(expected, actual);


        // (-1, -1) gets a flag
        initial = new Coordinate2D(-1, -1, 0);
        expected = new Coordinate2D(-1, -1, Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        actual = geometry.apply(initial, Geometry.APPLY_BOUNDARIES);
        assertEquals(expected, actual);

        // (-1, 0) gets a flag
        initial = new Coordinate2D(-1, 0, 0);
        expected = new Coordinate2D(-1, 0, Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        actual = geometry.apply(initial, Geometry.APPLY_BOUNDARIES);
        assertEquals(expected, actual);

        // (0, -1) gets a flag
        initial = new Coordinate2D(0, -1, 0);
        expected = new Coordinate2D(0, -1, Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        actual = geometry.apply(initial, Geometry.APPLY_BOUNDARIES);
        assertEquals(expected, actual);
    }
}
