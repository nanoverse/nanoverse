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
import org.junit.Test;
import test.LegacyTest;

import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * Regression/integration tests from earliest version of nanoverse.runtime.geometry
 * model. These tests use an arena nanoverse.runtime.geometry. Originally ported
 * from C++.
 *
 * @author dbborens
 */
public class HexArenaTest extends LegacyTest {

    // Test indexing and de-indexing behavior in 2D and 3D.
    //
    // Note that indexing works whether or not the coordinates are
    // valid for the specified nanoverse.runtime.geometry. Indexing should ultimately
    // be offloaded to an indexer object.
    //
    // Also note that calling the z coordinate of a 2D point will,
    // by design, return 0.
    @Test
    public void testIndex() {
        Coordinate o2 = new Coordinate2D(0, 0, 0);
        Coordinate o3 = new Coordinate3D(0, 0, 0, 0);

        // Origin
        assertEquals(0, o2.x());
        assertEquals(0, o2.y());
        assertEquals(0, o2.z());

        assertEquals(0, o3.x());
        assertEquals(0, o3.y());
        assertEquals(0, o3.z());


        // 3D point
        Coordinate p3 = new Coordinate3D(15, 37, 262, 0);
        assertEquals(15, p3.x());
        assertEquals(37, p3.y());
        assertEquals(262, p3.z());

        // 2D point
        Coordinate p2 = new Coordinate2D(24, 99, 0);
        assertEquals(24, p2.x());
        assertEquals(99, p2.y());
        assertEquals(0, p2.z());
    }

