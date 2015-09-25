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
import org.junit.*;

import java.util.function.Supplier;

import static org.mockito.Mockito.*;

public class ReactionInterpolatorTest extends InterpolatorTest {

    private ReactionDefaults defaults;
    private ReactionInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(ReactionDefaults.class);
        query = new ReactionInterpolator(load, defaults);
    }

    @Test
    public void inj() throws Exception {
        Supplier<Double> trigger = () -> query.inj(node, random);
        verifyDouble("inj", trigger);
    }

    @Test
    public void injDefault() throws Exception {
        Runnable trigger = () -> query.inj(node, random);
        double expected = 3.0;
        when(defaults.inj()).thenReturn(expected);
        verifyDoubleDefault("inj", expected, trigger);
    }

    @Test
    public void exp() throws Exception {
        Supplier<Double> trigger = () -> query.exp(node, random);
        verifyDouble("exp", trigger);
    }

    @Test
    public void expDefault() throws Exception {
        Runnable trigger = () -> query.exp(node, random);
        double expected = 3.0;
        when(defaults.exp()).thenReturn(expected);
        verifyDoubleDefault("exp", expected, trigger);
    }

    @Test
    public void layer() throws Exception {
        Supplier<String> trigger = () -> query.layer(node);
        verifyString("layer", trigger);
    }

}