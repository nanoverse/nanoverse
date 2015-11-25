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

package nanoverse.compiler.pipeline.instantiate.loader.io.visual.color.palette;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.color.ColorLoader;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 11/24/2015.
 */
public class PaletteInterpolatorTest extends InterpolatorTest {

    private PaletteInterpolator query;
    private PaletteDefaults defaults;
    private ColorLoader colorLoader;

    @Override @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(PaletteDefaults.class);
        colorLoader = mock(ColorLoader.class);
        query = new PaletteInterpolator(load, colorLoader, defaults);
    }

    @Test
    public void borderColor() throws Exception {
        Supplier<Color> trigger = () -> query.borderColor(node, p);
        verifyColor("border", trigger);
    }

    @Test
    public void nullValueColor() throws Exception {
        Supplier<Color> trigger = () -> query.nullValueColor(node, p);
        verifyColor("nullValue", trigger);
    }

    private void verifyColor(String member, Supplier<Color> trigger) throws Exception {
        String colorName = "expected";
        when(load.aString(eq(node), eq(member), any())).thenReturn(colorName);
        Color expected = mock(Color.class);
        when(colorLoader.instantiate(colorName, p)).thenReturn(expected);
        Color actual = trigger.get();
        assertSame(expected, actual);
    }
}