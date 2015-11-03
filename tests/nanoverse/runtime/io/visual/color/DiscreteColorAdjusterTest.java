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

import nanoverse.runtime.io.visual.HSLColor;
import org.junit.Test;
import test.TestBase;

import java.awt.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/27/2015.
 */
public class DiscreteColorAdjusterTest extends TestBase {

    // This HSB library stinks.
    private static final float TOLERANCE = 0.1F;

    @Test
    public void adjustColor() throws Exception {
        DiscreteColorAdjuster query = new DiscreteColorAdjuster(0.1F, 0.5F);
        Color original = HSLColor.toRGB(0.5F, 0.8F, 0.2F);
        Color expected = query.adjustColor(original);
        checkHSB(expected, 0.5F, 0.08F, 0.04F);
    }

    @Test
    public void excessValuesCapped() throws Exception {
        DiscreteColorAdjuster query = new DiscreteColorAdjuster(1000.0F, 2000.0F);
        Color original = HSLColor.toRGB(0.5F, 0.8F, 0.2F);
        Color expected = query.adjustColor(original);
        checkHSB(expected, 0.5F, 1.0F, 1.0F);
    }

    private void checkHSB(Color expected, float h, float s, float b) {
        float[] obs = HSLColor.fromRGB(expected);

        assertEquals(h, obs[0], TOLERANCE);
        assertEquals(s, obs[1], TOLERANCE);
        assertEquals(b, obs[2], TOLERANCE);
    }
}