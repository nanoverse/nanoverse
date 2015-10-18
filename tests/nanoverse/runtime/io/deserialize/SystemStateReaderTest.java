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

package nanoverse.runtime.io.deserialize;

import nanoverse.runtime.cells.MockAgent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.io.serialize.Serializer;
import nanoverse.runtime.io.serialize.binary.*;
import nanoverse.runtime.io.serialize.text.*;
import nanoverse.runtime.layers.LightweightSystemState;
import nanoverse.runtime.processes.MockStepState;
import nanoverse.runtime.structural.MockGeneralParameters;
import org.junit.*;
import test.LegacyLatticeTest;

import java.util.Iterator;
import java.util.stream.Stream;

import static org.junit.Assert.*;

//import nanoverse.runtime.layers.MockSoluteLayer;

/**
 * Test for the SystemStateReader. As an I/O orchestrator whose main function
 * is to open a bunch of files, this is an annoying class to mock. Ultimately,
 * I decided (for the time being) to use existing mocks, some of which are
 * incompatible. As a result, this is a rather cursory test. If I start running
 * into problems surrounding deserialization, I will rewrite the tests for this
 * class.
 * <p>
 * Created by dbborens on 3/28/14.
 */
public class SystemStateReaderTest extends LegacyLatticeTest {
    // The only thing SystemStateReader does is to return an anonymous
    // iterator. Therefore, each test is actually about this iterator,
    // and the iterator is the query.
    private Iterator<LightweightSystemState> query;
    private String[] soluteIds;
    private int[] channelIds;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        String path = fixturePath + "SystemStateReader/";

        soluteIds = new String[]{"0"};
        channelIds = new int[]{0};
        SystemStateReader parent = new SystemStateReader(soluteIds, channelIds, path, geom);
        query = parent.iterator();
    }

    /**
     * The hasNext() function determines its value from the time file. If
     * the the other files disagree with this file, an error will occur.
     *
     * @throws Exception
     */
    @Test
    public void testHasNext() throws Exception {
        // There are two frames specified in the time fixture, so we expect
        // that hasNext() will return true twice, and then return false the
        // third time.
        assertTrue(query.hasNext());

        query.next();
        assertTrue(query.hasNext());

        query.next();
        assertFalse(query.hasNext());
    }

    @Test
    public void testNext() throws Exception {
        LightweightSystemState state = query.next();

        // Check cell name
        assertEquals(5, state.getLayerManager().getAgentLayer().getViewer().getName(x));

        // Origin is vacant
        assertEquals(0, state.getLayerManager().getAgentLayer().getViewer().getName(origin));

        // Check time and frame
        assertEquals(2, state.getFrame());
        assertEquals(1.7, state.getTime(), epsilon);

        // Check highlighting
        assertTrue(state.isHighlighted(0, x));
        assertFalse(state.isHighlighted(0, y));
    }

    /**
     * Create the fixture files used in this test.
     */
    @SuppressWarnings("unused")
    private void generateFixtures() throws Exception {

        Serializer[] serializers = makeSerializerArray();

        placeAgent(x, 2.0, "5");
        placeAgent(y, 1.0, "3");

        Stream<Coordinate> highlights = Stream.of(x);
        MockStepState stepState = new MockStepState(1.7, 2);
        stepState.setHighlights(0, highlights);

        /* Initialize output and push first name */
        for (Serializer serializer : serializers) {
            serializer.init();
            serializer.flush(stepState);
            serializer.flush(stepState);
        }

        /* Set up second name */
        cellLayer.getUpdateManager().banish(x);
//        highlights = new Coordinate[]{y};
        highlights = Stream.of(y);
        stepState = new MockStepState(4.8, 6);
        stepState.setHighlights(0, highlights);

//        pushState(layer0, new double[]{0.1, 0.2, 0.3, 0.4, 0.5});

        /* Push second name and close fixture */
        for (Serializer serializer : serializers) {
            serializer.flush(stepState);
            serializer.dispatchHalt(null);
        }
    }

    private Serializer[] makeSerializerArray() {
        MockGeneralParameters p = makeMockGeneralParameters();
        p.setIsFrameValue(true);
        Serializer[] ret = new Serializer[]{
            new CoordinateIndexer(p, layerManager),
            new TimeWriter(p, layerManager),
            new ContinuumStateWriter(p, layerManager),
            new LegacyAgentClassWriter(p, layerManager),
            new HighlightWriter(p, Stream.<Integer>empty(), layerManager)
        };

        return ret;
    }

    private MockAgent placeAgent(Coordinate coord, double health, String name) throws Exception {
        MockAgent agent = new MockAgent();
        agent.setName(name);
        cellLayer.getUpdateManager().place(agent, coord);

        return agent;
    }
}