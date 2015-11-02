package nanoverse.runtime.io.deserialize.agent;

import nanoverse.runtime.io.FileSystemManager;
import nanoverse.runtime.io.deserialize.TextInputHandle;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/25/2015.
 */
public class AgentNameDeindexerTest {

    private AgentNameDeindexer query;
    private Map<Integer, String> reverseIndex;

    @Before
    public void before() throws Exception {
        FileSystemManager fsManager = mock(FileSystemManager.class);
        TextInputHandle handle = mock(TextInputHandle.class);
        when(fsManager.readInstanceTextFile(anyString())).thenReturn(handle);

        reverseIndex = mock(Map.class);
        NameIndexReader reader = mock(NameIndexReader.class);
        when(reader.readReverseIndex(handle)).thenReturn(reverseIndex);
        query = new AgentNameDeindexer(fsManager, reader);
    }

    @Test
    public void testLifeCycle() throws Exception {
        String expected = "a";
        when(reverseIndex.get(1)).thenReturn(expected);
        String actual = query.deindex(1);
        assertSame(expected, actual);
    }

    @Test
    public void nullDeindexSpecial() throws Exception {
        when(reverseIndex.get(anyInt())).thenThrow(new RuntimeException());
        assertNull(query.deindex(0));
    }
}