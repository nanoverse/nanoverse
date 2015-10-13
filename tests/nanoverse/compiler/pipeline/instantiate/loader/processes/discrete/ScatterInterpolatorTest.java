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

package nanoverse.compiler.pipeline.instantiate.loader.processes.discrete;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.compiler.pipeline.instantiate.loader.agent.AgentDescriptorLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.arguments.AgentDescriptor;
import org.junit.*;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class ScatterInterpolatorTest extends InterpolatorTest {

    private ScatterDefaults defaults;
    private ScatterInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(ScatterDefaults.class);
        query = new ScatterInterpolator(load, null, null, defaults);
    }

    @Test
    public void description() throws Exception {
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("description")).thenReturn(cNode);

        AgentDescriptorLoader loader = mock(AgentDescriptorLoader.class);
        when(load.getLoader(eq(node), eq("description"), anyBoolean())).thenReturn(loader);

        AgentDescriptor expected = mock(AgentDescriptor.class);
        when(loader.instantiate(cNode, lm, p)).thenReturn(expected);

        AgentDescriptor actual = query.description(node, lm, p);
        assertSame(expected, actual);
    }

    @Test
    public void descriptionDefault() throws Exception {
        AgentDescriptor expected = mock(AgentDescriptor.class);
        when(defaults.description(lm, p)).thenReturn(expected);

        AgentDescriptor actual = query.description(node, lm, p);
        assertSame(expected, actual);
    }
}