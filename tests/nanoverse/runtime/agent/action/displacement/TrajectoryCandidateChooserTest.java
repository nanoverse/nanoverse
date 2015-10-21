package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import org.junit.*;
import test.LayerMocks;

import java.util.Random;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class TrajectoryCandidateChooserTest extends LayerMocks {
    private Random random;
    private TrajectoryDisplacementCalculator calculator;
    private TrajectoryCandidateChooser query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        random = mock(Random.class);
        calculator = mock(TrajectoryDisplacementCalculator.class);
        query = new TrajectoryCandidateChooser(agentLayer, random, calculator);
    }

    @Test
    public void getNextCandidate() throws Exception {
        Coordinate a = mock(Coordinate.class);
        Coordinate b = mock(Coordinate.class);
        when(b.norm()).thenReturn(5);
        when(random.nextInt(5)).thenReturn(3);

        Coordinate c = mock(Coordinate.class);
        when(calculator.calcDisp(b, 3)).thenReturn(c);

        Coordinate expected = mock(Coordinate.class);
        when(geometry.rel2abs(a, c, Geometry.APPLY_BOUNDARIES)).thenReturn(expected);

        Coordinate actual = query.getNextCandidate(a, b);
        assertSame(expected, actual);
    }
}