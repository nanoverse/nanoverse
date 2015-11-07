package nanoverse.runtime.agent.action.stochastic;

import nanoverse.runtime.agent.action.*;
import nanoverse.runtime.layers.LayerManager;
import org.junit.*;
import test.TestBase;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NormalizedDynamicActionRangeMapTest extends TestBase {

    private LayerManager layerManager;
    private Action a1, a2;
    private ProbabilitySupplier p1, p2;

    private DynamicActionRangeMap query;

    @Before
    public void init() throws Exception {
        layerManager = mock(LayerManager.class);
        query = new NormalizedDynamicActionRangeMap(layerManager);

        a1 = mock(Action.class);
        p1 = mockProbabilitySupplier(0.5);
        query.add(a1, p1);

        a2 = mock(Action.class);
        p2 = mockProbabilitySupplier(0.3);
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

    private ProbabilitySupplier mockProbabilitySupplier(double p) {
        ProbabilitySupplier ret = mock(ProbabilitySupplier.class);
        when(ret.get()).thenReturn(p);
        return ret;
    }
}