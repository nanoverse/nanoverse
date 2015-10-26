package nanoverse.runtime.io.serialize.binary;

import nanoverse.runtime.io.FileSystemManager;
import nanoverse.runtime.io.serialize.text.TextOutputHandle;
import org.junit.*;
import org.mockito.InOrder;

import java.util.stream.Stream;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class AgentNameIndexWriterTest {
    private FileSystemManager fsManager;
    private AgentNameIndexManager indexManager;
    private AgentNameIndexWriter query;

    @Before
    public void before() throws Exception {
        fsManager = mock(FileSystemManager.class);
        indexManager = mock(AgentNameIndexManager.class);
        query = new AgentNameIndexWriter(fsManager, indexManager);
    }

    @Test
    public void nullNameExcluded() throws Exception {
        fail();
    }

    @Test
    public void writeNameIndex() throws Exception {
        TextOutputHandle handle = mock(TextOutputHandle.class);
        when(fsManager.makeInstanceTextFile(AgentNameIndexWriter.INDEX_FILENAME))
            .thenReturn(handle);

        String name = "name";
        Stream<String> nameStream = Stream.of(name);
        when(indexManager.getNameStream()).thenReturn(nameStream);
        when(indexManager.getIndex(name)).thenReturn(5);

        query.writeNameIndex();
        InOrder inOrder = inOrder(handle);
        inOrder.verify(handle).write("5\tname\n");
        inOrder.verify(handle).close();
        inOrder.verifyNoMoreInteractions();
    }
}