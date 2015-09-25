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

package nanoverse.compiler.pipeline.instantiate.loader.processes;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.runtime.control.arguments.IntegerArgument;
import org.junit.*;

import java.util.function.Supplier;

import static org.mockito.Mockito.*;

public class BaseProcessArgumentsInterpolatorTest extends InterpolatorTest {

    private BaseProcessArgumentsDefaults defaults;
    private BaseProcessArgumentsInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(BaseProcessArgumentsDefaults.class);
        query = new BaseProcessArgumentsInterpolator(load, defaults);
    }

    @Test
    public void period() throws Exception {
        Supplier<IntegerArgument> trigger = () -> query.period(node, random);
        verifyIntegerArgument("period", trigger);
    }

    @Test
    public void periodDefault() throws Exception {
        IntegerArgument expected = mock(IntegerArgument.class);
        when(defaults.period()).thenReturn(expected);
        Runnable trigger = () -> query.period(node, random);
        verifyIntegerArgumentDefault("period", expected, trigger);
    }

    @Test
    public void start() throws Exception {
        Supplier<IntegerArgument> trigger = () -> query.start(node, random);
        verifyIntegerArgument("start", trigger);
    }

    @Test
    public void startDefault() throws Exception {
        IntegerArgument expected = mock(IntegerArgument.class);
        when(defaults.start()).thenReturn(expected);
        Runnable trigger = () -> query.start(node, random);
        verifyIntegerArgumentDefault("start", expected, trigger);
    }
}