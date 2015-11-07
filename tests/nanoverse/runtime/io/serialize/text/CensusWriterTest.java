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

package nanoverse.runtime.io.serialize.text;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.processes.StepState;
import org.junit.*;
import test.LayerMocks;

import static org.mockito.Mockito.*;

public class CensusWriterTest extends LayerMocks {

    private CensusFlushHelper flushHelper;
    private CensusWriteHelper writeHelper;
    private GeneralParameters p;
    private CensusWriter query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        flushHelper = mock(CensusFlushHelper.class);
        writeHelper = mock(CensusWriteHelper.class);
        p = mock(GeneralParameters.class);
        query = new CensusWriter(p, layerManager, flushHelper, writeHelper);
    }

    @Test
    public void init() throws Exception {
        query.init();
        verify(flushHelper).init();
    }

    @Test
    public void dispatchHalt() throws Exception {
        HaltCondition ex = mock(HaltCondition.class);
        when(ex.getGillespie()).thenReturn(1.0);
        query.dispatchHalt(ex);
        verify(flushHelper).doFlush(agentLayer, 1);
        verify(writeHelper).commit();
    }

    @Test
    public void close() throws Exception {
        query.close();
        verifyNoMoreInteractions(flushHelper, writeHelper);
    }

    @Test
    public void flush() throws Exception {
        AgentLayer recorded = mock(AgentLayer.class);
        StepState state = mock(StepState.class);
        when(state.getRecordedAgentLayer()).thenReturn(recorded);
        when(state.getFrame()).thenReturn(1);
        query.flush(state);
        verify(flushHelper).doFlush(recorded, 1);
    }
}