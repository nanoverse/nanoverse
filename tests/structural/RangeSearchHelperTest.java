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

package structural;

import org.junit.*;
import test.EslimeTestCase;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
/**
 * The RangeSearchHelper performs a binary search between sequential
 * bins. This is used as part of the RangeMap object. The argument
 * is an array of bin floors, which has an extra element representing
 * the hypothetical floor of one more bin (ie, the ceiling of the last
 * bin).
 * <p>
 * Created by David B Borenstein on 3/19/14.
 */
public class RangeSearchHelperTest extends EslimeTestCase {

    private RangeSearchHelper query;

    @Before
    public void setUp() throws Exception {
        ArrayList<Double> bins = new ArrayList<>();
        // Remember that a dummy bin of 0.0 is required...
        bins.add(0.0);
        bins.add(0.5);
        bins.add(1.5);
        bins.add(2.0);
        query = new RangeSearchHelper(bins);
    }

    @Test
    public void testFindKey() throws Exception {
        int actual;

        // First bin
        actual = query.findKey(0.25);
        assertEquals(0, actual);

        // Second bin
        actual = query.findKey(1.0);
        assertEquals(1, actual);

        // Third bin
        actual = query.findKey(1.75);
        assertEquals(2, actual);
    }

    @Test
    public void testLowerBoundInclusivity() throws Exception {
        int actual;

        // First bin
        actual = query.findKey(0.0);
        assertEquals(0, actual);

        // Second bin
        actual = query.findKey(0.5);
        assertEquals(1, actual);

        // Third bin
        actual = query.findKey(1.5);
        assertEquals(2, actual);
    }

    /**
     * Verify that a single bin case works fine. Regression
     * test for problem case.
     *
     * @throws Exception
     */
    @Test
    public void testOneBinRegression() throws Exception {
        ArrayList<Double> bins = new ArrayList<>();
        bins.add(0.0);
        bins.add(0.5);

        query = new RangeSearchHelper(bins);
        int actual = query.findKey(0.25);
        assertEquals(0, actual);
    }
}
