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

package nanoverse.runtime.io.deserialize;

import org.junit.*;
import test.LegacyTest;

/**
 * Created by dbborens on 3/28/14.
 */
public class TimeReaderTest extends LegacyTest {

    private TimeReader query;

    @Before
    public void setUp() throws Exception {
        query = new TimeReader(fixturePath);
    }

    @Test
    public void testGetTimes() throws Exception {
        double[] expected = new double[]{0.5, 1.3};
        double[] actual = query.getTimes();
        assertArraysEqual(expected, actual, false);
    }

    @Test
    public void testGetFrames() throws Exception {
        int[] expected = new int[]{2, 4};
        int[] actual = query.getFrames();
        assertArraysEqual(expected, actual, false);
    }
}
