package nanoverse.runtime.io.visual.color;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.SystemState;
import org.junit.Before;
import org.junit.Test;
import test.LayerMocks;

import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/28/2015.
 */
public class InteriorCheckerTest extends LayerMocks {
    private InteriorChecker query;
    private Coordinate c;
    private SystemState state;

    @Override @Before
    public void before() throws Exception {
        super.before();
        c = mock(Coordinate.class);
        state = mock(SystemState.class);
        when(state.getLayerManager()).thenReturn(layerManager);

        query = new InteriorChecker();
    }

    @Test
    public void isInteriorYes() throws Exception {
        Stream<String> names = Stream.of("test");
        when(lookup.getNeighborNames(c, false)).thenReturn(names);
        assertTrue(query.isInterior(c, state));
    }

    @Test
    public void isInteriorNo() throws Exception {
        Stream<String> names = Stream.of("test", null);
        when(lookup.getNeighborNames(c, false)).thenReturn(names);
        assertFalse(query.isInterior(c, state));

    }
}