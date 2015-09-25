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

import control.identifiers.Coordinate;
import layers.LightweightSystemState;
import org.junit.*;
import test.LegacyLatticeTest;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by dbborens on 3/26/14.
 */
public class HighlightReaderTest extends LegacyLatticeTest {

    private HighlightReader query;
    private MockCoordinateDeindexer deindexer;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        String root = fixturePath;
        int[] channels = new int[]{0};
        deindexer = new MockCoordinateDeindexer();
        deindexer.setUnderlying(cc);
        query = new HighlightReader(root, channels, deindexer);
    }

    @Test
    public void testNext() throws Exception {
        Map<Integer, Set<Coordinate>> actual = query.next();

        // First, make sure that the only highlight channel is channel 0.
        assertEquals(1, actual.size());
        assertTrue(actual.containsKey(0));

        // Then, check what coordinates were highlighted. To see the
        // definition of the fixture, see HighlightWriterTest.
        Set<Coordinate> highlights = actual.get(0);
        assertEquals(2, highlights.size());
        assertTrue(highlights.contains(x));
        assertTrue(highlights.contains(y));
        assertFalse(highlights.contains(origin));
    }

    @Test
    public void testPopulate() throws Exception {
        LightweightSystemState state = new LightweightSystemState(geom);
        query.populate(state);
        assertTrue(state.isHighlighted(0, x));
        assertTrue(state.isHighlighted(0, y));
        assertFalse(state.isHighlighted(0, origin));
    }
}
