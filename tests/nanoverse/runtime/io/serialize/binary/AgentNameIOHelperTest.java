package nanoverse.runtime.io.serialize.binary;

import nanoverse.runtime.io.FileSystemManager;
import nanoverse.runtime.processes.StepState;
import org.junit.*;

import static org.mockito.Mockito.*;

public class AgentNameIOHelperTest {

    private FileSystemManager fsManager;
    private AgentNameIndexWriter indexWriter;
    private AgentNameIOHelper query;
    private BinaryOutputHandle nameVectorFile;
    private AgentNameCommitHelper commitHelper;

    @Before
    public void before() throws Exception {
        commitHelper = mock(AgentNameCommitHelper.class);
        fsManager = mock(FileSystemManager.class);
        indexWriter = mock(AgentNameIndexWriter.class);
        nameVectorFile = mock(BinaryOutputHandle.class);
        query = new AgentNameIOHelper(commitHelper, fsManager, indexWriter);

        String name = AgentNameIOHelper.VECTOR_FILENAME;
        when(fsManager.makeInstanceBinaryFile(name))
            .thenReturn(nameVectorFile);
    }

    @Test
    public void initCommit() throws Exception {
        query.init();
        StepState state = mock(StepState.class);
        query.commit(state);
        verify(commitHelper).commit(nameVectorFile, state);
    }

    @Test
    public void initConclude() throws Exception {
        query.init();
        query.conclude();
        verify(nameVectorFile).close();
        verify(indexWriter).writeNameIndex();
    }
}