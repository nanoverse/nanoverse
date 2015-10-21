package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;

import java.util.HashSet;
import java.util.function.BiFunction;

import static org.mockito.Mockito.*;

public class ShoveOperationManagerTest {

    private ShoveHelper helper;
    private BiFunction<Coordinate, Coordinate, Boolean> isBaseCase;
    private ShoveOperationManager query;
    private Coordinate c, d;
    private HashSet<Coordinate> sites;

    @Before
    public void before() throws Exception {
        helper = mock(ShoveHelper.class);
        isBaseCase = mock(BiFunction.class);
        query = new ShoveOperationManager(helper, isBaseCase);
        c = mock(Coordinate.class);
        d = mock(Coordinate.class);
        sites = new HashSet<>();
    }

    @Test
    public void baseCase() throws Exception {
        when(isBaseCase.apply(any(), any())).thenReturn(true);
        query.doShove(c, d, sites);
        verifyZeroInteractions(helper);
    }

    @Test
    public void recursiveCase() throws Exception {
        when(isBaseCase.apply(any(), any())).thenReturn(false, false, true);
        when(helper.getNextLocation(c, d)).thenReturn(c);
        query.doShove(c, d, sites);
        verify(helper, times(2)).swap(c, c);
    }
}