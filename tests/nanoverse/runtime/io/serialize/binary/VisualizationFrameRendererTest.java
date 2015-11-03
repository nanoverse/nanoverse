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

package nanoverse.runtime.io.serialize.binary;

import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.io.deserialize.SystemStateReader;
import nanoverse.runtime.io.visual.Visualization;
import nanoverse.runtime.layers.LightweightSystemState;
import nanoverse.runtime.layers.SystemState;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Iterator;
import java.util.function.Function;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/28/2015.
 */
public class VisualizationFrameRendererTest {

    private Visualization visualization;
    private Geometry geometry;
    private VisualizationFileGenerator generator;
    private Function<int[], SystemStateReader> readerMaker;
    private VisualizationFrameRenderer query;

    private int[] highlights;
    private LightweightSystemState state;
    private double[] times;
    private int[] frames;

    @Before
    public void before() throws Exception {
        visualization = mock(Visualization.class);
        geometry = mock(Geometry.class);
        generator = mock(VisualizationFileGenerator.class);
        readerMaker = mock(Function.class);
        query = new VisualizationFrameRenderer(visualization, geometry,
                readerMaker, generator);

        highlights = new int[0];
        SystemStateReader reader = mock(SystemStateReader.class);
        when(readerMaker.apply(highlights)).thenReturn(reader);

        times = new double[0];
        when(reader.getTimes()).thenReturn(times);

        frames = new int[0];
        when(reader.getFrames()).thenReturn(frames);
        state = mock(LightweightSystemState.class);

        Iterator<LightweightSystemState> iterator = makeIterator(state);
        when(reader.iterator()).thenReturn(iterator);
    }

    @Test
    public void renderAll() throws Exception {
        Image image = mock(Image.class);
        when(visualization.render(state)).thenReturn(image);

        double time = 0.5;
        when(state.getTime()).thenReturn(0.5);

        query.renderAll(highlights);
        verify(visualization).init(geometry, times, frames);
        verify(generator).generateFile(time, image);
    }

    @Test
    public void renderAllImageNull() throws Exception {
        when(visualization.render(state)).thenReturn(null);

        double time = 0.5;
        when(state.getTime()).thenReturn(0.5);

        query.renderAll(highlights);
        verify(visualization).init(geometry, times, frames);
        verify(generator, never()).generateFile(anyDouble(), any(Image.class));

    }
    private Iterator<LightweightSystemState> makeIterator(LightweightSystemState state) {
        Iterator ret = mock(Iterator.class);
        when(ret.next()).thenReturn(state);
        when(ret.hasNext()).thenReturn(true, false);
        return ret;
    }
}