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

import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.structural.utilities.ParityIO;
import org.junit.*;
import org.mockito.InOrder;
import test.LayerMocks;

import java.util.stream.*;

import static org.mockito.Mockito.*;

public class AgentNameCommitHelperTest extends LayerMocks {

    private ParityIO parity;
    private AgentNameIndexManager indexManager;
    private AgentNameCommitHelper query;
    private StepState state;
    private double time;
    private int frame;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        parity = mock(ParityIO.class);
        indexManager = mock(AgentNameIndexManager.class);
        query = new AgentNameCommitHelper(parity, indexManager);

        state = mock(StepState.class);
        when(state.getRecordedAgentLayer()).thenReturn(agentLayer);

        time = 1.0;
        when(state.getTime()).thenReturn(time);

        frame = 2;
        when(state.getFrame()).thenReturn(frame);

        when(indexManager.getIndexStream(viewer)).thenAnswer(invocation -> IntStream.range(0, 3).boxed());
    }

    @Test
    public void commit() throws Exception {
        BinaryOutputHandle file = mock(BinaryOutputHandle.class);

        query.commit(file, state);

        InOrder inOrder = inOrder(parity, file);
        inOrder.verify(parity).writeStart(file);
        inOrder.verify(file).writeDouble(time);
        inOrder.verify(file).writeInt(frame);
        inOrder.verify(file).writeInt(3);
        IntStream.range(0, 3)
            .forEach(i ->
                inOrder.verify(file).writeInt(i));
        inOrder.verify(parity).writeEnd(file);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void close() throws Exception {
        BinaryOutputHandle file = mock(BinaryOutputHandle.class);
        query.close(file);
        verify(parity).writeEOF(file);
    }
}