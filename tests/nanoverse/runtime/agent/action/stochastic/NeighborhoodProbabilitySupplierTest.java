package nanoverse.runtime.agent.action.stochastic;

import org.junit.Before;
import org.junit.Test;
import test.TestBase;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/13/2015.
 */
public class NeighborhoodProbabilitySupplierTest extends TestBase {

    private NeighborhoodCountHelper helper;
    private NeighborhoodProbabilitySupplier query;
    private double coefficient;
    private double offset;

    @Before
    public void before() throws Exception {
        helper = mock(NeighborhoodCountHelper.class);
        coefficient = 2.0;
        offset = 1.0;
        query = new NeighborhoodProbabilitySupplier(helper, coefficient, offset);
    }

    @Test
    public void testGet() throws Exception {
        when(helper.getNeighborCount()).thenReturn(3.0);
        double actual = query.get();
        assertEquals(7.0, actual, epsilon);
    }
}