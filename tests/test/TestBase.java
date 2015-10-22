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

package test;

import com.google.common.collect.Sets;
import com.google.common.io.Files;
import nanoverse.runtime.structural.utilities.EpsilonUtil;
import no.uib.cipr.matrix.*;
import no.uib.cipr.matrix.Vector;
import org.junit.Before;

import java.io.File;
import java.util.*;
import java.util.stream.*;

import static org.junit.Assert.*;

/**
 * Created by dbborens on 1/9/15.
 */
public abstract class TestBase {

    protected double epsilon;
    protected float floatEpsilon;

    protected static void assertVectorsEqual(Vector expected, Vector actual, double tolerance) {
        assertEquals(expected.size(), actual.size());

        IntStream.range(0, expected.size())
                .boxed()
                .forEach(i -> assertEquals(expected.get(i), actual.get(i), tolerance));
    }

    protected static void assertMatricesEqual(Matrix p, Matrix q, double tolerance) {
        assertEquals(p.numColumns(), q.numColumns());
        assertEquals(p.numRows(), q.numRows());

        IntStream.range(0, p.numRows())
                .forEach(i -> IntStream.range(0, p.numColumns())
                        .forEach(j ->
                            assertEquals(p.get(i, j), q.get(i, j), tolerance)));
    }

    protected static void assertMapsEqual(Map p, Map q) {
        assertCollectionsEqual(p.keySet(), q.keySet());
        p.keySet().forEach(key -> assertEquals(p.get(key), q.get(key)));
    }

    protected static void assertCollectionsEqual(Collection p, Collection q) {
        if (p.size() != q.size()) {
            fail("Expected " + p.size() + " elements but found " + q.size());
        }

        if (!p.containsAll(q)) {
            fail("Contents of sets do not match");
        }
    }

    protected static <T> void assertSetsEqual(Set<T> expected, Set<T> actual) {
        Set<T> difference = Sets.symmetricDifference(expected, actual);
        String differenceString = difference.stream().map(Object::toString).collect(Collectors.joining(", "));
        String errorString = "Unexpected difference between sets: " + differenceString;
        assertEquals(errorString, 0, difference.size());
    }

    protected static <T> void assertStreamsEqual(Stream<T> expected, Stream<T> actual) {
        List<T> expList = expected.collect(Collectors.toList());
        List<T> actList = actual.collect(Collectors.toList());
        assertEquals(expList, actList);
    }

    @Before
    public void calcEpsilon() {
        epsilon = EpsilonUtil.epsilon();
        floatEpsilon = EpsilonUtil.floatEpsilon();
    }

    public void assertFilesEqual(String expectedFn, String actualFn) throws Exception {
        File expected = new File(expectedFn);
        File actual = new File(actualFn);
        assertTrue(Files.equal(expected, actual));
    }
}
