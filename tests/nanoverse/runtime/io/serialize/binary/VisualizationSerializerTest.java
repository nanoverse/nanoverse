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

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.visual.Visualization;
import org.junit.Before;
import org.junit.Test;
import test.LayerMocks;

import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/28/2015.
 */
public class VisualizationSerializerTest extends LayerMocks {

    private GeneralParameters p;
    private VisualizationFrameRenderer renderer;
    private VisualizationSerializer query;
    private Visualization visualization;

    @Override @Before
    public void before() throws Exception {
        super.before();
        p = mock(GeneralParameters.class);
        renderer = mock(VisualizationFrameRenderer.class);
        visualization = mock(Visualization.class);
        query = new VisualizationSerializer(p, layerManager, visualization, renderer);
    }

    @Override
    public void verifyNothingHappened() throws Exception {
        super.verifyNothingHappened();
        verifyNoMoreInteractions(p, renderer, visualization);
    }

    @Test
    public void init() throws Exception {
        verifyNothingHappened();
    }

    @Test
    public void dispatchHalt() throws Exception {
        int[] channels = new int[0];
        when(visualization.getHighlightChannels()).thenReturn(channels);
        query.dispatchHalt(null);
        verify(renderer).renderAll(channels);
    }

    @Test
    public void close() throws Exception {
        verifyNothingHappened();
    }

    @Test
    public void flush() throws Exception {
        verifyNothingHappened();
    }

}