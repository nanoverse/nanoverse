package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;

import static org.mockito.Mockito.*;

public class ShoveManagerTest {

    private CardinalShover cardinalShover;
    private ShortestPathShover shortestPathShover;
    private WeightedShover weightedShover;
    private Coordinate origin, target;
    private ShoveManager query;

    @Before
    public void before() throws Exception {
        cardinalShover = mock(CardinalShover.class);
        shortestPathShover = mock(ShortestPathShover.class);
        weightedShover = mock(WeightedShover.class);
        origin = mock(Coordinate.class);
        target = mock(Coordinate.class);

        query = new ShoveManager(cardinalShover, shortestPathShover, weightedShover);
    }

    @Test
    public void shove() throws Exception {
        query.shove(origin, target);
        verify(shortestPathShover).shove(origin, target);
    }

    @Test
    public void shoveRandom() throws Exception {
        query.shoveRandom(origin);
        verify(cardinalShover).shoveRandom(origin);
    }

    @Test
    public void shoveWeighted() throws Exception {
        query.shoveWeighted(origin);
        verify(weightedShover).shoveWeighted(origin);
    }
}