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
import nanoverse.runtime.control.arguments.CellDescriptor;
import org.junit.*;

import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FillInterpolatorTest extends InterpolatorTest {

    private FillDefaults defaults;
    private FillInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(FillDefaults.class);
        query = new FillInterpolator(load, null, null, defaults);
    }

    @Test
    public void skipFilled() throws Exception {
        Supplier<Boolean> trigger = () -> query.skipFilled(node, random);
        verifyBoolean("skipFilled", trigger);
    }

    @Test
    public void skipFilledDefault() throws Exception {
        when(defaults.skipFilled()).thenReturn(true);
        Runnable trigger = () -> query.skipFilled(node, random);
        verifyBooleanDefault("skipFilled", true, trigger);
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