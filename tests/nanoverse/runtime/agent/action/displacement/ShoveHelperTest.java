package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;
import test.LayerMocks;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ShoveHelperTest extends LayerMocks {

    private TrajectoryChooser chooser;
    private ShoveHelper query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        chooser = mock(TrajectoryChooser.class);
        query = new ShoveHelper(agentLayer, chooser);
    }

    @Test
    public void isOccupied() throws Exception {
        Coordinate c = mock(Coordinate.class);
        when(viewer.isOccupied(c)).thenReturn(true);
        assertTrue(query.isOccupied(c));
    }

    @Test
    public void swap() throws Exception {
        Coordinate p = mock(Coordinate.class);
        Coordinate q = mock(Coordinate.class);
        query.swap(p, q);
        verify(update).swap(p, q);
    }

    @Test
    public void getNextLocation() throws Exception {
        Coordinate currentLocation = mock(Coordinate.class);
        Coordinate currentDisplacement = mock(Coordinate.class);
        CoordinateTuple expected = mock(CoordinateTuple.class);
        when(chooser.getNextTuple(currentLocation, currentDisplacement)).thenReturn(expected);

        CoordinateTuple actual = query.getNextTuple(currentLocation, currentDisplacement);
        assertSame(expected, actual);
    }
}