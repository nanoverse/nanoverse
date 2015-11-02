package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.action.ActionTest;
import nanoverse.runtime.agent.action.helper.SelfTargetHighlighter;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

public class PreferentialExpansionManagerTest extends ActionTest {

    private DisplacementManager displacementManager;
    private ExpandToTargetManager expansionManager;
    private CloneToVacancyHelper vacancyHelper;
    private PreferentialExpansionManager query;
    private SelfTargetHighlighter stHighlighter;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        stHighlighter = mock(SelfTargetHighlighter.class);
        displacementManager = mock(DisplacementManager.class);
        expansionManager = mock(ExpandToTargetManager.class);
        vacancyHelper = mock(CloneToVacancyHelper.class);
        query = new PreferentialExpansionManager(displacementManager, stHighlighter, expansionManager, identity, vacancyHelper);
    }

    @Test
    public void lifeCycle() throws Exception {
        // The parent agent will get moved in the course of the shoving process
        DisplacementOption option = mock(DisplacementOption.class);
        Coordinate target = mock(Coordinate.class);
        when(expansionManager.getShortestOption(target)).thenReturn(option);

        Coordinate newlyVacant = mock(Coordinate.class);
        when(option.getOccupied()).thenReturn(newlyVacant);

        Agent child = mock(Agent.class);
        when(selfAgent.copy()).thenReturn(child);

        query.preferentialExpand(target);

        InOrder inOrder = inOrder(expansionManager, vacancyHelper, displacementManager, stHighlighter);
        inOrder.verify(expansionManager).doShove(option);
        inOrder.verify(vacancyHelper).cloneToVacancy(newlyVacant);
        inOrder.verify(displacementManager).removeImaginary();
        inOrder.verify(stHighlighter).highlight(target, ownLocation);
        inOrder.verifyNoMoreInteractions();
    }
}