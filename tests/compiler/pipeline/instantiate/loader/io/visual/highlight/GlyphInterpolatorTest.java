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

package compiler.pipeline.instantiate.loader.io.visual.highlight;

import compiler.pipeline.instantiate.loader.InterpolatorTest;
import compiler.pipeline.instantiate.loader.io.visual.color.ColorLoader;

import java.awt.*;
import java.util.function.Supplier;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 8/23/2015.
 */
public abstract class GlyphInterpolatorTest extends InterpolatorTest {

    protected ColorLoader colorLoader;

    @Override
    public void before() throws Exception {
        super.before();
        colorLoader = mock(ColorLoader.class);
    }

    protected void verifyColor(String member, Supplier<Color> trigger) {
        String expStr = "expected";
        when(load.aString(eq(node), eq(member), any())).thenReturn(expStr);
        when(load.aString(eq(node), eq(member))).thenReturn(expStr);

        Color expected = mock(Color.class);
        when(colorLoader.instantiate(eq(expStr), any())).thenReturn(expected);
        Color actual = trigger.get();

        assertSame(expected, actual);
    }

}
