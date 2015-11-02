package nanoverse.runtime.io;

import org.junit.*;

import java.io.File;
import java.util.function.Function;

import static org.mockito.Mockito.*;

public class DirectoryMakerTest {

    private Function<String, File> pathMaker;
    private File path;
    private DirectoryMaker query;

    @Before
    public void before() throws Exception {
        pathMaker = mock(Function.class);
        path = mock(File.class);
        when(pathMaker.apply(anyString())).thenReturn(path);
        query = new DirectoryMaker(pathMaker);
    }

    @Test
    public void makeDirectory() throws Exception {
        when(path.exists()).thenReturn(false);
        query.makeDirectory("");
        verify(path).mkdirs();
    }

    @Test
    public void makeExistingSkips() throws Exception {
        when(path.exists()).thenReturn(true);
        query.makeDirectory("");
        verify(path, never()).mkdirs();
    }
}