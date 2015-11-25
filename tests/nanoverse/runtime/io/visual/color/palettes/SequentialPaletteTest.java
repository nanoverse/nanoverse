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
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

/**
 * Created by dbborens on 11/24/2015.
 */
public class SequentialPaletteTest extends PaletteTest {

    private Color a, b, c;
    private List<Color> elements;
    private HashMap<String, Color> colorsByName;


    @Override
    public Palette getQuery() {
        return new SequentialPalette(nullValueColor, borderColor, colorsByName) {
            @Override
            protected List<Color> resolveElements() {
                return elements;
            }
        };
    }

    @Before
    @Override
    public void before() throws Exception {
        super.before();
        a = mock(Color.class);
        b = mock(Color.class);
        c = mock(Color.class);
        elements = Stream.of(a, b, c).collect(Collectors.toList());
        colorsByName = new HashMap<>();
    }

    @Test
    public void colorSequenceLoops() throws Exception {
        Palette query = getQuery();
        assertSame(a, query.apply("a"));
        assertSame(b, query.apply("b"));
        assertSame(c, query.apply("c"));
        assertSame(a, query.apply("d"));
    }

    @Test
    public void assignedColorIsPreserved() throws Exception {
        Palette query = getQuery();
        assertSame(a, query.apply("a"));
        assertSame(b, query.apply("b"));
        assertSame(a, query.apply("a"));
    }

}