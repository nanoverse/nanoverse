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

package nanoverse.runtime.geometry.geometry;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.*;
import nanoverse.runtime.geometry.boundaries.Boundary;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.*;

/**
 * Pseudo-unit test for basic behavior of the Geometry object.
 *
 * @author David Bruce Borenstein
 * @untested
 */
public class GeometryTest extends LegacyTest {

    private Geometry geom;
    private Lattice lattice;
    private Shape shape;
    private Boundary boundary;

    private Coordinate p, q, r;

    @Before
    public void setUp() {
        lattice = new RectangularLattice();
        shape = new Rectangle(lattice, 4, 4);
        boundary = new MockBoundary(shape, lattice);
        geom = new Geometry(lattice, shape, boundary);

        p = new Coordinate2D(1, 2, 0);
        q = new Coordinate2D(3, 2, 0);
        r = new Coordinate2D(4, 2, 0);    // Wraps around
    }

    @Test
    public void testCloneAtScale() {
        assertEquals(16, geom.getCanonicalSites().length);
        Geometry scaled = geom.cloneAtScale(2.0);

        // They should be the same in all but dimension.
        assertTrue(geom.similar(scaled));

        // Scales by 2 in both x and y directions --> 4 times as many sites
        assertEquals(64, scaled.getCanonicalSites().length);
    }

