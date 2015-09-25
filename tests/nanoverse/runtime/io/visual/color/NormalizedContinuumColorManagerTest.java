/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein and the
 * Trustees of Princeton University.
 *
 * This file is part of the Nanoverse simulation framework
 * (patent pending).
 *
 * This program is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Affero General
 * Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package nanoverse.runtime.io.visual.color;

import nanoverse.runtime.control.arguments.ConstantDouble;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.io.visual.HSLColor;
import nanoverse.runtime.layers.SystemState;
import org.junit.*;
import test.TestBase;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NormalizedContinuumColorManagerTest extends TestBase {

    private ConstantDouble minHueArg = new ConstantDouble(0.5);
    private ConstantDouble maxHueArg = new ConstantDouble(1.0);
    private ConstantDouble minLuminanceArg = new ConstantDouble(0.0);
    private ConstantDouble maxLuminanceArg = new ConstantDouble(0.25);
    private ConstantDouble minSaturationArg = new ConstantDouble(0.25);
    private ConstantDouble maxSaturationArg = new ConstantDouble(0.25);
    private ContinuumNormalizationHelper normalizer;
    private Coordinate c;
    private SystemState systemState;
    private NormalizedContinuumColorManager query;

    @Before
    public void before() throws Exception {

        c = mock(Coordinate.class);
        systemState = mock(SystemState.class);
        normalizer = mock(ContinuumNormalizationHelper.class);
        query = new NormalizedContinuumColorManager(minHueArg,
            maxHueArg,
            minSaturationArg,
            maxSaturationArg,
            minLuminanceArg,
            maxLuminanceArg,
            normalizer, false,
            new UniformColorManager(Color.WHITE));
    }

    @Test
    public void rangeMinimumColor() throws Exception {
        // Zero lightness, so black
        doColorTest(0.0, 0F, 0F, 0F);
    }

    private void doColorTest(double normalized, float h, float s, float l) {
        when(normalizer.normalize(c, systemState)).thenReturn(normalized);
        Color color = query.getColor(c, systemState);
        float[] hslColor = HSLColor.fromRGB(color);
        assertEquals(h, hslColor[0], floatEpsilon * 1000F);
        assertEquals(s, hslColor[1], floatEpsilon * 1000F);
        assertEquals(l, hslColor[2], floatEpsilon * 1000F);
    }

    @Test
    public void rangeMaximumColor() throws Exception {
        doColorTest(1.0, 360F, 12.5F, 25F);
    }

    @Test
    public void rangeMidpointColor() throws Exception {
        doColorTest(0.5, 360F, 12.5F, 12.5F);
    }

    @Test
    public void getBorderColor() throws Exception {
        Color actual = query.getBorderColor();
        Color expected = Color.DARK_GRAY;
        assertEquals(expected, actual);
    }
}