package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;
import test.LayerMocks;

import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class ImaginarySiteCleanerTest extends LayerMocks {

    private ImaginarySiteCleaner query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        query = new ImaginarySiteCleaner(agentLayer);
    }

    @Test
    public void removeImaginary() throws Exception {
        Coordinate c = mock(Coordinate.class);
        Stream<Coordinate> imaginarySites = Stream.of(c);
        when(viewer.getImaginarySites()).thenReturn(imaginarySites);
        query.removeImaginary();
        verify(update).banish(c);
    }
}