    @Test
    public void testNeighborsApply() {
        Coordinate[] actual, expected;

        // Within bounds
        actual = geom.getNeighbors(p, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate2D[]{
            new Coordinate2D(2, 2, 0),
            new Coordinate2D(1, 3, 0),
            new Coordinate2D(0, 2, 0),
            new Coordinate2D(1, 1, 0)
        };
        assertArraysEqual(actual, expected, true);

        // One neighbor of bounds
        actual = geom.getNeighbors(q, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate2D[]{
            new Coordinate2D(0, 2, Flags.BOUNDARY_APPLIED),
            new Coordinate2D(3, 3, 0),
            new Coordinate2D(2, 2, 0),
            new Coordinate2D(3, 1, 0)
        };
        assertArraysEqual(actual, expected, true);

        // Origin out of bounds
        actual = geom.getNeighbors(q, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate2D[]{
            new Coordinate2D(1, 2, Flags.BOUNDARY_APPLIED),
            new Coordinate2D(0, 3, Flags.BOUNDARY_APPLIED),
            new Coordinate2D(3, 2, 0),
            new Coordinate2D(3, 1, 0)
        };
    }

    @Test
    public void testNeighborsFlag() {
        Coordinate[] actual, expected;

        // One neighbor of bounds
        actual = geom.getNeighbors(q, Geometry.FLAG_BOUNDARIES);
        expected = new Coordinate2D[]{
            new Coordinate2D(4, 2, Flags.BOUNDARY_IGNORED),
            new Coordinate2D(3, 3, 0),
            new Coordinate2D(2, 2, 0),
            new Coordinate2D(3, 1, 0)
        };
        assertArraysEqual(actual, expected, true);

        // Origin out of bounds
        actual = geom.getNeighbors(q, Geometry.FLAG_BOUNDARIES);
        expected = new Coordinate2D[]{
            new Coordinate2D(5, 2, Flags.BOUNDARY_IGNORED),
            new Coordinate2D(4, 3, Flags.BOUNDARY_IGNORED),
            new Coordinate2D(3, 2, 0),
            new Coordinate2D(3, 1, 0)
        };
    }

    @Test
    public void testNeighborsExclude() {
        Coordinate[] actual, expected;

        // One neighbor of bounds
        actual = geom.getNeighbors(q, Geometry.EXCLUDE_BOUNDARIES);
        expected = new Coordinate2D[]{
            new Coordinate2D(3, 3, 0),
            new Coordinate2D(2, 2, 0),
            new Coordinate2D(3, 1, 0)
        };
        assertArraysEqual(actual, expected, true);

        // Origin out of bounds
        actual = geom.getNeighbors(q, Geometry.EXCLUDE_BOUNDARIES);
        expected = new Coordinate2D[]{
            new Coordinate2D(3, 2, 0),
            new Coordinate2D(3, 1, 0)
        };
    }

    @Test
    public void testNeighborsIgnore() {
        Coordinate[] actual, expected;

        // One neighbor of bounds
        actual = geom.getNeighbors(q, Geometry.IGNORE_BOUNDARIES);
        expected = new Coordinate2D[]{
            new Coordinate2D(4, 2, 0),
            new Coordinate2D(3, 3, 0),
            new Coordinate2D(2, 2, 0),
            new Coordinate2D(3, 1, 0)
        };
        assertArraysEqual(actual, expected, true);

        // Origin out of bounds
        actual = geom.getNeighbors(q, Geometry.IGNORE_BOUNDARIES);
        expected = new Coordinate2D[]{
            new Coordinate2D(5, 2, 0),
            new Coordinate2D(4, 3, 0),
            new Coordinate2D(3, 2, 0),
            new Coordinate2D(3, 1, 0)
        };
    }

    @Test
    public void testAnnulusApply() {
        Coordinate[] actual, expected;

        // Within bounds
        actual = geom.getAnnulus(p, 1, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate2D[]{
            new Coordinate2D(2, 2, 0),
            new Coordinate2D(1, 3, 0),
            new Coordinate2D(0, 2, 0),
            new Coordinate2D(1, 1, 0)
        };
        assertArraysEqual(actual, expected, true);

        // One neighbor of bounds
        actual = geom.getAnnulus(q, 1, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate2D[]{
            new Coordinate2D(0, 2, Flags.BOUNDARY_APPLIED),
            new Coordinate2D(3, 3, 0),
            new Coordinate2D(2, 2, 0),
            new Coordinate2D(3, 1, 0)
        };
        assertArraysEqual(actual, expected, true);

        // Origin out of bounds
        actual = geom.getAnnulus(q, 1, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate2D[]{
            new Coordinate2D(1, 2, Flags.BOUNDARY_APPLIED),
            new Coordinate2D(0, 3, Flags.BOUNDARY_APPLIED),
            new Coordinate2D(3, 2, 0),
            new Coordinate2D(3, 1, 0)
        };
    }

    @Test
    public void testAnnulusFlag() {
        Coordinate[] actual, expected;

        // One neighbor of bounds
        actual = geom.getAnnulus(q, 1, Geometry.FLAG_BOUNDARIES);
        expected = new Coordinate2D[]{
            new Coordinate2D(4, 2, Flags.BOUNDARY_IGNORED),
            new Coordinate2D(3, 3, 0),
            new Coordinate2D(2, 2, 0),
            new Coordinate2D(3, 1, 0)
        };
        assertArraysEqual(actual, expected, true);

        // Origin out of bounds
        actual = geom.getAnnulus(q, 1, Geometry.FLAG_BOUNDARIES);
        expected = new Coordinate2D[]{
            new Coordinate2D(5, 2, Flags.BOUNDARY_IGNORED),
            new Coordinate2D(4, 3, Flags.BOUNDARY_IGNORED),
            new Coordinate2D(3, 2, 0),
            new Coordinate2D(3, 1, 0)
        };
    }

    @Test
    public void testAnnulusExclude() {
        Coordinate[] actual, expected;

        // One neighbor of bounds
        actual = geom.getAnnulus(q, 1, Geometry.EXCLUDE_BOUNDARIES);
        expected = new Coordinate2D[]{
            new Coordinate2D(3, 3, 0),
            new Coordinate2D(2, 2, 0),
            new Coordinate2D(3, 1, 0)
        };
        assertArraysEqual(actual, expected, true);

        // Origin out of bounds
        actual = geom.getAnnulus(q, 1, Geometry.EXCLUDE_BOUNDARIES);
        expected = new Coordinate2D[]{
            new Coordinate2D(3, 2, 0),
            new Coordinate2D(3, 1, 0)
        };
    }

    @Test
    public void testAnnulusIgnore() {
        Coordinate[] actual, expected;

        // One neighbor of bounds
        actual = geom.getAnnulus(q, 1, Geometry.IGNORE_BOUNDARIES);
        expected = new Coordinate2D[]{
            new Coordinate2D(4, 2, 0),
            new Coordinate2D(3, 3, 0),
            new Coordinate2D(2, 2, 0),
            new Coordinate2D(3, 1, 0)
        };
        assertArraysEqual(actual, expected, true);

        // Origin out of bounds
        actual = geom.getAnnulus(q, 1, Geometry.IGNORE_BOUNDARIES);
        expected = new Coordinate2D[]{
            new Coordinate2D(5, 2, 0),
            new Coordinate2D(4, 3, 0),
            new Coordinate2D(3, 2, 0),
            new Coordinate2D(3, 1, 0)
        };
    }

    @Test
    public void testRel2absApply() {
        Coordinate expected = new Coordinate2D(0, 2, Flags.BOUNDARY_APPLIED);
        Coordinate disp = new Coordinate2D(3, 0, Flags.VECTOR);
        Coordinate actual = geom.rel2abs(p, disp, Geometry.APPLY_BOUNDARIES);
        assertEquals(actual, expected);
    }

    @Test
    public void testRel2absFlag() {
        Coordinate expected = new Coordinate2D(4, 2, Flags.BOUNDARY_IGNORED);
        Coordinate disp = new Coordinate2D(3, 0, Flags.VECTOR);
        Coordinate actual = geom.rel2abs(p, disp, Geometry.FLAG_BOUNDARIES);
        assertEquals(actual, expected);
    }

    @Test
    public void testRel2absExclude() {
        Coordinate disp = new Coordinate2D(3, 0, Flags.VECTOR);
        Coordinate actual = geom.rel2abs(p, disp, Geometry.EXCLUDE_BOUNDARIES);
        assertNull(actual);
    }

    @Test
    public void testRel2absIgnore() {
        Coordinate expected = new Coordinate2D(4, 2, 0);
        Coordinate disp = new Coordinate2D(3, 0, Flags.VECTOR);
        Coordinate actual = geom.rel2abs(p, disp, Geometry.IGNORE_BOUNDARIES);
        assertEquals(expected, actual);
    }

    @Test
    public void testL1DistanceApply() {
        int actual, expected;

        // p->p
        actual = geom.getL1Distance(p, p, Geometry.APPLY_BOUNDARIES);
        expected = 0;
        assertEquals(actual, expected);

        // p->q
        actual = geom.getL1Distance(p, q, Geometry.APPLY_BOUNDARIES);
        expected = 2;
        assertEquals(actual, expected);

        // p->r
        actual = geom.getL1Distance(p, r, Geometry.APPLY_BOUNDARIES);
        expected = 1;
        assertEquals(actual, expected);

        // r->p
        actual = geom.getL1Distance(r, p, Geometry.APPLY_BOUNDARIES);
        expected = 1;
        assertEquals(actual, expected);

        // r->r
        actual = geom.getL1Distance(r, r, Geometry.APPLY_BOUNDARIES);
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test
    public void testL1DistanceIgnore() {
        int actual, expected;

        // p->p
        actual = geom.getL1Distance(p, p, Geometry.IGNORE_BOUNDARIES);
        expected = 0;
        assertEquals(actual, expected);

        // p->q
        actual = geom.getL1Distance(p, q, Geometry.IGNORE_BOUNDARIES);
        expected = 2;
        assertEquals(actual, expected);

        // p->r
        actual = geom.getL1Distance(p, r, Geometry.IGNORE_BOUNDARIES);
        expected = 3;
        assertEquals(actual, expected);

        // r->p
        actual = geom.getL1Distance(r, p, Geometry.IGNORE_BOUNDARIES);
        expected = 3;
        assertEquals(actual, expected);

        // r->r
        actual = geom.getL1Distance(r, r, Geometry.IGNORE_BOUNDARIES);
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test
    public void testL1DistanceExclude() {
        boolean threw = false;
        try {
            geom.getL1Distance(p, q, Geometry.EXCLUDE_BOUNDARIES);
        } catch (Exception ex) {
            threw = true;
        }
        assertTrue(threw);
    }

    @Test
    public void testL1DistanceFlag() {
        boolean threw = false;
        try {
            geom.getL1Distance(p, q, Geometry.FLAG_BOUNDARIES);
        } catch (Exception ex) {
            threw = true;
        }
        assertTrue(threw);
    }

    @Test
    public void testDisplacementApply() {
        Coordinate actual, expected;

        // p->p
        actual = geom.getDisplacement(p, p, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate2D(0, 0, Flags.VECTOR);
        assertEquals(actual, expected);

        // p->q
        actual = geom.getDisplacement(p, q, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate2D(2, 0, Flags.VECTOR);
        assertEquals(actual, expected);

        // p->r
        actual = geom.getDisplacement(p, r, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate2D(-1, 0, Flags.VECTOR);
        assertEquals(actual, expected);

        // r->p
        actual = geom.getDisplacement(r, p, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate2D(1, 0, Flags.VECTOR);
        assertEquals(actual, expected);

        // r->r
        actual = geom.getDisplacement(r, r, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate2D(0, 0, Flags.VECTOR);
        assertEquals(actual, expected);
    }

    @Test
    public void testDisplacementFlag() {
        boolean threw = false;
        try {
            geom.getDisplacement(p, q, Geometry.FLAG_BOUNDARIES);
        } catch (Exception ex) {
            threw = true;
        }
        assertTrue(threw);
    }

    @Test
    public void testDisplacementExclude() {
        boolean threw = false;
        try {
            geom.getDisplacement(p, q, Geometry.EXCLUDE_BOUNDARIES);
        } catch (Exception ex) {
            threw = true;
        }
        assertTrue(threw);
    }

    @Test
    public void testDisplacementIgnore() {
        Coordinate actual, expected;

        // p->p
        actual = geom.getDisplacement(p, p, Geometry.IGNORE_BOUNDARIES);
        expected = new Coordinate2D(0, 0, Flags.VECTOR);
        assertEquals(actual, expected);

        // p->q
        actual = geom.getDisplacement(p, q, Geometry.IGNORE_BOUNDARIES);
        expected = new Coordinate2D(2, 0, Flags.VECTOR);
        assertEquals(actual, expected);

        // p->r
        actual = geom.getDisplacement(p, r, Geometry.IGNORE_BOUNDARIES);
        expected = new Coordinate2D(3, 0, Flags.VECTOR);
        assertEquals(actual, expected);

        // r->p
        actual = geom.getDisplacement(r, p, Geometry.IGNORE_BOUNDARIES);
        expected = new Coordinate2D(-3, 0, Flags.VECTOR);
        assertEquals(actual, expected);

        // r->r
        actual = geom.getDisplacement(r, r, Geometry.IGNORE_BOUNDARIES);
        expected = new Coordinate2D(0, 0, Flags.VECTOR);
        assertEquals(actual, expected);
    }

    @Test
    public void testCanonicalSites() {
        MockGeometry geom = new MockGeometry();
        Coordinate[] canonicals = new Coordinate[]{
            new Coordinate3D(1, 2, 3, 4),
            new Coordinate3D(0, 0, 0, 0)
        };
        geom.setCanonicalSites(canonicals);

        Coordinate[] expected = canonicals.clone();
        Coordinate[] actual = geom.getCanonicalSites();

        assertArraysEqual(expected, actual, true);
    }

    @Test
    public void testApplyExclude() {
        Coordinate actual, expected;

        actual = geom.apply(p, Geometry.EXCLUDE_BOUNDARIES);
        expected = p.clone();
        assertEquals(expected, actual);

        actual = geom.apply(q, Geometry.EXCLUDE_BOUNDARIES);
        expected = q.clone();
        assertEquals(expected, actual);

        actual = geom.apply(r, Geometry.EXCLUDE_BOUNDARIES);
        expected = null;
        assertEquals(expected, actual);
    }

    @Test
    public void testApplyApply() {
        Coordinate actual, expected;

        actual = geom.apply(p, Geometry.APPLY_BOUNDARIES);
        expected = p.clone();
        assertEquals(expected, actual);

        actual = geom.apply(q, Geometry.APPLY_BOUNDARIES);
        expected = q.clone();
        assertEquals(expected, actual);

        actual = geom.apply(r, Geometry.APPLY_BOUNDARIES);
        expected = new Coordinate2D(0, 2, Flags.BOUNDARY_APPLIED);
        assertEquals(expected, actual);
    }

    @Test
    public void testApplyIgnore() {
        Coordinate actual, expected;

        actual = geom.apply(p, Geometry.IGNORE_BOUNDARIES);
        expected = p.clone();
        assertEquals(expected, actual);

        actual = geom.apply(q, Geometry.IGNORE_BOUNDARIES);
        expected = q.clone();
        assertEquals(expected, actual);

        actual = geom.apply(r, Geometry.IGNORE_BOUNDARIES);
        expected = r.clone();
        assertEquals(expected, actual);
    }

    @Test
    public void testApplyFlag() {
        Coordinate actual, expected;

        actual = geom.apply(p, Geometry.FLAG_BOUNDARIES);
        expected = p.clone();
        assertEquals(expected, actual);

        actual = geom.apply(q, Geometry.FLAG_BOUNDARIES);
        expected = q.clone();
        assertEquals(expected, actual);

        actual = geom.apply(r, Geometry.FLAG_BOUNDARIES);
        expected = r.addFlags(Flags.BOUNDARY_IGNORED);
        assertEquals(expected, actual);
    }

    @Test
    public void testConnectivity() {
        MockLattice lattice = new MockLattice();
        MockShape shape = new MockShape();
        Geometry geom = new Geometry(lattice, shape, null);
        lattice.setConnectivity(5);
        assertEquals(5, geom.getConnectivity());
    }

    @Test
    public void testDimensionality() {
        MockLattice lattice = new MockLattice();
        MockShape shape = new MockShape();
        Geometry geom = new Geometry(lattice, shape, null);
        lattice.setDimensionality(5);
        assertEquals(5, geom.getDimensionality());
    }

    @Test
    public void testGetZeroVector() {
        Coordinate expected = new Coordinate2D(0, 0, 0);
        Coordinate actual = lattice.getZeroVector();
        assertEquals(expected, actual);
    }

    /**
     * Tests getIndexer().apply(...) and rebuildIndex()
     */
    @Test
    public void testCoordinateIndex() {
        MockGeometry geom = new MockGeometry();
        Coordinate o, p;
        o = new Coordinate3D(0, 0, 0, 0);

        // Notice not canonical.
        p = new Coordinate3D(1, 2, 3, 4);

        Coordinate[] canonicals = new Coordinate[]{o};
        geom.setCanonicalSites(canonicals);

        assertNull(geom.getIndexer().apply(p));
        assertNotNull(geom.getIndexer().apply(o));

        geom.setCanonicalSites(new Coordinate[]{o, p});

        assertNotNull(geom.getIndexer().apply(p));
        assertNotNull(geom.getIndexer().apply(o));
        assertFalse(geom.getIndexer().apply(o).equals(geom.getIndexer().apply(p)));
    }

    @Test
    public void testGetComponentClasses() {
        Class[] actual = geom.getComponentClasses();

        Class[] expected = new Class[]{
            RectangularLattice.class,
            Rectangle.class,
            MockBoundary.class
        };

        assertTrue(arraysEqual(expected, actual));
    }


    private class MockBoundary extends Boundary {

        public MockBoundary(Shape shape, Lattice lattice) {
            super(shape, lattice);
        }

        @Override
        protected void verify(Shape shape, Lattice lattice) {
        }

        @Override
        public Coordinate apply(Coordinate c) {
            int x = c.x() % 4;
            int y = c.y() % 4;

            int flags = 0;
            if (x != c.x() || y != c.y()) {
                flags |= Flags.BOUNDARY_APPLIED;
            }
            return new Coordinate2D(x, y, flags);
        }

        @Override
        public boolean isInfinite() {
            return false;
        }

        @Override
        public Boundary clone(Shape scaledShape, Lattice clonedLattice) {
            return new MockBoundary(scaledShape, clonedLattice);
        }

    }

}
