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

package nanoverse.compiler.pipeline.instantiate.loader.layers.agent;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.compiler.pipeline.instantiate.loader.geometry.boundary.BoundaryLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.arguments.GeometryDescriptor;
import nanoverse.runtime.geometry.boundaries.Boundary;
import org.junit.*;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class AgentLayerInterpolatorTest extends InterpolatorTest {

    private GeometryDescriptor geom;
    private AgentLayerDefaults defaults;
    private AgentLayerInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        geom = mock(GeometryDescriptor.class);
        defaults = mock(AgentLayerDefaults.class);
        query = new AgentLayerInterpolator(load, defaults);
    }

    @Test
    public void boundary() throws Exception {
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("boundary")).thenReturn(cNode);

        BoundaryLoader loader = mock(BoundaryLoader.class);
        when(load.getLoader(eq(node), eq("boundary"), anyBoolean())).thenReturn(loader);

        Boundary expected = mock(Boundary.class);
        when(loader.instantiate(geom)).thenReturn(expected);

        Boundary actual = query.boundary(node, geom);
        assertSame(expected, actual);
    }

    @Test
    public void boundaryDefault() throws Exception {
        Boundary expected = mock(Boundary.class);
        when(defaults.boundary(geom)).thenReturn(expected);
        Boundary actual = query.boundary(node, geom);

        assertSame(expected, actual);

    }
}