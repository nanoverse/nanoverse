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

package compiler.pipeline.instantiate.loader.processes.discrete;

import compiler.pipeline.instantiate.loader.InterpolatorTest;
import compiler.pipeline.instantiate.loader.agent.AgentDescriptorLoader;
import compiler.pipeline.instantiate.loader.processes.discrete.cluster.ScatterClustersHelperLoader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.arguments.CellDescriptor;
import org.junit.*;
import processes.discrete.cluster.ScatterClustersHelper;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PowerScatterInterpolatorTest extends InterpolatorTest {

    private PowerScatterDefaults defaults;
    private PowerScatterInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(PowerScatterDefaults.class);
        query = new PowerScatterInterpolator(load, null, null, defaults);
    }

    @Test
    public void helper() throws Exception {
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("separation")).thenReturn(cNode);

        ScatterClustersHelperLoader loader = mock(ScatterClustersHelperLoader.class);
        when(load.getLoader(eq(node), eq("separation"), anyBoolean())).thenReturn(loader);

        ScatterClustersHelper expected = mock(ScatterClustersHelper.class);
        when(loader.instantiate(cNode, lm, p)).thenReturn(expected);

        ScatterClustersHelper actual = query.helper(node, lm, p);
        assertSame(expected, actual);
    }

    @Test
    public void helperDefault() throws Exception {
        ScatterClustersHelper expected = mock(ScatterClustersHelper.class);
        when(defaults.helper(lm, p)).thenReturn(expected);

        ScatterClustersHelper actual = query.helper(node, lm, p);
        assertSame(expected, actual);
    }

    @Test
    public void description() throws Exception {
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("description")).thenReturn(cNode);

        AgentDescriptorLoader loader = mock(AgentDescriptorLoader.class);
        when(load.getLoader(eq(node), eq("description"), anyBoolean())).thenReturn(loader);

        CellDescriptor expected = mock(CellDescriptor.class);
        when(loader.instantiate(cNode, lm, p)).thenReturn(expected);

        CellDescriptor actual = query.description(node, lm, p);
        assertSame(expected, actual);
    }

    @Test
    public void descriptionDefault() throws Exception {
        CellDescriptor expected = mock(CellDescriptor.class);
        when(defaults.description(lm, p)).thenReturn(expected);

        CellDescriptor actual = query.description(node, lm, p);
        assertSame(expected, actual);
    }
}