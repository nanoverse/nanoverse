package nanoverse.runtime.agent.targets;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.processes.discrete.filter.Filter;
import org.junit.*;
import test.LayerMocks;

import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TargetRuleTest extends LayerMocks {
    private Random random;
    private Agent callback;
    private Filter filter;

    private TargetRule query;
    private List<Coordinate> baseCandidates;
    private Agent caller;

    @Before
    public void before() throws Exception {
        random = mock(Random.class);
        callback = mock(Agent.class);
        filter = mock(Filter.class);
        baseCandidates = makeBaseCandidates(3);
        caller = mock(Agent.class);
        query = makeQuery(agent -> baseCandidates);
    }

    private List<Coordinate> makeBaseCandidates(int i) {
        return IntStream.range(0, i - 1)
            .mapToObj(k -> mock(Coordinate.class))
            .collect(Collectors.toList());
    }

    private TargetRule makeQuery(Function<Agent, List<Coordinate>> getCandidates) {
        return new TargetRule(callback, layerManager, filter, random) {
            @Override
            protected List<Coordinate> getCandidates(Agent caller) {
                return getCandidates.apply(caller);
            }

            @Override
            public TargetRule copy(Agent child) {
                return null;
            }
        };
    }

    @Test
    public void report() throws Exception {
        when(filter.apply(any())).thenAnswer(invocation -> {
            List<Coordinate> input = (List<Coordinate>) invocation.getArguments()[0];
            return input;
        });
        List<Coordinate> expected = baseCandidates;
        List<Coordinate> actual = query.report(caller);
        assertEquals(expected, actual);
    }
    @Test
    public void reportRespectsFilter() throws Exception {
        when(filter.apply(any())).thenReturn(new ArrayList<>(0));
        List<Coordinate> expected = new ArrayList<>(0);
        List<Coordinate> actual = query.report(caller);
        assertEquals(expected, actual);
    }

    @Test
    public void getCallback() throws Exception {
        assertSame(callback, query.getCallback());
    }
}