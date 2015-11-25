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

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 11/24/2015.
 */
public class SequentialPaletteTest {

    private Color a, b, c;
    private Color nullValueColor, borderColor;
    private List<Color> elements;
    private HashMap<String, Color> colorsByName;

    private SequentialPalette<String> query;

    @Before
    public void before() throws Exception {
        a = mock(Color.class);
        b = mock(Color.class);
        c = mock(Color.class);
        nullValueColor = mock(Color.class);
        borderColor = mock(Color.class);
        elements = Stream.of(a, b, c).collect(Collectors.toList());
        colorsByName = new HashMap<>();

        query = new SequentialPalette(nullValueColor, borderColor, colorsByName) {
            @Override
            protected List<Color> resolveElements() {
                return elements;
            }
        };
    }

    @Test
    public void colorSequenceLoops() throws Exception {
        assertSame(a, query.apply("a"));
        assertSame(b, query.apply("b"));
        assertSame(c, query.apply("c"));
        assertSame(a, query.apply("d"));
    }

    @Test
    public void assignedColorIsPreserved() throws Exception {
        assertSame(a, query.apply("a"));
        assertSame(b, query.apply("b"));
        assertSame(a, query.apply("a"));
    }

    @Test
    public void nullValueColorIsSpecial() throws Exception {
        assertSame(nullValueColor, query.apply(null));
    }

    @Test
    public void borderColor() throws Exception {
        assertSame(borderColor, query.getBorderColor());
    }
}