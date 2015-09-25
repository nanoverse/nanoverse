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

package nanoverse.compiler.pipeline.instantiate.loader.layers.continuum;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.compiler.pipeline.instantiate.loader.geometry.boundary.BoundaryLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.arguments.GeometryDescriptor;
import nanoverse.runtime.geometry.boundaries.Boundary;
import nanoverse.runtime.layers.continuum.*;
import org.junit.*;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class ContinuumLayerInterpolatorTest extends InterpolatorTest {

    private ContinuumLayerInterpolator query;
    private ContinuumLayerDefaults defaults;
    private ContinuumLayerContent content;
    private ScheduledOperations so;

    private GeometryDescriptor geom;

    @Before
    public void before() throws Exception {
        super.before();
        geom = mock(GeometryDescriptor.class);
        content = mock(ContinuumLayerContent.class);
        so = mock(ScheduledOperations.class);
        defaults = mock(ContinuumLayerDefaults.class);
        query = new ContinuumLayerInterpolator(load, defaults);
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