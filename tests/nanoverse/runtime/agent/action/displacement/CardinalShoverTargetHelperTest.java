package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import org.junit.*;
import test.LayerMocks;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class CardinalShoverTargetHelperTest extends LayerMocks {

    private RandomNeighborChooser neighborChooser;
    private CardinalShoverTargetHelper query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        neighborChooser = mock(RandomNeighborChooser.class);
        query = new CardinalShoverTargetHelper(agentLayer, neighborChooser);
    }

    @Test
    public void getDisplacementToRandomTarget() throws Exception {
        Coordinate origin = mock(Coordinate.class);
        Coordinate target = mock(Coordinate.class);
        Coordinate expected = mock(Coordinate.class);
        when(neighborChooser.chooseRandomNeighbor(origin)).thenReturn(target);
        when(geometry.getDisplacement(origin, target, Geometry.APPLY_BOUNDARIES))
            .thenReturn(expected);

        Coordinate actual = query.getDisplacementToRandomTarget(origin);
        assertSame(expected, actual);
    }
}