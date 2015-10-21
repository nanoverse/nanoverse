package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.structural.RangeMap;
import org.junit.*;
import test.LayerMocks;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class WeightedShoveTargetBuilderTest extends LayerMocks {

    private Random random;
    private CardinalVacancyWeightCalculator calculator;
    private WeightedShoveTargetBuilder query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        random = mock(Random.class);
        calculator = mock(CardinalVacancyWeightCalculator.class);
        query = new WeightedShoveTargetBuilder(agentLayer, random, calculator);
    }

    @Test
    public void buildRangeMap() throws Exception {
        Coordinate neighbor = mock(Coordinate.class);
        Coordinate origin = mock(Coordinate.class);
        when(calculator.calcWeight(origin, neighbor)).thenReturn(2.0);

        Coordinate[] cc = new Coordinate[]{neighbor};
        when(geometry.getNeighbors(origin, Geometry.APPLY_BOUNDARIES)).thenReturn(cc);

        // I don't like this--I should be able to mock this out
        RangeMap<Coordinate> result = query.buildRangeMap(origin);
        assertEquals(2.0, result.getTotalWeight(), epsilon);

        Stream<Coordinate> actual = result.getKeys().stream();
        Stream<Coordinate> expected = Stream.of(neighbor);
        assertStreamsEqual(expected, actual);
    }

    @Test
    public void chooseTarget() throws Exception {
        RangeMap<Coordinate> rangeMap = mock(RangeMap.class);
        when(rangeMap.getTotalWeight()).thenReturn(1.0);
        when(random.nextDouble()).thenReturn(1.0);

        Coordinate expected = mock(Coordinate.class);
        when(rangeMap.selectTarget(1.0)).thenReturn(expected);

        Coordinate actual = query.chooseTarget(rangeMap);
        assertSame(expected, actual);
    }
}