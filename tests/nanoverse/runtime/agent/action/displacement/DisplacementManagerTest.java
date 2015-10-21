package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;

import static org.mockito.Mockito.*;

public class DisplacementManagerTest {

    private ImaginarySiteCleaner cleaner;
    private ShoveManager shoveManager;
    private VacancyChooser vacancyChooser;
    private DisplacementManager query;
    private Coordinate origin, target;

    @Before
    public void before() throws Exception {
        cleaner = mock(ImaginarySiteCleaner.class);
        shoveManager = mock(ShoveManager.class);
        vacancyChooser = mock(VacancyChooser.class);
        query = new DisplacementManager(cleaner, shoveManager, vacancyChooser);

        origin = mock(Coordinate.class);
        target = mock(Coordinate.class);
    }

    @Test
    public void shove() throws Exception {
        query.shove(origin, target);
        verify(shoveManager).shove(origin, target);
    }

    @Test
    public void removeImaginary() throws Exception {
        query.removeImaginary();
        verify(cleaner).removeImaginary();
    }

    @Test
    public void chooseVacancy() throws Exception {
        query.chooseVacancy(origin);
        verify(vacancyChooser).chooseVacancy(origin);
    }

    @Test
    public void shoveRandom() throws Exception {
        query.shoveRandom(origin);
        verify(shoveManager).shoveRandom(origin);
    }

    @Test
    public void shoveWeighted() throws Exception {
        query.shoveWeighted(origin);
        verify(shoveManager).shoveWeighted(origin);
    }
}