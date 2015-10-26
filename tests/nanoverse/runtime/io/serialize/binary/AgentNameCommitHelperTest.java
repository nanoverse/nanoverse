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

        frame = 3;
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
}