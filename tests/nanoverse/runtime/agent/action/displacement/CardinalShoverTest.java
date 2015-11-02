package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;
import test.TestBase;

import java.util.*;
import java.util.stream.*;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class CardinalShoverTest extends TestBase {

    private CardinalShoverTargetHelper targetHelper;
    private ShoveHelper shoveHelper;
    private ShoveOperationManager operationManager;
    private Coordinate origin, displacement;

    private CardinalShover query;

    @Before
    public void before() throws Exception {
        targetHelper = mock(CardinalShoverTargetHelper.class);
        shoveHelper = mock(ShoveHelper.class);
        operationManager = mock(ShoveOperationManager.class);
        query = new CardinalShover(targetHelper, shoveHelper, operationManager);

        origin = mock(Coordinate.class);
        displacement = mock(Coordinate.class);
    }

    @Test
    public void shoveRandom() throws Exception {
        when(targetHelper.getDisplacementToRandomTarget(origin))
            .thenReturn(displacement);

        query.shoveRandom(origin);
        verify(operationManager).doShove(eq(origin), eq(displacement), any());
    }

    @Test
    public void doShove() throws Exception {
        doAnswer(invocation -> {
            HashSet<Coordinate> affectedSites = (HashSet<Coordinate>) invocation.getArguments()[2];
            affectedSites.add(origin);
            return null;
        }).when(operationManager).doShove(eq(origin), eq(displacement), any(HashSet.class));

        Set<Coordinate> expected = Stream.of(origin).collect(Collectors.toSet());
        HashSet<Coordinate> actual = query.doShove(origin, displacement);
        assertSetsEqual(expected, actual);
    }
}