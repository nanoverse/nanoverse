package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.set.*;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.processes.discrete.filter.Filter;
import org.junit.*;
import test.LayerMocks;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.*;

import static org.mockito.Mockito.*;

public class TriggerProcessTargetResolverTest extends LayerMocks {

    private Coordinate c, d;
    private Supplier<CoordinateSet> activeSites;
    private List<Coordinate> decoy;
    private AgentLayer layer;
    private Filter userFilter;
    private Filter skipVacancyFilter;
    private Filter hasNeighborsFilter;
    private Filter maxTargetsFilter;
    private Agent agent;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        c = mock(Coordinate.class);
        activeSites = () -> CustomSet.of(c);
        layer = mock(AgentLayer.class);

        d = mock(Coordinate.class);
        decoy = Stream.of(d).collect(Collectors.toList());

        userFilter = makePassThroughFilter();
        skipVacancyFilter = makePassThroughFilter();
        hasNeighborsFilter = makePassThroughFilter();
        maxTargetsFilter = makePassThroughFilter();

        // If the tests work right, "decoy" will pass through the filters
        // after it is introduced. If they don't, "failure" will go through
        // all the way.
        agent = mock(Agent.class);
        when(viewer.getAgent(d)).thenReturn(agent);
    }

    private Filter makePassThroughFilter() throws Exception {
        Filter filter = mock(Filter.class);
        when(filter.apply(any())).thenAnswer(invocation -> {
            List<Coordinate> input = (List<Coordinate>) invocation.getArguments()[0];
            return input;
        });

        return filter;
    }

    @Test
    public void appliesVacancyFilter() throws Exception {
        skipVacancyFilter = makeDecoyFilter();
        doTest();
    }

    private void doTest() throws Exception {
        TriggerProcessTargetResolver query = new TriggerProcessTargetResolver(activeSites, agentLayer, userFilter, skipVacancyFilter, hasNeighborsFilter, maxTargetsFilter);
        Stream<Agent> actual = query.resolveTargets();
        Stream<Agent> expected = Stream.of(agent);
        assertStreamsEqual(expected, actual);
    }

    private Filter makeDecoyFilter() throws Exception {
        Filter filter = mock(Filter.class);
        when(filter.apply(any())).thenReturn(decoy);
        return filter;
    }

    @Test
    public void appliesNeighborhoodFilter() throws Exception {
        hasNeighborsFilter = makeDecoyFilter();
        doTest();
    }

    @Test
    public void appliesUserFilter() throws Exception {
        userFilter = makeDecoyFilter();
        doTest();
    }

    @Test
    public void appliesMaxTargetFilter() throws Exception {
        maxTargetsFilter = makeDecoyFilter();
        doTest();
    }
}