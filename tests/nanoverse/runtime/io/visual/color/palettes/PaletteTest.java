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

package nanoverse.runtime.io.visual.color.palettes;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 11/24/2015.
 */
public class PaletteTest {

    private Palette query;
    private Color nullValueColor;
    private Color borderColor;

    @Before
    public void setUp() throws Exception {
        nullValueColor = mock(Color.class);
        borderColor = mock(Color.class);

        query = new Palette(nullValueColor, borderColor) {
            @Override
            public Object apply(Object o) {
                return null;
            }
        };

    }

    @Test
    public void getBorderColor() throws Exception {
        assertSame(borderColor, query.getBorderColor());
    }

    @Test
    public void getNullValueColor() throws Exception {
        assertSame(nullValueColor, query.getNullValueColor());
    }
}