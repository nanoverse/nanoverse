package nanoverse.runtime.io;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.deserialize.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FileSystemInputManagerTest {

    private static final String PATH = "path";
    private static final String INST_PATH = "instPath";
    private static final String NAME = "name";

    private GeneralParameters p;
    private DiskInputManager manager;
    private FileSystemInputManager query;

    @Before
    public void before() throws Exception {
        p = mock(GeneralParameters.class);
        when(p.getPath()).thenReturn(PATH);
        when(p.getInstancePath()).thenReturn(INST_PATH);

        manager = mock(DiskInputManager.class);
        query = new FileSystemInputManager(p, manager);
    }

    @Test
    public void readProjectBinaryFile() throws Exception {
        BinaryInputHandle expected = mock(BinaryInputHandle.class);
        when(manager.doMakeBinaryReader(PATH, NAME)).thenReturn(expected);

        BinaryInputHandle actual = query.readProjectBinaryFile(NAME);
        assertSame(expected, actual);
    }

    @Test
    public void readInstanceBinaryFile() throws Exception {
        BinaryInputHandle expected = mock(BinaryInputHandle.class);
        when(manager.doMakeBinaryReader(INST_PATH, NAME)).thenReturn(expected);

        BinaryInputHandle actual = query.readInstanceBinaryFile(NAME);
        assertSame(expected, actual);
    }

    @Test
    public void readProjectTextFile() throws Exception {
        TextInputHandle expected = mock(TextInputHandle.class);
        when(manager.doMakeTextReader(PATH, NAME)).thenReturn(expected);

        TextInputHandle actual = query.readProjectTextFile(NAME);
        assertSame(expected, actual);
    }

    @Test
    public void readInstanceTextFile() throws Exception {
        TextInputHandle expected = mock(TextInputHandle.class);
        when(manager.doMakeTextReader(INST_PATH, NAME)).thenReturn(expected);

        TextInputHandle actual = query.readInstanceTextFile(NAME);
        assertSame(expected, actual);
    }
}