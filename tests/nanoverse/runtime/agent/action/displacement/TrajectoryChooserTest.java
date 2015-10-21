package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class TrajectoryChooserTest {

    private TrajectoryCandidateChooser candidateChooser;
    private TrajectoryLegalityHelper legalityHelper;
    private TrajectoryChooser query;
    private Coordinate w, x, y, z;

    @Before
    public void before() throws Exception {
        candidateChooser = mock(TrajectoryCandidateChooser.class);
        legalityHelper = mock(TrajectoryLegalityHelper.class);
        query = new TrajectoryChooser(candidateChooser, legalityHelper);
        w = mock(Coordinate.class);
        x = mock(Coordinate.class);
        y = mock(Coordinate.class);
        z = mock(Coordinate.class);
        when(candidateChooser.getNextCandidate(w, x)).thenReturn(y, z);
    }

    @Test
    public void partialPassCase() throws Exception {
        when(legalityHelper.isLegal(y)).thenReturn(true);
        Coordinate actual = query.getNextLocation(w, x);
        assertSame(y, actual);
        verify(legalityHelper, never()).handleIllegal(any(), any());
    }

    @Test
    public void fullPassCase() throws Exception {
        when(legalityHelper.isLegal(y)).thenReturn(false);
        when(legalityHelper.isLegal(z)).thenReturn(true);
        Coordinate actual = query.getNextLocation(w, x);
        assertSame(z, actual);
        verify(legalityHelper, times(1)).handleIllegal(any(), any());
    }
}