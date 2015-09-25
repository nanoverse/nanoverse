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

package nanoverse.compiler.pipeline.instantiate.loader.agent.action;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.runtime.control.arguments.IntegerArgument;
import org.junit.*;

import java.util.function.Supplier;

import static org.mockito.Mockito.*;

public class ExpandWeightedInterpolatorTest extends InterpolatorTest {

    private ExpandWeightedDefaults defaults;
    private ExpandWeightedInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(ExpandWeightedDefaults.class);
        query = new ExpandWeightedInterpolator(load, defaults);
    }

    @Test
    public void targetHighlight() throws Exception {
        Supplier<IntegerArgument> trigger = () -> query.targetHighlight(node, random);
        verifyIntegerArgument("targetHighlight", trigger);
    }

    @Test
    public void targetHighlightDefault() throws Exception {
        IntegerArgument expected = mock(IntegerArgument.class);
        when(defaults.targetHighlight()).thenReturn(expected);
        Runnable trigger = () -> query.targetHighlight(node, random);
        verifyIntegerArgumentDefault("targetHighlight", expected, trigger);
    }

    @Test
    public void selfHighlight() throws Exception {
        Supplier<IntegerArgument> trigger = () -> query.selfHighlight(node, random);
        verifyIntegerArgument("selfHighlight", trigger);
    }

    @Test
    public void selfHighlightDefault() throws Exception {
        IntegerArgument expected = mock(IntegerArgument.class);
        when(defaults.selfHighlight()).thenReturn(expected);
        Runnable trigger = () -> query.selfHighlight(node, random);
        verifyIntegerArgumentDefault("selfHighlight", expected, trigger);
    }
}