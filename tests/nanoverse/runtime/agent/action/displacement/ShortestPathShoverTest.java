package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import org.junit.*;
import test.LayerMocks;

import java.util.*;
import java.util.stream.*;

import static org.mockito.Mockito.*;

public class ShortestPathShoverTest extends LayerMocks {

    private ShoveHelper shoveHelper;
    private ShoveOperationManager operationManager;
    private Coordinate origin, target, displacement;

    private ShortestPathShover query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        shoveHelper = mock(ShoveHelper.class);
        operationManager = mock(ShoveOperationManager.class);
        query = new ShortestPathShover(agentLayer, shoveHelper, operationManager);

        origin = mock(Coordinate.class);
        target = mock(Coordinate.class);
        displacement = mock(Coordinate.class);
        when(geometry.getDisplacement(origin, target, Geometry.APPLY_BOUNDARIES))
            .thenReturn(displacement);
    }

    @Test
    public void doShove() throws Exception {
        doAnswer(invocation -> {
            HashSet<Coordinate> affectedSites = (HashSet<Coordinate>) invocation.getArguments()[2];
            affectedSites.add(origin);
            return null;
        }).when(operationManager).doShove(eq(origin), eq(displacement), any(HashSet.class));

        Set<Coordinate> expected = Stream.of(origin).collect(Collectors.toSet());
        HashSet<Coordinate> actual = query.shove(origin, target);
        assertSetsEqual(expected, actual);
    }
}