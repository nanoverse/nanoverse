package nanoverse.runtime.agent.action.stochastic;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.layers.cell.AgentLookupManager;
import org.junit.Before;
import org.junit.Test;
import test.TestBase;

import static org.junit.Assert.*;
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

        String[] neighborNames = new String[3];
        when(layer.getLookupManager().getNeighborNames(c, true))
                .thenReturn(neighborNames);

        double actual = query.getNeighborCount();
        assertEquals(3.0, actual, epsilon);
    }
}