package nanoverse.runtime.io.deserialize.agent;

import nanoverse.runtime.io.FileSystemManager;
import nanoverse.runtime.io.deserialize.BinaryInputHandle;
import nanoverse.runtime.structural.utilities.ParityIO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import test.TestBase;

import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/25/2015.
 */
public class AgentNameIteratorTest extends TestBase {

    private static final int NUM_SITES = 2;

    private BinaryInputHandle vectorHandle;
    private ParityIO parityIO;
    private AgentNameDeindexer deindexer;

    private AgentNameIterator query;

    @Before
    public void before() throws Exception {
        vectorHandle = mock(BinaryInputHandle.class);
        parityIO = mock(ParityIO.class);
        deindexer = mock(AgentNameDeindexer.class);
    }

    private void initQuery(boolean hasNext) {
        FileSystemManager fsManager = mock(FileSystemManager.class);
        when(fsManager.readInstanceBinaryFile(any())).thenReturn(vectorHandle);
        when(parityIO.readStartOrEOF(vectorHandle)).thenReturn(hasNext);
        query = new AgentNameIterator(fsManager, deindexer, parityIO, NUM_SITES);
    }

    @Test
    public void hasNextInitialYes() throws Exception {
        initQuery(true);
        assertTrue(query.hasNext());
    }

    @Test
    public void hasNextInitialNo() throws Exception {
        initQuery(false);
        assertFalse(query.hasNext());
    }

    @Test
    public void nextGetsNameVector() throws Exception {
        AgentNameViewer viewer = getValue(true);
        Stream<String> expected = Stream.of("a", "b");
        Stream<String> actual = viewer.getNames();
        assertStreamsEqual(expected, actual);
    }

    @Test
    public void nextChecksHasNext() throws Exception {
        initQuery(true);
        getValue(false);
        assertFalse(query.hasNext());
    }
    @Test
    public void initAndNextCheckParity() throws Exception {
        getValue(false);
        verify(parityIO, times(2)).readStartOrEOF(vectorHandle);
        verify(parityIO).readEnd(vectorHandle);
        assertFalse(query.hasNext());
    }

    @Test(expected = IllegalStateException.class)
    public void nextPastEndThrows() throws Exception {
        initQuery(false);
        query.next();
    }

    @Test(expected = IllegalStateException.class)
    public void siteCountMismatchThrows() throws Exception {
        initQuery(true);

        // Second element in sequence should be "2"
        when(vectorHandle.readInt()).thenReturn(1, 1, 3, 5);
        query.next();
    }

    private AgentNameViewer getValue(boolean hasNextAfterGet) throws Exception {
        // First one is time; second is site count; next two are indices
        when(vectorHandle.readInt()).thenReturn(1, 2, 3, 5);
        when(vectorHandle.readDouble()).thenReturn(6.0);
        when(deindexer.deindex(3)).thenReturn("a");
        when(deindexer.deindex(5)).thenReturn("b");
        initQuery(true);
        when(parityIO.readStartOrEOF(vectorHandle)).thenReturn(hasNextAfterGet);
        AgentNameViewer viewer = query.next();
        return viewer;
    }

}