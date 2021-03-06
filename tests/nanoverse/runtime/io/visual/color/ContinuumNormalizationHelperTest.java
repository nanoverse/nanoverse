/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package nanoverse.runtime.io.visual.color;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.layers.SystemState;
import org.junit.*;
import test.TestBase;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ContinuumNormalizationHelperTest extends TestBase {

    private String continuumId = "test";
    private Coordinate c;
    private SystemState systemState;
    private Extrema extrema;
    private ContinuumNormalizationHelper query;

    @Before
    public void before() throws Exception {
        c = mock(Coordinate.class);
        systemState = mock(SystemState.class);
        extrema = mock(Extrema.class);
        when(systemState.getContinuumExtrema(continuumId)).thenReturn(extrema);
        query = new ContinuumNormalizationHelper(continuumId);
    }

    @Test
    public void normalizeMin() throws Exception {
        doNormalizationTest(-1.0, -1.0, 3.0, 0.0);
    }

    private void doNormalizationTest(double rawValue, double min, double max, double expected) throws Exception {
        when(systemState.getContinuumValue(continuumId, c)).thenReturn(rawValue);
        when(extrema.min()).thenReturn(min);
        when(extrema.max()).thenReturn(max);

        double actual = query.normalize(c, systemState);
        assertEquals(expected, actual, epsilon);
    }

    @Test
    public void normalizeMid() throws Exception {
        doNormalizationTest(1.0, -1.0, 3.0, 0.5);
    }

    @Test
    public void normalizeMax() throws Exception {
        doNormalizationTest(3.0, -1.0, 3.0, 1.0);
    }
}