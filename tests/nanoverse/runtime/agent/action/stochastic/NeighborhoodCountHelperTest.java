package nanoverse.runtime.agent.action.stochastic;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.*;
import org.junit.*;
import test.TestBase;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/13/2015.
 */
public class NeighborhoodCountHelperTest extends TestBase {

    private AgentLayer layer;
    private Agent agent;

    private NeighborhoodCountHelper query;

    @Before
    public void before() throws Exception {
        agent = mock(Agent.class);
        layer = mock(AgentLayer.class);
        query = new NeighborhoodCountHelper(layer, agent);
    }

    @Test
    public void testGetNeighborCount() throws Exception {
        AgentLookupManager lm = mock(AgentLookupManager.class);
        when(layer.getLookupManager()).thenReturn(lm);

        Coordinate c = mock(Coordinate.class);
        when(lm.getAgentLocation(agent)).thenReturn(c);

        Stream<String> neighborNames = Stream.of("a", "b", "c");
        when(layer.getLookupManager().getNeighborNames(c, true))
                .thenReturn(neighborNames);

        double actual = query.getNeighborCount();
        assertEquals(3.0, actual, epsilon);
    }
}