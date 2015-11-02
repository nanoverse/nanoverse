package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import org.junit.*;
import test.LayerMocks;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class CardinalVacancyWeightCalculatorTest extends LayerMocks {

    private CardinalVacancyDistanceCalculator calculator;
    private CardinalVacancyWeightCalculator query;

    @Before
    public void before() throws Exception {
        super.before();
        calculator = mock(CardinalVacancyDistanceCalculator.class);
        query = new CardinalVacancyWeightCalculator(calculator, agentLayer);
    }

    @Test
    public void calcWeight() throws Exception {
        Coordinate origin = mock(Coordinate.class);
        Coordinate target = mock(Coordinate.class);
        Coordinate displacement = mock(Coordinate.class);
        when(geometry.getDisplacement(origin, target, Geometry.APPLY_BOUNDARIES))
            .thenReturn(displacement);

        doAnswer(invocation -> {
            HashSet<Coordinate> affectedSites =
                (HashSet<Coordinate>) invocation.getArguments()[2];

            affectedSites.add(origin);
            affectedSites.add(target);

            return null;

        }).when(calculator)
            .calculateDistToVacancy(eq(origin), eq(displacement), any(HashSet.class));

        double actual = query.calcWeight(origin, target);
        assertEquals(0.5, actual, epsilon);
    }
}