    @Test
    public void testCanonicalSites() {
        // Produce 6x4 HexArena
        int height = 6;
        int width = 4;
        Lattice lattice = new TriangularLattice();
        Shape shape = new Rectangle(lattice, width, height);
        Boundary boundary = new Arena(shape, lattice);
        Geometry hr = new Geometry(lattice, shape, boundary);

        // Create unordered set of all expected coordinates
        HashSet<Coordinate> s = new HashSet<Coordinate>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int yAdj = y + (x / 2);
                Coordinate c = new Coordinate2D(x, yAdj, 0);
                s.add(c);
            }
        }

        Coordinate[] sites = hr.getCanonicalSites();

        // Vector should contain all of the sites.
        assertEquals(s.size(), sites.length);
        for (int i = 0; i < sites.length; i++) {
            Coordinate coord = sites[i];
            assertTrue(s.contains(coord));
        }
    }


    // getL1Distance(...)
    // getDisplacement(...)
    @Test
    public void testL1AndDisplacement() {
        Lattice lattice = new TriangularLattice();
        Shape shape = new Rectangle(lattice, 4, 6);
        Boundary boundary = new Arena(shape, lattice);
        Geometry hr = new Geometry(lattice, shape, boundary);

        Coordinate p = new Coordinate2D(1, 1, 0);
        Coordinate q = new Coordinate2D(2, 4, 0);

        Coordinate disp = hr.getDisplacement(p, q, Geometry.APPLY_BOUNDARIES);
        Coordinate expected = new Coordinate3D(0, 1, 2, Flags.VECTOR);
        assertEquals(expected, disp);

        assertEquals(3, hr.getL1Distance(p, q, Geometry.APPLY_BOUNDARIES));

        p = new Coordinate2D(1, 2, 0);
        q = new Coordinate2D(0, 0, 0);
        disp = hr.getDisplacement(p, q, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate3D(0, -1, -1, Flags.VECTOR);
        assertEquals(expected, disp);

        assertEquals(2, hr.getL1Distance(p, q, Geometry.APPLY_BOUNDARIES));

        p = new Coordinate2D(0, 0, 0);
        q = new Coordinate2D(0, 0, 0);
        disp = hr.getDisplacement(p, q, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate3D(0, 0, 0, Flags.VECTOR);
        assertEquals(expected, disp);

        assertEquals(0, hr.getL1Distance(p, q, Geometry.APPLY_BOUNDARIES));
    }


    // Test (non-)wrapping
    @Test
    public void testWrap() {

        Lattice lattice = new TriangularLattice();
        Shape shape = new Rectangle(lattice, 4, 4);
        Boundary boundary = new Arena(shape, lattice);
        Geometry hr = new Geometry(lattice, shape, boundary);

        // Over right edge
        Coordinate actual, expected, initial;
        initial = new Coordinate2D(4, 2, 0);
        actual = hr.apply(initial, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate2D(4, 2, Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        assertEquals(actual, expected);

        // Over left edge
        initial = new Coordinate2D(-1, 0, 0);
        actual = hr.apply(initial, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate2D(-1, 0, Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        assertEquals(actual, expected);

        // Above
        initial = new Coordinate2D(4, 0, 0);
        actual = hr.apply(initial, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate2D(4, 0, Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        assertEquals(actual, expected);

        // No wrap (internal coordinate)
        initial = new Coordinate2D(2, 3, 0);
        actual = hr.apply(initial, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate2D(2, 3, 0);
        assertEquals(actual, expected);

        // More than twice the system width
        initial = new Coordinate2D(9, 6, 0);
        actual = hr.apply(initial, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate2D(9, 6, Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        assertEquals(actual, expected);
    }

    @Test
    public void testAgentNeighbors() {

        Lattice lattice = new TriangularLattice();
        Shape shape = new Rectangle(lattice, 6, 6);
        Boundary boundary = new Arena(shape, lattice);
        Geometry hr = new Geometry(lattice, shape, boundary);

        // Interior
        Coordinate initial = new Coordinate2D(3, 4, 0);
        Coordinate coord = hr.apply(initial, Geometry.APPLY_BOUNDARIES);

        HashSet<Coordinate> interior_exp = new HashSet<Coordinate>();
        interior_exp.add(new Coordinate2D(3, 5, 0));
        interior_exp.add(new Coordinate2D(4, 5, 0));
        interior_exp.add(new Coordinate2D(4, 4, 0));
        interior_exp.add(new Coordinate2D(3, 3, 0));
        interior_exp.add(new Coordinate2D(2, 3, 0));
        interior_exp.add(new Coordinate2D(2, 4, 0));

        Coordinate[] neighbors = hr.getNeighbors(coord, Geometry.APPLY_BOUNDARIES);

        assertEquals(neighbors.length, 6);
        for (int i = 0; i < neighbors.length; i++) {
            Coordinate neighbor = neighbors[i];
            assertTrue(interior_exp.contains(neighbor));
        }

        // Side -- check wrapped
        initial = new Coordinate2D(5, 5, 0);
        coord = hr.apply(initial, Geometry.APPLY_BOUNDARIES);

        HashSet<Coordinate> side_exp = new HashSet<Coordinate>();
        side_exp.add(new Coordinate2D(5, 6, 0));
        side_exp.add(new Coordinate2D(6, 6, Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED));
        side_exp.add(new Coordinate2D(6, 5, Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED));
        side_exp.add(new Coordinate2D(5, 4, 0));
        side_exp.add(new Coordinate2D(4, 4, 0));
        side_exp.add(new Coordinate2D(4, 5, 0));

        neighbors = hr.getNeighbors(coord, Geometry.APPLY_BOUNDARIES);
        assertEquals(neighbors.length, 6);
        for (int i = 0; i < neighbors.length; i++) {
            Coordinate neighbor = neighbors[i];
            assertTrue(side_exp.contains(neighbor));
        }

        // Bottom
        initial = new Coordinate2D(2, 1, 0);
        coord = hr.apply(initial, Geometry.APPLY_BOUNDARIES);

        HashSet<Coordinate> bottom_exp = new HashSet<Coordinate>();
        bottom_exp.add(new Coordinate2D(2, 0, Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED));
        bottom_exp.add(new Coordinate2D(1, 0, 0));
        bottom_exp.add(new Coordinate2D(1, 1, 0));
        bottom_exp.add(new Coordinate2D(2, 2, 0));
        bottom_exp.add(new Coordinate2D(3, 2, 0));
        bottom_exp.add(new Coordinate2D(3, 1, 0));

        neighbors = hr.getNeighbors(coord, Geometry.APPLY_BOUNDARIES);

        assertEquals(6, neighbors.length);
        for (int i = 0; i < neighbors.length; i++) {
            Coordinate neighbor = neighbors[i];
            assertTrue(bottom_exp.contains(neighbor));
        }
    }

    // All cases but top/bottom should be same as getAgentNeighbors(...)
    // for the HexTorus nanoverse.runtime.geometry.
    // getSoluteNeighbors(...)
    @Test
    public void testSoluteNeighbors() {
        Lattice lattice = new TriangularLattice();
        Shape shape = new Rectangle(lattice, 6, 6);
        Boundary boundary = new Arena(shape, lattice);
        Geometry hr = new Geometry(lattice, shape, boundary);
        Coordinate[] neighbors;

        // Interior
        Coordinate coord = new Coordinate2D(3, 4, 0);

        HashSet<Coordinate> interior_exp = new HashSet<Coordinate>();
        interior_exp.add(new Coordinate2D(3, 5, 0));
        interior_exp.add(new Coordinate2D(4, 5, 0));
        interior_exp.add(new Coordinate2D(4, 4, 0));
        interior_exp.add(new Coordinate2D(3, 3, 0));
        interior_exp.add(new Coordinate2D(2, 3, 0));
        interior_exp.add(new Coordinate2D(2, 4, 0));

        neighbors = hr.getNeighbors(coord, Geometry.EXCLUDE_BOUNDARIES);

        assertEquals(neighbors.length, 6);
        for (int i = 0; i < neighbors.length; i++) {
            Coordinate neighbor = neighbors[i];
            assertTrue(interior_exp.contains(neighbor));
        }

        // Side
        coord = new Coordinate2D(5, 5, 0);

        HashSet<Coordinate> side_exp = new HashSet<Coordinate>();
        side_exp.add(new Coordinate2D(5, 6, 0));
        side_exp.add(new Coordinate2D(5, 4, 0));
        side_exp.add(new Coordinate2D(4, 4, 0));
        side_exp.add(new Coordinate2D(4, 5, 0));

        neighbors = hr.getNeighbors(coord, Geometry.EXCLUDE_BOUNDARIES);
        assertEquals(side_exp.size(), neighbors.length);
        for (int i = 0; i < neighbors.length; i++) {
            Coordinate neighbor = neighbors[i];
            assertTrue(side_exp.contains(neighbor));
        }

        // Bottom
        coord = new Coordinate2D(2, 1, 0);

        HashSet<Coordinate> bottom_exp = new HashSet<Coordinate>();
        bottom_exp.add(new Coordinate2D(1, 0, 0));
        bottom_exp.add(new Coordinate2D(1, 1, 0));
        bottom_exp.add(new Coordinate2D(2, 2, 0));
        bottom_exp.add(new Coordinate2D(3, 2, 0));
        bottom_exp.add(new Coordinate2D(3, 1, 0));


        neighbors = hr.getNeighbors(coord, Geometry.EXCLUDE_BOUNDARIES);
        assertEquals(bottom_exp.size(), neighbors.length);

        for (int i = 0; i < neighbors.length; i++) {

            Coordinate neighbor = neighbors[i];
            assertTrue(bottom_exp.contains(neighbor));
        }

    }

    // getAnnulus(...)
    @Test
    public void testAnnulus() {
        Lattice lattice = new TriangularLattice();
        Shape shape = new Rectangle(lattice, 6, 6);
        Boundary boundary = new Arena(shape, lattice);
        Geometry hr = new Geometry(lattice, shape, boundary);

        Coordinate coord = new Coordinate2D(0, 2, 0);

        Coordinate[] result;

        // Point
        result = hr.getAnnulus(coord, 0, Geometry.APPLY_BOUNDARIES);
        assertEquals(1, result.length);
        assertEquals(coord, result[0]);

        // r=1
        result = hr.getAnnulus(coord, 1, Geometry.APPLY_BOUNDARIES);
        assertEquals(6, result.length);

        // r=2 (big)--circumnavigation shouldn't matter.
        result = hr.getAnnulus(coord, 2, Geometry.APPLY_BOUNDARIES);
        assertEquals(12, result.length);
    }

    // Tests for correct behavior in vicinity of origin when dimensions
    // are 6x6, as in the lattice tests.
    @Test
    public void originWrap() {
        // Explicitly test wrapping behavior in vicinity of origin
        Lattice lattice = new TriangularLattice();
        Shape shape = new Rectangle(lattice, 6, 6);
        Boundary boundary = new Arena(shape, lattice);
        Geometry hr = new Geometry(lattice, shape, boundary);

        // (1, 0) stays (1, 0)
        Coordinate initial, actual, expected;
        initial = new Coordinate2D(1, 0, 0);
        expected = new Coordinate2D(1, 0, 0);
        actual = hr.apply(initial, Geometry.APPLY_BOUNDARIES);
        assertEquals(expected, actual);

        // (1, 1) stays (1, 1)
        initial = new Coordinate2D(1, 1, 0);
        expected = new Coordinate2D(1, 1, 0);
        actual = hr.apply(initial, Geometry.APPLY_BOUNDARIES);
        assertEquals(expected, actual);

        // (0, 1) stays (0, 1)
        initial = new Coordinate2D(0, 1, 0);
        expected = new Coordinate2D(0, 1, 0);
        actual = hr.apply(initial, Geometry.APPLY_BOUNDARIES);
        assertEquals(expected, actual);


        // (-1, -1) gets a flag
        initial = new Coordinate2D(-1, -1, 0);
        expected = new Coordinate2D(-1, -1, Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        actual = hr.apply(initial, Geometry.APPLY_BOUNDARIES);
        assertEquals(expected, actual);

        // (-1, 0) gets a flag
        initial = new Coordinate2D(-1, 0, 0);
        expected = new Coordinate2D(-1, 0, Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        actual = hr.apply(initial, Geometry.APPLY_BOUNDARIES);
        assertEquals(expected, actual);

        // (0, -1) gets a flag
        initial = new Coordinate2D(0, -1, 0);
        expected = new Coordinate2D(0, -1, Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        actual = hr.apply(initial, Geometry.APPLY_BOUNDARIES);
        assertEquals(expected, actual);
    }
}
