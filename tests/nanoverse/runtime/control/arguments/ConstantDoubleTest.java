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

package nanoverse.runtime.control.arguments;

import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by David B Borenstein on 4/7/14.
 */
public class ConstantDoubleTest extends ArgumentTest {
    protected double[] results;
    private double value = 12e-5;

    @Before
    public void setUp() throws Exception {
        ConstantDouble query = new ConstantDouble(value);

        results = new double[NUM_ITERATES];
        for (int i = 0; i < NUM_ITERATES; i++) {
            results[i] = query.next();
        }
    }

    @Test
    public void testMean() throws Exception {

        double expected = value;
        double actual = mean(results);

        assertEquals(expected, actual, epsilon);
    }

}
