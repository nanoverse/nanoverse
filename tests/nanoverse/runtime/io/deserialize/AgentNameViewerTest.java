package nanoverse.runtime.io.deserialize;

import nanoverse.runtime.io.deserialize.agent.AgentNameViewer;
import org.junit.Before;
import org.junit.Test;
import test.TestBase;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/25/2015.
 */
public class AgentNameViewerTest extends TestBase {

    private List<String> names;
    private double time;
    private int frameNumber;
    private AgentNameViewer query;

    @Before
    public void before() throws Exception {
        names = mock(List.class);
        time = 1.0;
        frameNumber = 1;
        query = new AgentNameViewer(names, time, frameNumber);
    }

    @Test
    public void getNames() throws Exception {
        Stream<String> expected = mock(Stream.class);
        when(names.stream()).thenReturn(expected);
        Stream<String> actual = query.getNames();
        assertSame(expected, actual);
    }

    @Test
    public void getTime() throws Exception {
        assertEquals(time, query.getTime(), epsilon);
    }

    @Test
    public void getFrameNumber() throws Exception {
        assertEquals(frameNumber, query.getFrameNumber());
    }

    @Test
    public void getName() throws Exception {
        when(names.get(3)).thenReturn("test");
        assertEquals("test", query.getName(3));
    }
}