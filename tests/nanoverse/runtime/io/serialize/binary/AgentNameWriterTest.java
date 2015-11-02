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