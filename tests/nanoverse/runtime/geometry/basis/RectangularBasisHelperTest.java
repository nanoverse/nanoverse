package nanoverse.runtime.geometry.basis;

import nanoverse.runtime.control.identifiers.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RectangularBasisHelperTest {

    private RectangularBasisHelper query;

    @Before
    public void before() throws Exception {
        query = new RectangularBasisHelper();
    }

    @Test
    public void adjustOrigin() throws Exception {
        Coordinate initial = new Coordinate2D(0, 0, 0);
        Coordinate actual = query.adjust(initial);
        Coordinate expected = new Coordinate2D(0, 0, 0);
        assertEquals(actual, expected);
    }

    @Test
    public void adjustX() throws Exception {
        Coordinate initial = new Coordinate2D(8, 0, 0);
        Coordinate actual = query.adjust(initial);
        Coordinate expected = new Coordinate2D(8, 0, 0);
        assertEquals(actual, expected);
    }

    @Test
    public void adjustY() throws Exception {
        Coordinate initial = new Coordinate2D(0, 8, 0);
        Coordinate actual = query.adjust(initial);
        Coordinate expected = new Coordinate2D(0, 8, 0);
        assertEquals(actual, expected);
    }


    @Test
    public void invAdjustOrigin() throws Exception {
        Coordinate initial = new Coordinate2D(0, 0, 0);
        Coordinate actual = query.invAdjust(initial);
        Coordinate expected = new Coordinate2D(0, 0, 0);
        assertEquals(actual, expected);
    }

    @Test
    public void invAdjustX() throws Exception {
        Coordinate initial = new Coordinate2D(8, 0, 0);
        Coordinate actual = query.invAdjust(initial);
        Coordinate expected = new Coordinate2D(8, 0, 0);
        assertEquals(actual, expected);

    }

    @Test
    public void invAdjustY() throws Exception {
        Coordinate initial = new Coordinate2D(0, 8, 0);
        Coordinate actual = query.invAdjust(initial);
        Coordinate expected = new Coordinate2D(0, 8, 0);
        assertEquals(actual, expected);
    }
}