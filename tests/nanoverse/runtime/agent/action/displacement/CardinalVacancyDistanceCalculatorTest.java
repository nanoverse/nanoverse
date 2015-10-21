package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;

import java.util.HashSet;
import java.util.function.BiFunction;

import static org.mockito.Mockito.*;

public class CardinalVacancyDistanceCalculatorTest {

    private ShoveHelper shoveHelper;
    private BiFunction<Coordinate, Coordinate, Boolean> baseCaseFunction;
    private CardinalVacancyDistanceCalculator query;
    private Coordinate currentLocation, d;
    private HashSet<Coordinate> sites;

    @Before
    public void before() throws Exception {
        shoveHelper = mock(ShoveHelper.class);
        baseCaseFunction = mock(BiFunction.class);
        query = new CardinalVacancyDistanceCalculator(shoveHelper, baseCaseFunction);
        currentLocation = mock(Coordinate.class);
        d = mock(Coordinate.class);
        sites = new HashSet<>();
    }

    @Test
    public void baseCase() throws Exception {
        when(baseCaseFunction.apply(any(), any())).thenReturn(true);
        query.calculateDistToVacancy(currentLocation, d, sites);
        verifyZeroInteractions(shoveHelper);
    }

    @Test
    public void recursiveCase() throws Exception {
        when(baseCaseFunction.apply(any(), any())).thenReturn(false, false, true);
        when(shoveHelper.getNextLocation(currentLocation, d)).thenReturn(currentLocation);
        query.calculateDistToVacancy(currentLocation, d, sites);
        verify(shoveHelper, times(2)).getNextLocation(currentLocation, d);
    }
}