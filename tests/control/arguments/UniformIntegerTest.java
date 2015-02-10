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

package control.arguments;

import java.util.Random;

/**
 * Created by David B Borenstein on 4/7/14.
 */
public class UniformIntegerTest extends ArgumentTest {

    protected double[] results;
    private int min = 0;
    private int max = 10;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Random random = new Random(super.RANDOM_SEED);
        UniformInteger query = new UniformInteger(min, max, random);

        results = new double[NUM_ITERATES];
        for (int i = 0; i < NUM_ITERATES; i++) {
            results[i] = query.next();
        }
    }

    public void testMean() throws Exception {
        double expected = 5.0;
        double actual = mean(results);

        double var = (1.0 / 12.0) * Math.pow(max - min, 2.0);
        assertEquals(expected, actual, var);
    }
}
