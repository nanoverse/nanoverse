package nanoverse.runtime.processes.discrete.filter;

import nanoverse.runtime.control.identifiers.*;
import org.junit.*;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.*;

import static org.junit.Assert.assertEquals;

public class SampleFilterTest {

    private int maximum = 10;
    private Consumer<List<Coordinate>> shuffler;
    private SampleFilter query;

    @Before
    public void before() throws Exception {
        shuffler = list -> Collections.reverse(list);
        query = new SampleFilter(maximum, shuffler);
    }

    @Test
    public void noMaximum() throws Exception {
        query = new SampleFilter(-1, shuffler);
        assertNoChange(15);
    }

    private void assertNoChange(int length) {
        List<Coordinate> expected = makeList(length);
        System.out.println(expected.size());
        List<Coordinate> actual = query.apply(expected);
        assertEquals(expected, actual);
    }

    private List<Coordinate> makeList(int length) {
        return IntStream.range(0, length)
            .mapToObj(i -> new Coordinate1D(i, 0))
            .collect(Collectors.toList());
    }

    @Test
    public void belowMaximum() throws Exception {
        assertNoChange(5);
    }

    @Test
    public void atMaximum() throws Exception {
        assertNoChange(10);
    }

    @Test
    public void aboveMaximum() throws Exception {
        List<Coordinate> initial = makeList(15);

        // Since the "shuffler" reverses order, we expect to get the last ten
        // elements of the list
        List<Coordinate> expected = IntStream.range(5, 15)
            .mapToObj(i -> new Coordinate1D(i, 0))
            .collect(Collectors.toList());
        Collections.reverse(expected);

        List<Coordinate> actual = query.apply(initial);

        assertEquals(expected, actual);
    }

}