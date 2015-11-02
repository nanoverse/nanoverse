package nanoverse.runtime.io.deserialize;

import nanoverse.runtime.io.serialize.text.TextOutputHandle;
import org.junit.*;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.*;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextInputHandle.class)
public class TextInputHandleTest {

    private BufferedReader bw;
    private TextInputHandle query;

    @Before
    public void before() throws Exception {
        bw = mock(BufferedReader.class);
        query = new TextInputHandle(bw);
    }

    @Test
    public void readLine() throws Exception {
        String expected = "line";
        when(bw.readLine()).thenReturn(expected);
        String actual = query.readLine();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readLineIOExceptionHandled() throws Exception {
        doThrow(RuntimeException.class).when(bw.readLine());
        query.readLine();
    }

    @Test
    public void lines() throws Exception {
        Stream expected = mock(Stream.class);
        when(bw.lines()).thenReturn(expected);
        Stream actual = query.lines();
        assertSame(expected, actual);
    }

    @Test
    public void close() throws Exception {
        query.close();
        verify(bw).close();
    }

    @Test(expected = RuntimeException.class)
    public void closeIOExceptionHandled() throws Exception {
        doThrow(IOException.class).when(bw).close();
        query.close();
    }
}