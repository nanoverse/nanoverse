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

import static org.junit.Assert.assertEquals;
/**
 * Created by dbborens on 3/5/14.
 */
public class RangeMapTest extends EslimeTestCase {
    private RangeMap<Integer> query;

    @Before
    public void setUp() throws Exception {
        query = new RangeMap<>();
    }

    @Test
    public void testAdd() throws Exception {
        // Verify that rangemap is empty.
        assertEquals(0.0, query.getTotalWeight(), epsilon);
        assertEquals(0, query.getNumBins());

        // Add an item.
        query.add(1, 0.5);

        // Verify that the total weight is the weight of the item added.
        assertEquals(0.5, query.getTotalWeight(), epsilon);

        // Verify that there is one item in the range map.
        assertEquals(1, query.getNumBins());
    }

    @Test
    public void testSelectTarget() throws Exception {
        setupThreeElementCase();
        Integer actual, expected;

        // First bin
        expected = 5;
        actual = query.selectTarget(0.25);
        assertEquals(expected, actual);

        // Second bin
        expected = -3;
        actual = query.selectTarget(1.0);
        assertEquals(expected, actual);

        // Third bin
        expected = 0;
        actual = query.selectTarget(1.75);
        assertEquals(expected, actual);
    }

    private void setupThreeElementCase() {
        query.add(5, 0.5);
        query.add(-3, 1.0);
        query.add(0, 0.5);
    }

    @Test
    public void testLowerBoundInclusivity() throws Exception {
        setupThreeElementCase();
        Integer actual, expected;

        // First bin
        expected = 5;
        actual = query.selectTarget(0.0);
        assertEquals(expected, actual);

        // Second bin
        expected = -3;
        actual = query.selectTarget(0.5);
        assertEquals(expected, actual);

        // Third bin
        expected = 0;
        actual = query.selectTarget(1.5);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTotalWeight() throws Exception {
        setupThreeElementCase();
        assertEquals(2.0, query.getTotalWeight(), epsilon);
    }

    /**
     * Make sure that, when two range maps have members of
     * unequal classes, they are not themselves considered
     * equal.
     *
     * @throws Exception
     */
    @Test
    public void testInequalityClassCase() throws Exception {
        setupThreeElementCase();
        RangeMap<String> other = new RangeMap<>();
        other.add("Due to type erasure, there is no way to check equality until at least one element is loaded. This may have been fixed in Java 8.", 1.0);
        assertNotEquals(query, other);
    }

    /**
     * Make sure that, when two range maps have members of
     * the same class with unequal values, they are not
     * considered equal.
     *
     * @throws Exception
     */
    @Test
    public void testInequalityValueCase() throws Exception {
        setupThreeElementCase();
        RangeMap<Integer> other = new RangeMap<>();
        other.add(1, 0.5);      // Different
        other.add(-3, 1.0);     // Same
        other.add(0, 0.5);      // Same

        assertNotEquals(query, other);
    }

    /**
     * Make sure that, when two range maps have members of
     * the same class with equal values, but they have
     * different weights, they are not considered equal.
     *
     * @throws Exception
     */
    @Test
    public void testInequalityWeightsCase() throws Exception {
        setupThreeElementCase();
        RangeMap<Integer> other = new RangeMap<>();
        other.add(5, 0.25);      // Different
        other.add(-3, 1.0);     // Same
        other.add(0, 0.5);      // Same

        assertNotEquals(query, other);
    }

    /**
     * Make sure that, when two range maps have members of
     * the same class with equal values, but in the wrong
     * order, they are not considered equal.
     *
     * @throws Exception
     */
    @Test
    public void testInequalityOrderCase() throws Exception {
        setupThreeElementCase();
        RangeMap<Integer> other = new RangeMap<>();
        other.add(-3, 1.0);     // Used to be second
        other.add(5, 0.5);      // Used to be first
        other.add(0, 0.5);      // Same

        assertNotEquals(query, other);
    }

    /**
     * Make sure that, if two range maps have the same ranges
     * in the same order pointing to equal items, they are
     * considered equal.
     *
     * @throws Exception
     */
    @Test
    public void testEquals() throws Exception {
        setupThreeElementCase();
        RangeMap<Integer> other = new RangeMap<>();
        other.add(5, 0.5);      // Same
        other.add(-3, 1.0);     // Same
        other.add(0, 0.5);      // Same

        assertEquals(query, other);
    }

    @Test
    public void testClone() throws Exception {
        RangeMap<Integer> clone = query.clone();
        assertEquals(query, clone);
    }

}

