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

package compiler.pipeline.instantiate.loader.processes;

import compiler.pipeline.instantiate.loader.InterpolatorTest;
import control.arguments.DoubleArgument;
import org.junit.*;

import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MockProcessInterpolatorTest extends InterpolatorTest {

    private MockProcessDefaults defaults;
    private MockProcessInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(MockProcessDefaults.class);
        query = new MockProcessInterpolator(load, null, defaults);
    }

    @Test
    public void identifier() throws Exception {
        Supplier<String> trigger = () -> query.identifier(node);
        verifyString("identifier", trigger);
    }

    @Test
    public void count() throws Exception {
        Supplier<Integer> trigger = () -> query.count(node, random);
        verifyInteger("count", trigger);
    }

    @Test
    public void countDefault() throws Exception {
        when(defaults.count()).thenReturn(7);
        Runnable trigger = () -> query.count(node, random);
        verifyIntegerDefault("count", 7, trigger);
    }

    @Test
    public void weight() throws Exception {
        Supplier<Double> trigger = () -> query.weight(node, random);
        verifyDouble("weight", trigger);
    }

    @Test
    public void weightDefault() throws Exception {
        when(defaults.weight()).thenReturn(7.0);
        Runnable trigger = () -> query.weight(node, random);
        verifyDoubleDefault("weight", 7.0, trigger);
    }
}