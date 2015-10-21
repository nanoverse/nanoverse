package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;
import test.LayerMocks;

import java.util.Set;
import java.util.stream.*;

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
        Set<Coordinate> imaginarySites = Stream.of(c).collect(Collectors.toSet());
        when(viewer.getImaginarySites()).thenReturn(imaginarySites);
        query.removeImaginary();
        verify(update).banish(c);
    }
}