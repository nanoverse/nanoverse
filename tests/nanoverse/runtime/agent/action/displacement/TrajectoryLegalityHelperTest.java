package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TrajectoryLegalityHelperTest {

    private TrajectoryLegalityHelper query;
    private Coordinate c, d;

    @Before
    public void before() throws Exception {
        query = new TrajectoryLegalityHelper();
        c = mock(Coordinate.class);
        d = mock(Coordinate.class);
    }

    @Test
    public void isLegalNull() throws Exception {
        boolean actual = query.isLegal(null);
        assertFalse(actual);
    }

    @Test
    public void isLegalBeyondBounds() throws Exception {
        when(c.hasFlag(Flags.BEYOND_BOUNDS)).thenReturn(true);
        boolean actual = query.isLegal(c);
        assertFalse(actual);
    }

    @Test
    public void isLegalInBounds() throws Exception {
        boolean actual = query.isLegal(c);
        assertTrue(actual);
    }

    @Test(expected = IllegalStateException.class)
    public void handleIllegalPushToBoundaryThrows() throws Exception {
        when(c.hasFlag(Flags.BEYOND_BOUNDS)).thenReturn(true);
        when(d.norm()).thenReturn(1);
        query.handleIllegal(c, d);
    }

}