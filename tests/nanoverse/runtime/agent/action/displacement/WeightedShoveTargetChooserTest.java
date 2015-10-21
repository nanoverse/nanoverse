package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.structural.RangeMap;
import org.junit.*;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class WeightedShoveTargetChooserTest {

    private WeightedShoveTargetBuilder builder;
    private WeightedShoveTargetChooser query;

    @Before
    public void before() throws Exception {
        builder = mock(WeightedShoveTargetBuilder.class);
        query = new WeightedShoveTargetChooser(builder);
    }

    @Test
    public void choose() throws Exception {
        Coordinate origin = mock(Coordinate.class);
        RangeMap<Coordinate> rangeMap = mock(RangeMap.class);
        when(builder.buildRangeMap(origin)).thenReturn(rangeMap);

        Coordinate expected = mock(Coordinate.class);
        when(builder.chooseTarget(rangeMap)).thenReturn(expected);

        Coordinate actual = query.choose(origin);
        assertSame(expected, actual);
    }
}