package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.action.ActionTest;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;

import static org.mockito.Mockito.*;

public class CloneToVacancyHelperTest extends ActionTest {

    private CloneToVacancyHelper query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        query = new CloneToVacancyHelper(identity, mapper);
    }

    @Test
    public void cloneToVacancy() throws Exception {
        Agent child = mock(Agent.class);
        when(selfAgent.copy()).thenReturn(child);

        Coordinate target = mock(Coordinate.class);
        query.cloneToVacancy(target);
        verify(update).place(child, target);
    }
}