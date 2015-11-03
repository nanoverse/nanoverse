/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

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