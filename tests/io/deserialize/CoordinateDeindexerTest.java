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

package io.deserialize;

import control.identifiers.*;
import org.junit.*;
import test.EslimeTestCase;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
/**
 * Created by dbborens on 12/10/13.
 */
public class CoordinateDeindexerTest extends EslimeTestCase {

    private ExposedDeindexer query;

    @Before
    public void setUp() {
        query = new ExposedDeindexer(fixturePath);
    }

    @Test
    public void testIndex() {
        // This coordinate has index 1 -- see test fixture
        Coordinate input = new Coordinate2D(0, 1, 1);
        assertEquals(1, (int) query.getIndex(input));
    }

    @Test
    public void testDeindex() {
        Coordinate expected = new Coordinate2D(0, 1, 1);
        Coordinate actual = query.getCoordinate(1);
        assertEquals(expected, actual);
    }

    @Test
    public void testParseCoordinate() {
        String input = "(3, 5, 9 | 0)";
        Coordinate expected = new Coordinate3D(3, 5, 9, 0);
        Coordinate actual = query.parseCoordinate(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetNumSites() {
        assertEquals(4, query.getNumSites());
    }

    private class ExposedDeindexer extends CoordinateDeindexer {

        public HashMap<Integer, Coordinate> indexToCoord;

        public ExposedDeindexer(String path) {
            super(path);
            indexToCoord = super.indexToCoord;
        }

        public void deindex() throws IOException {
            super.deindex();
        }

        public Coordinate parseCoordinate(String token) {
            return super.parseCoordinate(token);
        }
    }
}
