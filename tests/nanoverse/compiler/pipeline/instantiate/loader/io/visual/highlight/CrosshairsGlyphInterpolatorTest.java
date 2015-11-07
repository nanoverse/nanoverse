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

package nanoverse.compiler.pipeline.instantiate.loader.io.visual.highlight;

import nanoverse.runtime.control.GeneralParameters;
import org.junit.*;

import java.awt.*;
import java.util.function.Supplier;

import static org.mockito.Mockito.*;

public class CrosshairsGlyphInterpolatorTest extends GlyphInterpolatorTest {

    private CrosshairsGlyphInterpolator query;
    private CrosshairsGlyphDefaults defaults;
    private GeneralParameters p;

    @Before
    @Override
    public void before() throws Exception {
        super.before();
        defaults = mock(CrosshairsGlyphDefaults.class);
        p = mock(GeneralParameters.class);
        query = new CrosshairsGlyphInterpolator(load, defaults, colorLoader);
    }

    @Test
    public void crossSize() throws Exception {
        Supplier<Double> trigger = () -> query.crossSize(node, random);
        verifyDouble("crossSize", trigger);
    }

    @Test
    public void crossSizeDefault() throws Exception {
        double expected = 3.0;
        Runnable trigger = () -> query.crossSize(node, random);
        when(defaults.crossSize()).thenReturn(expected);
        verifyDoubleDefault("crossSize", expected, trigger);
    }

    @Test
    public void circleSize() throws Exception {
        Supplier<Double> trigger = () -> query.circleSize(node, random);
        verifyDouble("circleSize", trigger);
    }

    @Test
    public void circleSizeDefault() throws Exception {
        double expected = 3.0;
        Runnable trigger = () -> query.circleSize(node, random);
        when(defaults.circleSize()).thenReturn(expected);
        verifyDoubleDefault("circleSize", expected, trigger);
    }

    @Test
    public void color() throws Exception {
        Supplier<Color> trigger = () -> query.color(node, p);
        verifyColor("color", trigger);
    }

    @Test
    public void colorDefault() throws Exception {
        String expected = "color";
        when(defaults.color()).thenReturn(expected);
        verifyStringDefault("color", expected, () -> query.color(node, p));
    }
}