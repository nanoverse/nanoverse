package nanoverse.runtime.agent.targets;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.processes.discrete.filter.Filter;
import org.junit.*;
import test.LayerMocks;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public abstract class TargetRuleTestBase extends LayerMocks {

    protected Agent self;
    protected Agent caller;
    protected Filter filter;
    protected Random random;

    // Create one coordinate for each type of coordinate considered under
    // any target rule.
    protected Coordinate ownLocation;
    protected Coordinate callerCoord;
    protected Coordinate occupiedNeighbor;
    protected Coordinate vacantNeighbor;

    @Before
    public void before() throws Exception {
        super.before();

        self = mock(Agent.class);
        caller = mock(Agent.class);
        filter = mock(Filter.class);

        // Pass-through filter
        when(filter.apply(anyList())).thenAnswer(invocation -> {
            List<Coordinate> input = (List<Coordinate>) invocation.getArguments()[0];
            return input;
        });

        random = mock(Random.class);

        ownLocation = mock(Coordinate.class);
        callerCoord = mock(Coordinate.class);
        occupiedNeighbor = mock(Coordinate.class);
        vacantNeighbor = mock(Coordinate.class);

        Coordinate[] neighbors = new Coordinate[]{
            occupiedNeighbor,
            vacantNeighbor
        };

        when(lookup.getAgentLocation(self)).thenReturn(ownLocation);
        when(lookup.getAgentLocation(caller)).thenReturn(callerCoord);

        when(geometry.getNeighbors(ownLocation, Geometry.APPLY_BOUNDARIES))
            .thenReturn(neighbors);

        when(viewer.isOccupied(occupiedNeighbor)).thenReturn(true);
        when(viewer.isOccupied(ownLocation)).thenReturn(true);
    }

    @Test
    public void selfAcceptance() throws Exception {
        Set<Coordinate> accepted = getAccepted();
        assertEquals(acceptsSelf(), accepted.contains(ownLocation));
    }

    private HashSet<Coordinate> getAccepted() throws Exception {
        TargetRule query = resolveQuery();

        List<Coordinate> acceptedList = query.report(caller);
        return new HashSet<>(acceptedList);
    }

    protected abstract TargetRule resolveQuery() throws Exception;

    protected abstract boolean acceptsSelf();

    @Test
    public void callerAcceptance() throws Exception {
        Set<Coordinate> accepted = getAccepted();
        assertEquals(acceptsCaller(), accepted.contains(callerCoord));
    }

    protected abstract boolean acceptsCaller();

    @Test
    public void occupiedNeighborAcceptance() throws Exception {
        Set<Coordinate> accepted = getAccepted();
        assertEquals(acceptsOccupiedNeighbors(), accepted.contains(occupiedNeighbor));
    }

    protected abstract boolean acceptsOccupiedNeighbors();

    @Test
    public void vacantNeighborAcceptance() throws Exception {
        Set<Coordinate> accepted = getAccepted();
        assertEquals(acceptsVacantNeighbors(), accepted.contains(vacantNeighbor));
    }

    protected abstract boolean acceptsVacantNeighbors();
}