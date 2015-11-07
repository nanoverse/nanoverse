package nanoverse.runtime.agent.action.stochastic;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.action.Action;
import nanoverse.runtime.agent.action.NullAction;
import nanoverse.runtime.layers.LayerManager;
import org.junit.Before;
import org.junit.Test;
import test.TestBase;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NormalizedDynamicActionRangeMapTest extends TestBase {

    private LayerManager layerManager;
    private Action a1, a2;
    private ProbabilitySupplier p1, p2;

    private NormalizedDynamicActionRangeMap query;
    private Map<Action, ProbabilitySupplier> functionMap;
    @Before
    public void init() throws Exception {
        layerManager = mock(LayerManager.class);

        a1 = mock(Action.class);
        p1 = mockProbabilitySupplier(0.5);

        a2 = mock(Action.class);
        p2 = mockProbabilitySupplier(0.3);
        functionMap = new LinkedHashMap<>();
        query = new NormalizedDynamicActionRangeMap(functionMap, layerManager);
        query.add(a1, p1);
        query.add(a2, p2);
    }

    @Test
    public void complementContainsNullAction() throws Exception {
        query.refresh();
        Action actual = query.selectTarget(0.9);
        assertEquals(NullAction.class, actual.getClass());
    }

    @Test
    public void optionsLoadedAsExpected() throws Exception {
        query.refresh();
        Action actual = query.selectTarget(0.6);
        assertSame(a2, actual);
    }

    @Test(expected = IllegalStateException.class)
    public void weightOverOneThrows() throws Exception {
        Action a3 = mock(Action.class);
        ProbabilitySupplier p3 = mockProbabilitySupplier(0.5);
        query.add(a3, p3);
        query.refresh();
    }

    @Test(expected = IllegalStateException.class)
    public void selectOverOneThrows() throws Exception {
        query.refresh();
        query.selectTarget(1.1);
    }

    @Test
    public void cloneReturnsExpectedClass() throws Exception {
        Agent child = mock(Agent.class);
        DynamicActionRangeMap cloned = query.clone(child);
        assertEquals(NormalizedDynamicActionRangeMap.class, cloned.getClass());
    }

//    @Test
//    public void cloneHasExpectedMembers() throws Exception {
//        Agent child = mock(Agent.class);
//        DynamicActionRangeMap cloned = query.clone(child);
//        cloned.refresh();
//        assertEquals(3, cloned.getTargetCount());
//    }

    private ProbabilitySupplier mockProbabilitySupplier(double p) {
        ProbabilitySupplier ret = mock(ProbabilitySupplier.class);
        when(ret.get()).thenReturn(p);
        return ret;
    }
}