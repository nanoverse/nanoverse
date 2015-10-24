package nanoverse.runtime.io;

import nanoverse.runtime.io.deserialize.*;
import nanoverse.runtime.io.serialize.binary.BinaryOutputHandle;
import nanoverse.runtime.io.serialize.text.TextOutputHandle;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FileSystemManagerTest {

    private static final String NAME = "name";

    private FileSystemOutputManager outputManager;
    private FileSystemInputManager inputManager;
    private FileSystemManager query;

    @Before
    public void before() throws Exception {
        outputManager = mock(FileSystemOutputManager.class);
        inputManager = mock(FileSystemInputManager.class);
        query = new FileSystemManager(outputManager, inputManager);
    }

    @Test
    public void makeProjectBinaryFile() throws Exception {
        BinaryOutputHandle expected = mock(BinaryOutputHandle.class);
        when(outputManager.makeProjectBinaryFile(NAME)).thenReturn(expected);
        BinaryOutputHandle actual = query.makeProjectBinaryFile(NAME);
        assertSame(expected, actual);
    }

    @Test
    public void makeInstanceBinaryFile() throws Exception {
        BinaryOutputHandle expected = mock(BinaryOutputHandle.class);
        when(outputManager.makeInstanceBinaryFile(NAME)).thenReturn(expected);
        BinaryOutputHandle actual = query.makeInstanceBinaryFile(NAME);
        assertSame(expected, actual);
    }

    @Test
    public void makeProjectTextFile() throws Exception {
        TextOutputHandle expected = mock(TextOutputHandle.class);
        when(outputManager.makeProjectTextFile(NAME)).thenReturn(expected);
        TextOutputHandle actual = query.makeProjectTextFile(NAME);
        assertSame(expected, actual);
    }

    @Test
    public void makeInstanceTextFile() throws Exception {
        TextOutputHandle expected = mock(TextOutputHandle.class);
        when(outputManager.makeInstanceTextFile(NAME)).thenReturn(expected);
        TextOutputHandle actual = query.makeInstanceTextFile(NAME);
        assertSame(expected, actual);
    }

    @Test
    public void readProjectBinaryFile() throws Exception {
        BinaryInputHandle expected = mock(BinaryInputHandle.class);
        when(inputManager.readProjectBinaryFile(NAME)).thenReturn(expected);
        BinaryInputHandle actual = query.readProjectBinaryFile(NAME);
        assertSame(expected, actual);
    }

    @Test
    public void readInstanceBinaryFile() throws Exception {
        BinaryInputHandle expected = mock(BinaryInputHandle.class);
        when(inputManager.readInstanceBinaryFile(NAME)).thenReturn(expected);
        BinaryInputHandle actual = query.readInstanceBinaryFile(NAME);
        assertSame(expected, actual);
    }

    @Test
    public void readProjectTextFile() throws Exception {
        TextInputHandle expected = mock(TextInputHandle.class);
        when(inputManager.readProjectTextFile(NAME)).thenReturn(expected);
        TextInputHandle actual = query.readProjectTextFile(NAME);
        assertSame(expected, actual);
    }

    @Test
    public void readInstanceTextFile() throws Exception {
        TextInputHandle expected = mock(TextInputHandle.class);
        when(inputManager.readInstanceTextFile(NAME)).thenReturn(expected);
        TextInputHandle actual = query.readInstanceTextFile(NAME);
        assertSame(expected, actual);
    }
}