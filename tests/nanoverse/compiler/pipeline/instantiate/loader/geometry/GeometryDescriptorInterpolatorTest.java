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

package nanoverse.compiler.pipeline.instantiate.loader.geometry;

import nanoverse.compiler.pipeline.instantiate.factory.geometry.GeometryDescriptorDefaults;
import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.compiler.pipeline.instantiate.loader.geometry.lattice.LatticeLoader;
import nanoverse.compiler.pipeline.instantiate.loader.geometry.shape.ShapeLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.geometry.lattice.Lattice;
import nanoverse.runtime.geometry.shape.Shape;
import org.junit.*;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class GeometryDescriptorInterpolatorTest extends InterpolatorTest {

    private GeometryDescriptorInterpolator query;
    private GeometryDescriptorDefaults defaults;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(GeometryDescriptorDefaults.class);
        query = new GeometryDescriptorInterpolator(load, defaults);
    }

    @Test
    public void lattice() throws Exception {
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("lattice")).thenReturn(cNode);

        LatticeLoader loader = mock(LatticeLoader.class);
        when(load.getLoader(eq(node), eq("lattice"), anyBoolean()))
            .thenReturn(loader);

        Lattice expected = mock(Lattice.class);
        when(loader.instantiate()).thenReturn(expected);

        Lattice actual = query.lattice(node);
        assertSame(expected, actual);
    }

    @Test
    public void latticeDefault() throws Exception {
        Lattice expected = mock(Lattice.class);
        when(defaults.lattice()).thenReturn(expected);
        Lattice actual = query.lattice(node);
        assertSame(expected, actual);
    }

    @Test
    public void shape() throws Exception {
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("shape")).thenReturn(cNode);

        ShapeLoader loader = mock(ShapeLoader.class);
        when(load.getLoader(eq(node), eq("shape"), anyBoolean()))
            .thenReturn(loader);

        Shape expected = mock(Shape.class);
        Lattice lattice = mock(Lattice.class);
        when(loader.instantiate(cNode, lattice, p)).thenReturn(expected);

        Shape actual = query.shape(node, lattice, p);
        assertSame(expected, actual);
    }

    @Test
    public void shapeDefault() throws Exception {
        Lattice lattice = mock(Lattice.class);
        Shape expected = mock(Shape.class);
        when(defaults.shape(lattice, p)).thenReturn(expected);
        Shape actual = query.shape(node, lattice, p);
        assertSame(expected, actual);
    }
}