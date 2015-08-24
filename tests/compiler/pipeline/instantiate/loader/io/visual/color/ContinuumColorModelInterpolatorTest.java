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

package compiler.pipeline.instantiate.loader.io.visual.color;

import compiler.pipeline.instantiate.loader.InterpolatorTest;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.arguments.DoubleArgument;
import io.visual.color.ColorManager;
import org.junit.*;

import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ContinuumColorModelInterpolatorTest extends InterpolatorTest {

    private ContinuumColorModelDefaults defaults;
    private ContinuumColorModelInterpolator query;

    @Before
    @Override
    public void before() throws Exception {
        super.before();
        defaults = mock(ContinuumColorModelDefaults.class);
        query = new ContinuumColorModelInterpolator(load, defaults);
    }

    @Test
    public void averageLuminance() throws Exception {
        Supplier<Boolean> trigger = () -> query.averageLuminance(node, random);
        verifyBoolean("useLuminanceAverage", trigger);
    }

    @Test
    public void averageLuminanceDefault() throws Exception {
        when(defaults.averageLuminance()).thenReturn(true);
        Runnable trigger = () -> query.averageLuminance(node, random);
        verifyBooleanDefault("useLuminanceAverage", true, trigger);
    }

    @Test
    public void base() throws Exception {
        ColorModelLoader loader = mock(ColorModelLoader.class);
        when(load.getLoader(eq(node), eq("base"), anyBoolean()))
            .thenReturn(loader);

        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("base")).thenReturn(cNode);

        ColorManager expected = mock(ColorManager.class);
        when(loader.instantiate(cNode, p)).thenReturn(expected);

        ColorManager actual = query.base(node, p);
        assertSame(expected, actual);
    }

    @Test
    public void baseDefault() throws Exception {
        ColorManager expected = mock(ColorManager.class);
        when(defaults.base(p)).thenReturn(expected);
        ColorManager actual = query.base(node, p);

        assertSame(expected, actual);
    }

    @Test
    public void id() throws Exception {
        Supplier<String> trigger = () -> query.id(node);
        verifyString("id", trigger);
    }

    @Test
    public void maxHue() throws Exception {
        Supplier<DoubleArgument> trigger =
            () -> query.maxHue(node, random);

        verifyDoubleArgument("maxHue", trigger);
    }

    @Test
    public void maxHueDefault() throws Exception {
        DoubleArgument expected = mock(DoubleArgument.class);
        when(defaults.maxHue()).thenReturn(expected);
        Runnable trigger = () -> query.maxHue(node, random);
        verifyDoubleArgumentDefault("maxHue", expected, trigger);
    }

    @Test
    public void minHue() throws Exception {
        Supplier<DoubleArgument> trigger =
            () -> query.minHue(node, random);

        verifyDoubleArgument("minHue", trigger);
    }

    @Test
    public void minHueDefault() throws Exception {
        DoubleArgument expected = mock(DoubleArgument.class);
        when(defaults.minHue()).thenReturn(expected);
        Runnable trigger = () -> query.minHue(node, random);
        verifyDoubleArgumentDefault("minHue", expected, trigger);
    }

    @Test
    public void minSat() throws Exception {
        Supplier<DoubleArgument> trigger =
            () -> query.minSat(node, random);

        verifyDoubleArgument("minSat", trigger);
    }

    @Test
    public void minSatDefault() throws Exception {
        DoubleArgument expected = mock(DoubleArgument.class);
        when(defaults.minSat()).thenReturn(expected);
        Runnable trigger = () -> query.minSat(node, random);
        verifyDoubleArgumentDefault("minSat", expected, trigger);
    }

    @Test
    public void maxSat() throws Exception {
        Supplier<DoubleArgument> trigger =
            () -> query.maxSat(node, random);

        verifyDoubleArgument("maxSat", trigger);
    }

    @Test
    public void maxSatDefault() throws Exception {
        DoubleArgument expected = mock(DoubleArgument.class);
        when(defaults.maxSat()).thenReturn(expected);
        Runnable trigger = () -> query.maxSat(node, random);
        verifyDoubleArgumentDefault("maxSat", expected, trigger);
    }

    @Test
    public void maxLum() throws Exception {
        Supplier<DoubleArgument> trigger =
            () -> query.maxLum(node, random);

        verifyDoubleArgument("maxLum", trigger);
    }

    @Test
    public void maxLumDefault() throws Exception {
        DoubleArgument expected = mock(DoubleArgument.class);
        when(defaults.maxLum()).thenReturn(expected);
        Runnable trigger = () -> query.maxLum(node, random);
        verifyDoubleArgumentDefault("maxLum", expected, trigger);
    }

    @Test
    public void minLum() throws Exception {
        Supplier<DoubleArgument> trigger =
            () -> query.minLum(node, random);

        verifyDoubleArgument("minLum", trigger);
    }

    @Test
    public void minLumDefault() throws Exception {
        DoubleArgument expected = mock(DoubleArgument.class);
        when(defaults.minLum()).thenReturn(expected);
        Runnable trigger = () -> query.minLum(node, random);
        verifyDoubleArgumentDefault("minLum", expected, trigger);
    }
}