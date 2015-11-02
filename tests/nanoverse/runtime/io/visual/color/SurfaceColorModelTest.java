package nanoverse.runtime.io.visual.color;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.SystemState;
import org.junit.Before;
import org.junit.Test;
import test.LayerMocks;

import java.awt.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/27/2015.
 */
public class SurfaceColorModelTest extends LayerMocks {
    private ColorManager base;
    private DiscreteColorAdjuster adjuster;
    private InteriorChecker interiorChecker;
    private SurfaceColorModel query;
    private SystemState state;
    private Coordinate c;

    @Override @Before
    public void before() throws Exception {
        super.before();
        base = mock(ColorManager.class);
        adjuster = mock(DiscreteColorAdjuster.class);
        interiorChecker = mock(InteriorChecker.class);
        query = new SurfaceColorModel(base, adjuster, interiorChecker);

        state = mock(SystemState.class);
        when(state.getLayerManager()).thenReturn(layerManager);

        c = mock(Coordinate.class);
    }

    private void doTest(boolean isOccupied, boolean isInterior, Color expected) {
        when(viewer.isOccupied(c)).thenReturn(isOccupied);
        when(interiorChecker.isInterior(c, state)).thenReturn(isInterior);
        Color actual = query.getColor(c, state);
        assertEquals(expected, actual);
    }

    @Test
    public void getColorInterior() throws Exception {
        Color baseColor = mock(Color.class);
        when(base.getColor(c, state)).thenReturn(baseColor);
        Color adjusted = mock(Color.class);
        when(adjuster.adjustColor(baseColor)).thenReturn(adjusted);
        doTest(true, true, adjusted);
    }

    @Test
    public void getColorSurface() throws Exception {
        Color baseColor = mock(Color.class);
        when(base.getColor(c, state)).thenReturn(baseColor);
        doTest(true, false, baseColor);
    }

    @Test
    public void getVacantColor() throws Exception {
        doTest(false, false, Color.BLACK);
    }

    @Test
    public void getBorderColor() throws Exception {
        assertEquals(Color.DARK_GRAY, query.getBorderColor());
    }
}