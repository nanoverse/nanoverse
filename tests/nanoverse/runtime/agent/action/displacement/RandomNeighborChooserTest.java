package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import org.junit.*;
import test.LayerMocks;

import java.util.Random;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class RandomNeighborChooserTest extends LayerMocks {

    private Random random;
    private RandomNeighborChooser query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        random = mock(Random.class);
        query = new RandomNeighborChooser(geometry, random);
    }

    @Test
    public void chooseRandomNeighbor() throws Exception {
        Coordinate c = mock(Coordinate.class);
        Coordinate o = mock(Coordinate.class);
        Coordinate[] options = new Coordinate[]{null, c};

        when(geometry.getNeighbors(o, Geometry.APPLY_BOUNDARIES)).thenReturn(options);
        when(random.nextInt(2)).thenReturn(1);

        Coordinate actual = query.chooseRandomNeighbor(o);
        assertSame(c, actual);
    }
}