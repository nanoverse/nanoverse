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