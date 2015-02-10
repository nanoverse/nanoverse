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

package processes;

import test.EslimeTestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by dbborens on 3/7/14.
 */
public class MaxTargetHelperTest extends EslimeTestCase {
    // Set of all outcomes
    private Object[] omega;
    private Random random;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        omega = new Object[10];
        for (int i = 0; i < 10; i++) {
            omega[i] = new Object();
        }

        random = new Random(RANDOM_SEED);
    }

    public void testOverMaximum() throws Exception {
        doTest(1, 1);
    }

    public void testUnderMaximum() throws Exception {
        doTest(15, 10);
    }

    public void testNoMaximum() throws Exception {
        doTest(-1, 10);
    }

    public void testTrivial() throws Exception {
        doTest(0, 0);
    }

    public void testListCase() {
        List<Object> omegaList = Arrays.asList(omega);
        Object[] coordinates = MaxTargetHelper.respectMaxTargets(omegaList, 1, random);
        int actual = coordinates.length;
        int expected = 1;

        assertEquals(expected, actual);
    }

    private void doTest(int maxTargetsArg, int expected) {
        Object[] actualArr = MaxTargetHelper.respectMaxTargets(omega, maxTargetsArg, random);
        int actual = actualArr.length;
        assertEquals(expected, actual);
    }
}
