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

package compiler.pipeline.instantiate.loader.layers.continuum;

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.instantiate.loader.InterpolatorTest;
import layers.continuum.ContinuumLayerScheduler;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ContinuumLayerSchedulerInterpolatorTest extends InterpolatorTest {

    private ContinuumLayerSchedulerInterpolator query;
    private ContinuumLayerSchedulerDefaults defaults;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(ContinuumLayerSchedulerDefaults.class);
        query = new ContinuumLayerSchedulerInterpolator(load, defaults);
    }

    @Test
    public void operators() throws Exception {
        Boolean expected = true;
        when(load.aBoolean(eq(node), eq("operators"), eq(random), any()))
                .thenReturn(expected);
        Boolean actual = query.operators(node, random);
        assertEquals(expected, actual);
    }

    @Test
    public void operatorsDefault() throws Exception {
        Boolean expected = true;
        when(defaults.operators()).thenReturn(expected);
        verifyBooleanDefault("operators", expected,
                () -> query.operators(node, random));
    }

}