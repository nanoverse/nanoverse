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

package nanoverse.compiler.pipeline.instantiate.loader.io.visual.highlight;

import nanoverse.runtime.control.GeneralParameters;
import org.junit.*;

import java.awt.*;
import java.util.function.Supplier;

import static org.mockito.Mockito.*;

public class DotGlyphInterpolatorTest extends GlyphInterpolatorTest {

    private DotGlyphInterpolator query;
    private DotGlyphDefaults defaults;
    private GeneralParameters p;

    @Before
    @Override
    public void before() throws Exception {
        super.before();
        defaults = mock(DotGlyphDefaults.class);
        p = mock(GeneralParameters.class);
        query = new DotGlyphInterpolator(load, defaults, colorLoader);
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

    @Test
    public void size() throws Exception {
        Supplier<Double> trigger = () -> query.size(node, random);
        verifyDouble("size", trigger);
    }

    @Test
    public void sizeDefault() throws Exception {
        double expected = 3.0;
        Runnable trigger = () -> query.size(node, random);
        when(defaults.size()).thenReturn(expected);
        verifyDoubleDefault("size", expected, trigger);
    }
}