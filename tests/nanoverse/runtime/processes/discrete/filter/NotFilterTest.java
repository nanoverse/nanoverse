package nanoverse.runtime.processes.discrete.filter;

import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;
import test.TestBase;

import java.util.*;
import java.util.stream.*;

import static org.mockito.Mockito.*;

public class NotFilterTest extends TestBase {

    private Filter toInvert;
    private Coordinate c;
    private NotFilter query;
    private List<Coordinate> cList;

    @Before
    public void before() throws Exception {
        toInvert = mock(Filter.class);
        c = mock(Coordinate.class);
        cList = Stream.of(c).collect(Collectors.toList());
        query = new NotFilter(toInvert);
    }

    @Test
    public void negatesPositive() throws Exception {
        configureOutput(true);
        Stream<Coordinate> expected = Stream.empty();
        Stream<Coordinate> actual = query.apply(cList).stream();
        assertStreamsEqual(expected, actual);
    }

    private void configureOutput(boolean include) {
        List<Coordinate> output = include
            ? cList
            : new ArrayList<>();

        when(toInvert.apply(any())).thenReturn(output);
    }

    @Test
    public void negatesNegative() throws Exception {
        configureOutput(false);
        Stream<Coordinate> expected = Stream.of(c);
        Stream<Coordinate> actual = query.apply(cList).stream();
        assertStreamsEqual(expected, actual);
    }
}