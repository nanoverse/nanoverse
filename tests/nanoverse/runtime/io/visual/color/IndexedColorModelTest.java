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
import nanoverse.runtime.io.visual.color.palettes.Palette;
import nanoverse.runtime.layers.SystemState;
import org.junit.*;
import test.LayerMocks;

import java.awt.*;
import java.util.HashMap;
import java.util.function.Supplier;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by dbborens on 4/2/14.
 */
public class IndexedColorModelTest extends LayerMocks {
    private static final String NAME = "test";

    private Palette<String> palette;
    private IndexedColorModel query;
    private SystemState systemState;
    private Coordinate c;

    @Override @Before
    public void before() throws Exception {
        super.before();
        palette = mock(Palette.class);
        query = new IndexedColorModel(palette);

        c = mock(Coordinate.class);
        systemState = mock(SystemState.class);
        when(systemState.getLayerManager()).thenReturn(layerManager);
        when(viewer.getName(c)).thenReturn(NAME);
    }

    @Test
    public void getColor() throws Exception {
        when(viewer.getName(c)).thenReturn(NAME);
        Color expected = mock(Color.class);
        when(palette.apply(NAME)).thenReturn(expected);
        Color actual = query.getColor(c, systemState);
        assertSame(expected, actual);
    }

    @Test
    public void getBorderColor() throws Exception {
        Color expected = mock(Color.class);
        when(palette.getBorderColor()).thenReturn(expected);
        Color actual = query.getBorderColor();
        assertSame(expected, actual);
    }

//    @Test
//    public void vacantColorSpecial() throws Exception {
//        fail();
////        Color expected = Color.BLACK;
////        when(viewer.getName(c)).thenReturn(null);
////        Color actual = query.getColor(c, systemState);
////        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void getColorNew() throws Exception {
//        fail();
////        Color expected = mock(Color.class);
////        when(palette.get()).thenReturn(expected);
////        Color actual = query.getColor(c, systemState);
////        assertSame(expected, actual);
//    }
//
//    @Test
//    public void getColorExisting() throws Exception {
//        fail();
////        Color expected = mock(Color.class);
////        colorsByName.put(NAME, expected);
////        Color actual = query.getColor(c, systemState);
////        assertSame(expected, actual);
//    }

}
