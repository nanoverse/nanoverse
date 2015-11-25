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
import java.util.Map;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by dbborens on 11/25/2015.
 */
public class CustomPaletteTest extends PaletteTest {

    private final static String NAME = "test";

    private Color defaultColor;
    private Map<String, Color> map;

    @Override
    public Palette<String> getQuery() {
        return new CustomPalette(nullValueColor, borderColor, defaultColor, map);
    }

    @Before
    @Override
    public void before() throws Exception {
        super.before();
        map = mock(Map.class);
        defaultColor = mock(Color.class);
    }

    @Test
    public void apply() throws Exception {
        Palette<String> query = getQuery();

        when(map.containsKey(NAME)).thenReturn(true);

        Color expected = mock(Color.class);
        when(map.get(NAME)).thenReturn(expected);

        Color actual = query.apply(NAME);
        assertSame(expected, actual);
    }

    @Test
    public void defaultColorSpecial() throws Exception {
        Palette<String> query = getQuery();
        when(map.containsKey(NAME)).thenReturn(false);
        Color actual = query.apply(NAME);
        assertSame(defaultColor, actual);
    }

}