package nanoverse.runtime.agent.action.helper;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import org.junit.*;

import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CoordAgentMapperTest {

    private AgentResolver resolver;
    private CoordAgentMapper query;

    @Before
    public void before() throws Exception {
        resolver = mock(AgentResolver.class);
        query = new CoordAgentMapper(resolver);
    }

    @Test
    public void resolveCaller() throws Exception {
        Coordinate c = mock(Coordinate.class);
        Supplier<Agent> trigger = () -> query.resolveCaller(c);
        doResolveTest(c, trigger);
    }

    private void doResolveTest(Coordinate c, Supplier<Agent> trigger) {
        Agent expected = mock(Agent.class);
        when(resolver.resolveAgent(c)).thenReturn(expected);

        Agent actual = trigger.get();
        assertSame(expected, actual);
    }

    @Test
    public void nullCallerReturnsNullAgent() throws Exception {
        Agent actual = query.resolveAgent(null);
        assertNull(actual);
    }

    @Test
    public void resolveAgent() throws Exception {
        Coordinate c = mock(Coordinate.class);
        Supplier<Agent> trigger = () -> query.resolveAgent(c);
        doResolveTest(c, trigger);
    }

    @Test
    public void getLayerManager() throws Exception {
        LayerManager expected = mock(LayerManager.class);
        when(resolver.getLayerManager()).thenReturn(expected);

        LayerManager actual = query.getLayerManager();
        assertSame(expected, actual);
    }
}