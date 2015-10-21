package nanoverse.runtime.processes.discrete.filter;

import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;
import test.LayerMocks;

import java.util.*;
import java.util.stream.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class HasNeighborsFilterTest extends LayerMocks {

    private HasNeighborsFilter query;
    private Coordinate c;
    private List<Coordinate> cList;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        query = new HasNeighborsFilter(agentLayer);

        c = mock(Coordinate.class);
        cList = Stream.of(c).collect(Collectors.toList());
    }

    @Test
    public void hasNeighborsIncludes() throws Exception {
        // Filter works by counting non-empty neighbor names
        Stream<String> names = Stream.of("a");
        when(lookup.getNeighborNames(c, true)).thenReturn(names);

        List<Coordinate> actual = query.apply(cList);
        assertEquals(cList, actual);
    }

    @Test
    public void noNeighborsExcludes() throws Exception {
        Stream<String> names = Stream.empty();
        when(lookup.getNeighborNames(c, true)).thenReturn(names);

        List<Coordinate> expected = new ArrayList<>();
        List<Coordinate> actual = query.apply(cList);
        assertEquals(expected, actual);
    }
}