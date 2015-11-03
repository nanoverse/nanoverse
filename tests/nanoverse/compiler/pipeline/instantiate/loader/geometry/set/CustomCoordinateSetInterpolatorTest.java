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

package nanoverse.compiler.pipeline.instantiate.loader.geometry.set;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.compiler.pipeline.instantiate.loader.control.identifiers.CoordinateLoader;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;

import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CustomCoordinateSetInterpolatorTest extends InterpolatorTest {

    private CustomCoordinateSetDefaults defaults;
    private CustomCoordinateSetInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(CustomCoordinateSetDefaults.class);
        query = new CustomCoordinateSetInterpolator(defaults);
    }

    @Test
    public void coordinates() throws Exception {
        ListObjectNode node = mock(ListObjectNode.class);
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMemberStream()).thenReturn(Stream.of(cNode));

        MapSymbolTable ist = mock(MapSymbolTable.class);
        when(cNode.getSymbolTable()).thenReturn(ist);

        CoordinateLoader loader = mock(CoordinateLoader.class);
        when(ist.getLoader()).thenReturn(loader);

        Coordinate coordinate = mock(Coordinate.class);
        when(loader.instantiate(cNode, lm, p)).thenReturn(coordinate);

        Stream<Coordinate> expected = Stream.of(coordinate);
        Stream<Coordinate> actual = query.coordinates(node, lm, p);

        assertStreamsEqual(expected, actual);
    }

    @Test
    public void coordinatesDefault() throws Exception {
        Stream<Coordinate> expected = mock(Stream.class);
        when(defaults.coordinates()).thenReturn(expected);
        Stream<Coordinate> actual = query.coordinates(null, lm, p);
        assertSame(expected, actual);
    }
}