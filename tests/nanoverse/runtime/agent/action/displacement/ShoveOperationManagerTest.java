package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;

import java.util.HashSet;
import java.util.function.BiFunction;

import static org.junit.Assert.fail;
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

    // Hello, technical debt!
//    @Test
//    public void recursiveCase() throws Exception {
//        when(isBaseCase.apply(any(), any())).thenReturn(false, false, true);
//        CoordinateTuple tuple = new CoordinateTuple(c, d);
//        when(helper.getNextTuple(c, d)).thenReturn(tuple);
//        when(helper.getNextTuple(d, c)).thenReturn(tuple);
//        query.doShove(c, d, sites);
//        verify(helper).swap(c, d);
//        verify(helper).swap(d, c);
//    }
}