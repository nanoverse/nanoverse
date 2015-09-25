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

package nanoverse.runtime.processes;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.*;
import nanoverse.runtime.layers.continuum.ContinuumLayer;
import org.junit.*;
import test.TestBase;

import java.util.*;
import java.util.stream.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by David B Borenstein on 4/20/14.
 */
public class StepStateTest extends TestBase {

    private LayerManager lm;
    private ContinuumLayer continuumLayer;
    private CellLayer cellLayer;
    private StepState query;
    private List<Double> continuumValues;
    private HashMap<String, List<Double>> continuumValueMap;
    private HashMap<Integer, Set<Coordinate>> highlights;
    private Coordinate c;

    @Before
    public void before() throws Exception {
        buildMockLayerManager();
        continuumValueMap = new HashMap<>();
        highlights = new HashMap<>();
        c = mock(Coordinate.class);
        query = new StepState(5.0, 1, highlights, continuumValueMap);
    }

    private void buildMockLayerManager() throws Exception {
        cellLayer = mock(CellLayer.class);
        continuumLayer = mock(ContinuumLayer.class);
        lm = mock(LayerManager.class);
        when(lm.getCellLayer()).thenReturn(cellLayer);
        when(lm.getContinuumLayerIds()).thenReturn(Stream.of("test"));
        when(lm.getContinuumLayer("test")).thenReturn(continuumLayer);
        continuumValues = DoubleStream.of(3.7, 2)
                .boxed()
                .collect(Collectors.toList());

        when(continuumLayer.getStateStream())
                .thenReturn(continuumValues.stream());
    }

    @Test
    public void highlightCreatesNewKey() throws Exception {
        query.highlight(c, 3);
        assertTrue(highlights.get(3).contains(c));
    }

    @Test
    public void highlightAddsCoordinate() throws Exception {
        HashSet<Coordinate> three = new HashSet<>();
        highlights.put(3, three);
        query.highlight(c, 3);
        assertTrue(three.contains(c));
    }

    @Test
    public void advanceClockDt() throws Exception {
        assertEquals(0.0, query.getDt(), epsilon);
        query.advanceClock(0.1);
        assertEquals(0.1, query.getDt(), epsilon);
    }

    @Test
    public void advanceClockTime() throws Exception {
        assertEquals(5.0, query.getTime(), epsilon);
        query.advanceClock(0.1);
        assertEquals(5.1, query.getTime(), epsilon);
    }

    @Test
    public void getHighlightsAsksMap() throws Exception {
        HashSet<Coordinate> cc = new HashSet<>();
        cc.add(c);
        highlights.put(3, cc);
        Stream<Coordinate> actual = query.getHighlights(3);
        assertStreamsEqual(cc.stream(), actual);
    }

    @Test
    public void getEmptyHighlightsReturnsEmptyStream() throws Exception {
        Stream<Coordinate> actual = query.getHighlights(3);
        assertStreamsEqual(Stream.empty(), actual);
    }

    @Test
    public void recordSetsRecordedFlag() throws Exception {
        assertFalse(query.isRecorded());
        query.record(lm);
        assertTrue(query.isRecorded());
    }

    @Test
    public void recordCapturesCellLayerClone() throws Exception {
        CellLayer expected = mock(CellLayer.class);
        when(cellLayer.clone()).thenReturn(expected);
        query.record(lm);
        CellLayer actual = query.getRecordedCellLayer();
        assertSame(expected, actual);
    }

    @Test
    public void recordCapturesContinuumState() throws Exception {
        query.record(lm);
        Stream<Double> expected = continuumValues.stream();
        Stream<Double> actual = query.getRecordedContinuumValues("test");
        assertStreamsEqual(expected, actual);
    }

    @Test
    public void testGetFrame() throws Exception {
        assertEquals(1, query.getFrame());
    }
}
