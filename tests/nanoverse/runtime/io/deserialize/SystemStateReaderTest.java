package nanoverse.runtime.io.deserialize;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/26/2015.
 */
public class SystemStateReaderTest {

    private SystemStateIterator iterator;
    private SystemStateReader query;

    @Before
    public void before() throws Exception {
        iterator = mock(SystemStateIterator.class);
        query = new SystemStateReader(iterator);
    }

    @Test
    public void getTimes() throws Exception {
        double[] times = new double[0];
        when(iterator.getTimes()).thenReturn(times);
        assertSame(times, query.getTimes());
    }

    @Test
    public void getFrames() throws Exception {
        int[] frames = new int[0];
        when(iterator.getFrames()).thenReturn(frames);
        assertSame(frames, query.getFrames());
    }

    @Test
    public void iterator() throws Exception {
        assertSame(iterator, query.iterator());
    }
}