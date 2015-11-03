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

package nanoverse.runtime.io.deserialize;

import nanoverse.runtime.control.identifiers.Extrema;
import nanoverse.runtime.io.deserialize.agent.AgentNameIterator;
import nanoverse.runtime.io.deserialize.agent.AgentNameViewer;
import nanoverse.runtime.io.deserialize.continuum.ContinuumLayerViewer;
import nanoverse.runtime.io.deserialize.continuum.ContinuumStateReader;
import nanoverse.runtime.layers.LightweightSystemState;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/26/2015.
 */
public class SystemStateIteratorTest {
    private static final double TIME = 0.5;
    private static final int FRAME = 1;

    private HighlightReader highlightReader;
    private ContinuumStateReader continuumStateReader;
    private AgentNameIterator nameIterator;
    private double[] times;
    private int[] frames;
    private CoordinateDeindexer deindexer;
    private SystemStateIterator query;
    private LightweightSystemState target;
    private Stream<String> names;
    private Map<String, Extrema> extremaMap;
    private ContinuumLayerViewer continuumViewer;

    @Before
    public void before() throws Exception {
        highlightReader = mock(HighlightReader.class);
        continuumStateReader = mock(ContinuumStateReader.class);
        nameIterator = mock(AgentNameIterator.class);
        times = new double[] {TIME};
        frames = new int[] {FRAME};
        deindexer = mock(CoordinateDeindexer.class);
        names = mockNames();
        extremaMap = mockExtrema();
        continuumViewer = mockContinuumViewer();

        target = mock(LightweightSystemState.class);
        Supplier<LightweightSystemState> constructor = () -> target;
        query = new SystemStateIterator(highlightReader, continuumStateReader, nameIterator, constructor, times, frames, deindexer);
    }

    @Test
    public void getTimes() throws Exception {
        assertSame(times, query.getTimes());
    }

    @Test
    public void getFrames() throws Exception {
        assertSame(frames, query.getFrames());
    }

    @Test
    public void hasNext() throws Exception {
        assertTrue(query.hasNext());
        query.next();
        assertFalse(query.hasNext());
    }

    @Test(expected = IllegalStateException.class)
    public void nextAfterEndThrows() throws Exception {
        query.next();
        query.next();
    }

    @Test
    public void nextSetsAllMembers() throws Exception {
        query.next();
        verify(target).setTime(TIME);
        verify(target).setFrame(FRAME);
        verify(target).setAgentNames(names);
        verify(target).setContinuumLayerViewer(continuumViewer);
        verify(target).setExtremaMap(extremaMap);
        verify(highlightReader).populate(target);

    }
    @Test
    public void nextSuppliesFromConstructor() throws Exception {
        LightweightSystemState actual = query.next();
        assertSame(target, actual);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void removeNotImplemented() throws Exception {
        query.remove();
    }

    private ContinuumLayerViewer mockContinuumViewer() {
        ContinuumLayerViewer viewer = mock(ContinuumLayerViewer.class);
        when(continuumStateReader.next()).thenReturn(viewer);
        return viewer;
    }

    private Map<String, Extrema> mockExtrema() {
        Map<String, Extrema> extremaMap = mock(Map.class);
        when(continuumStateReader.getExtremaMap()).thenReturn(extremaMap);
        return extremaMap;
    }


    private Stream<String> mockNames() {
        AgentNameViewer viewer = mock(AgentNameViewer.class);
        when(nameIterator.next()).thenReturn(viewer);
        Stream<String> names = mock(Stream.class);
        when(viewer.getNames()).thenReturn(names);
        return names;
    }

}