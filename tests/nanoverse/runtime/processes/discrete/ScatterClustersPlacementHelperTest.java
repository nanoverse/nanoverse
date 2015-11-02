package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.arguments.AgentDescriptor;
import nanoverse.runtime.control.halt.LatticeFullEvent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.processes.discrete.cluster.SeparationStrategyManager;
import org.junit.*;

import java.util.Iterator;

import static org.mockito.Mockito.*;

public class ScatterClustersPlacementHelperTest {

    private SeparationStrategyManager strategy;
    private AgentDescriptor descriptor;
    private Iterator<Coordinate> cIter;
    private ScatterClustersPlacementHelper query;

    @Before
    public void before() throws Exception {
        strategy = mock(SeparationStrategyManager.class);
        descriptor = mock(AgentDescriptor.class);
        cIter = mock(Iterator.class);
        query = new ScatterClustersPlacementHelper(strategy, descriptor);
    }

    @Test
    public void doPlacement() throws Exception {
        when(cIter.hasNext()).thenReturn(true);

        Coordinate c = mock(Coordinate.class);
        when(cIter.next()).thenReturn(c);

        Agent toPlace = mock(Agent.class);
        when(descriptor.next()).thenReturn(toPlace);

        int m = 5;
        int n = 1;

        when(strategy.attemptPlacement(c, toPlace, m)).thenReturn(1);
        query.doPlacement(n, m, cIter);
        verify(strategy, times(1)).attemptPlacement(any(Coordinate.class), any(Agent.class), anyInt());
    }

    @Test(expected = LatticeFullEvent.class)
    public void fullArenaHalts() throws Exception {
        when(cIter.hasNext()).thenReturn(false);
        query.doPlacement(1, 1, cIter);
    }
}