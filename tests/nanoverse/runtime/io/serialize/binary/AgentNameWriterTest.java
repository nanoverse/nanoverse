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
import nanoverse.runtime.processes.StepState;
import org.junit.*;
import test.LayerMocks;

import static org.mockito.Mockito.*;

public class AgentNameWriterTest extends LayerMocks {

    private AgentNameIndexManager indexManager;
    private AgentNameIOHelper ioHelper;
    private GeneralParameters p;
    private AgentNameWriter query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        p = mock(GeneralParameters.class);
        indexManager = mock(AgentNameIndexManager.class);
        ioHelper = mock(AgentNameIOHelper.class);
        query = new AgentNameWriter(p, layerManager, indexManager, ioHelper);
    }

    @Test
    public void init() throws Exception {
        query.init();
        verify(indexManager).init();
        verify(ioHelper).init();
    }

    @Test
    public void dispatchHalt() throws Exception {
        query.dispatchHalt(null);
        verify(ioHelper).conclude();
    }

    @Test
    public void close() throws Exception {
        query.close();
        verifyNoMoreInteractions(indexManager, ioHelper, p, layerManager);
    }

    @Test
    public void flush() throws Exception {
        StepState state = mock(StepState.class);
        query.flush(state);
        verify(ioHelper).commit(state);
    }
}