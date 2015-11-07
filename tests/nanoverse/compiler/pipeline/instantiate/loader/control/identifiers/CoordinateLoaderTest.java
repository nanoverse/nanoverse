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

package nanoverse.compiler.pipeline.instantiate.loader.control.identifiers;

import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.AgentLayer;
import org.junit.*;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class CoordinateLoaderTest {

    private CoordinateAdapter adapter;
    private CoordinateLoader query;

    @Before
    public void before() throws Exception {
        adapter = mock(CoordinateAdapter.class);
        query = new CoordinateLoader(adapter);
    }

    @Test
    public void instantiate() throws Exception {
        LayerManager lm = mock(LayerManager.class);
        AgentLayer layer = mock(AgentLayer.class);
        when(lm.getAgentLayer()).thenReturn(layer);

        Geometry geom = mock(Geometry.class);
        when(layer.getGeometry()).thenReturn(geom);

        CoordinateSubclassLoader loader = mock(CoordinateSubclassLoader.class);
        when(adapter.getLoader(geom)).thenReturn(loader);

        MapObjectNode node = mock(MapObjectNode.class);
        GeneralParameters p = mock(GeneralParameters.class);
        Coordinate expected = mock(Coordinate.class);
        when(loader.instantiate(node, lm, p)).thenReturn(expected);

        Coordinate actual = query.instantiate(node, lm, p);
        assertSame(expected, actual);
    }
}