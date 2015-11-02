package nanoverse.runtime.agent.action.helper;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;
import test.LayerMocks;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ActionIdentityManagerTest extends LayerMocks {

    private Agent self;
    private ActionIdentityManager query;

    @Before
    public void before() throws Exception {
        super.before();
        self = mock(Agent.class);
        query = new ActionIdentityManager(self, agentLayer);
    }

    @Test
    public void getOwnLocation() throws Exception {
        when(viewer.exists(self)).thenReturn(true);

        Coordinate expected = mock(Coordinate.class);
        when(lookup.getAgentLocation(self)).thenReturn(expected);

        Coordinate actual = query.getOwnLocation();
        assertSame(expected, actual);
    }

    @Test
    public void missingSelfReturnsNullCoordinate() throws Exception {
        when(viewer.exists(self)).thenReturn(true);
        Coordinate actual = query.getOwnLocation();
        assertNull(actual);
    }

    @Test
    public void getSelf() throws Exception {
        Agent actual = query.getSelf();
        assertSame(self, actual);
    }


    @Test
    public void selfExists() throws Exception {
        when(viewer.exists(self)).thenReturn(true);
        assertTrue(query.selfExists());
    }
}