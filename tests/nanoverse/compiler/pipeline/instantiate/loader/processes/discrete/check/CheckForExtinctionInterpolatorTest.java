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

package nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.check;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.runtime.control.arguments.DoubleArgument;
import org.junit.*;

import java.util.function.Supplier;

import static org.mockito.Mockito.*;

public class CheckForExtinctionInterpolatorTest extends InterpolatorTest {

    private CheckForExtinctionDefaults defaults;
    private CheckForExtinctionInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(CheckForExtinctionDefaults.class);
        query = new CheckForExtinctionInterpolator(load, null, null, defaults);
    }

    @Test
    public void threshold() throws Exception {
        Supplier<DoubleArgument> trigger = () -> query.threshold(node, random);
        verifyDoubleArgument("threshold", trigger);
    }

    @Test
    public void thresholdDefault() throws Exception {
        DoubleArgument expected = mock(DoubleArgument.class);
        when(defaults.threshold()).thenReturn(expected);

        Runnable trigger = () -> query.threshold(node, random);
        verifyDoubleArgumentDefault("threshold", expected, trigger);
    }
}