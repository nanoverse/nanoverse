package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;

import java.util.Random;
import java.util.function.BiFunction;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class TrajectoryChooserTest {
    private Random random;
    private BiFunction<Coordinate, Coordinate, CoordinateTupleOptionMap> mapMaker;
    private TrajectoryChooser query;

    public TrajectoryChooserTest() {
        random = mock(Random.class);
        mapMaker = mock(BiFunction.class);
        query = new TrajectoryChooser(random, mapMaker);
    }
    @Test
    public void testLifeCycle() throws Exception {
        Coordinate currentLocation = mock(Coordinate.class);
        Coordinate currentDisplacement = mock(Coordinate.class);
        CoordinateTupleOptionMap map = mock(CoordinateTupleOptionMap.class);
        when(mapMaker.apply(currentLocation, currentDisplacement))
            .thenReturn(map);

        when(map.getTotalWeight()).thenReturn(10.0);
        when(random.nextDouble()).thenReturn(2.0);

        CoordinateTuple expected = mock(CoordinateTuple.class);
        when(map.selectTarget(20.0)).thenReturn(expected);

        CoordinateTuple actual = query.getNextTuple(currentLocation, currentDisplacement);

        assertSame(expected, actual);
    }
}