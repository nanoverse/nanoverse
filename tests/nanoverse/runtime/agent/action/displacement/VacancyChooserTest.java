package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.halt.LatticeFullEvent;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;
import test.LayerMocks;

import java.util.Random;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class VacancyChooserTest extends LayerMocks {

    private Random random;
    private VacancyChooser query;
    private Coordinate origin;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        random = mock(Random.class);
        query = new VacancyChooser(agentLayer, random);
        origin = mock(Coordinate.class);
    }

    @Test
    public void chooseVacancy() throws Exception {
        Coordinate expected = mock(Coordinate.class);
        Coordinate[] targets = new Coordinate[]{null, expected};
        when(random.nextInt(2)).thenReturn(1);
        when(lookup.getNearestVacancies(origin, -1)).thenReturn(targets);
        Coordinate actual = query.chooseVacancy(origin);
        assertSame(expected, actual);
    }

    @Test(expected = LatticeFullEvent.class)
    public void noTargetsHalts() throws Exception {
        when(lookup.getNearestVacancies(origin, -1)).thenReturn(new Coordinate[0]);
        query.chooseVacancy(origin);
    }
}