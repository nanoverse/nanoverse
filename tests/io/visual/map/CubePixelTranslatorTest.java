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

package io.visual.map;

import control.identifiers.*;
import geometry.lattice.*;
import geometry.shape.*;
import io.visual.VisualizationProperties;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.*;

public class CubePixelTranslatorTest extends LegacyTest {
    private CubePixelTranslator query;
    private Coordinate c0, c1;

    @Before
    public void setUp() throws Exception {
        c0 = new Coordinate3D(0, 0, 2, 0);
        c1 = new Coordinate3D(1, 4, 2, 0);
        Lattice lattice = new CubicLattice();
        Shape shape = new Cuboid(lattice, 5, 5, 5);
        Coordinate[] cc = shape.getCanonicalSites();
        VisualizationProperties mapState = new VisualizationProperties(null, 10, 1);
        mapState.setCoordinates(cc);
        query = new CubePixelTranslator();
        query.init(mapState);
    }

    @Test
    public void testOrigin() throws Exception {
        Coordinate expected = new Coordinate2D(5, 45, 0);
        Coordinate actual = query.indexToPixels(c0);
        assertEquals(expected, actual);
    }

    @Test
    public void testIndexToPixels() throws Exception {
        Coordinate actual = query.indexToPixels(c1);
        Coordinate expected = new Coordinate2D(15, 5, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetImageDims() throws Exception {
        Coordinate actual = query.getImageDims();
        Coordinate expected = new Coordinate2D(50, 50, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetDiagonal() throws Exception {
        double expected = Math.sqrt(2.0) * 10.0;
        double actual = query.getDiagonal();
        assertEquals(expected, actual, epsilon);
    }

    @Test
    public void testNonMiddleThrows() throws Exception {
        Coordinate nonMiddle = new Coordinate3D(0, 0, 0, 0);

        boolean thrown = false;

        try {
            query.indexToPixels(nonMiddle);
        } catch (IllegalStateException ex) {
            thrown = true;
        }

        assertTrue(thrown);
    }
}