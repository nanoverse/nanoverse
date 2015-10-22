package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.control.arguments.IntegerArgument;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ScatterClustersCountHelperTest {

    private IntegerArgument maxTargets;
    private IntegerArgument neighborCount;
    private ScatterClustersCountHelper query;

    @Before
    public void before() throws Exception {
        maxTargets = mock(IntegerArgument.class);
        neighborCount = mock(IntegerArgument.class);
        query = new ScatterClustersCountHelper(maxTargets, neighborCount);
    }

    @Test
    public void getCeiling() throws Exception {
        when(maxTargets.next()).thenReturn(2);
        assertEquals(2, query.getCeiling());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nBelowZeroThrows() throws Exception {
        when(maxTargets.next()).thenReturn(-1);
        query.getCeiling();
    }

    @Test
    public void getNeighborCount() throws Exception {
        when(neighborCount.next()).thenReturn(3);
        assertEquals(3, query.getNeighborCount());
    }
}