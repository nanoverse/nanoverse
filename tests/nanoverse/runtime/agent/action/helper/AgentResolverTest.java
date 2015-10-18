package nanoverse.runtime.agent.action.helper;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import org.junit.*;
import test.LayerMocks;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AgentResolverTest extends LayerMocks {

    private AgentResolver query;
    private Coordinate c;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        c = mock(Coordinate.class);
        query = new AgentResolver(layerManager);
    }

    @Test
    public void resolveAgent() throws Exception {
        when(viewer.isOccupied(c)).thenReturn(true);

        Agent expected = mock(Agent.class);
        when(viewer.getAgent(c)).thenReturn(expected);

        Agent actual = query.resolveAgent(c);
        assertSame(expected, actual);
    }

    @Test(expected = IllegalStateException.class)
    public void resolveNullThrows() throws Exception {
        query.resolveAgent(null);
    }

    @Test
    public void resolveVacantReturnsNull() throws Exception {
        when(viewer.isOccupied(c)).thenReturn(true);
        Agent actual = query.resolveAgent(c);
        assertNull(actual);
    }

    @Test
    public void getLayerManager() throws Exception {
        LayerManager actual = query.getLayerManager();
        assertSame(layerManager, actual);
    }
}