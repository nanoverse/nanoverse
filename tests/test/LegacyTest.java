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
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.*;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.set.*;
import nanoverse.runtime.geometry.shape.*;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.runtime.processes.discrete.CellProcessArguments;
import nanoverse.runtime.structural.MockGeneralParameters;
import no.uib.cipr.matrix.Vector;

import java.util.*;
import java.util.stream.*;

import static org.junit.Assert.*;

public abstract class LegacyTest {

    protected final String eslimeRoot = "./";
    protected final String outputPath = eslimeRoot + "/output/";
    protected final String fixturePath = eslimeRoot + "/fixtures/";
    protected final int RANDOM_SEED = 0;
    protected double epsilon = calcEpsilon();

    protected static void assertNotEquals(Object p, Object q) {
        boolean equality = p.equals(q);
        assertFalse(equality);
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

    // Superceded by Comparable[] implementation
    protected void assertArraysEqual(int[] actual, int[] expected, boolean sort) {
        assertEquals(expected.length, actual.length);

        if (sort) {
            Arrays.sort(actual);
            Arrays.sort(expected);
        }

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    protected void assertArraysEqual(double[] expected, double[] actual, boolean sort) {
        assertEquals(expected.length, actual.length);

        if (sort) {
            Arrays.sort(actual);
            Arrays.sort(expected);
        }

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i], epsilon);
        }
    }

    protected void assertArraysEqual(boolean[] actual, boolean[] expected) {
        assertEquals(expected.length, actual.length);

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    protected void assertArraysEqual(Comparable[] expected, Comparable[] actual, boolean sort) {
        assertEquals(expected.length, actual.length);

        if (sort) {
            Arrays.sort(actual);
            Arrays.sort(expected);
        }

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }

    }

    protected void assertArraysNotEqual(Comparable[] expected, Comparable[] actual, boolean sort) {
        // If the arrays are of unequal length, we have satisfied the assertion.
        if (expected.length != actual.length)
            return;

        if (sort) {
            Arrays.sort(actual);
            Arrays.sort(expected);
        }

        // If any element of the array is unequal, we have satisfied the assertion.
        for (int i = 0; i < expected.length; i++) {
            if (!expected[i].equals(actual[i])) {
                return;
            }
            assertEquals(expected[i], actual[i]);
        }

        // In all other cases, the assertion has failed.
        fail("Arrays are equal when they were expected to be unequal.");
    }

    protected void assertVectorsEqual(Vector expected, Vector actual, double tolerance) {
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i), tolerance);
        }
    }

    protected Coordinate[] clean(Coordinate[] check) {
        ArrayList<Coordinate> retain = new ArrayList<Coordinate>(check.length);
        for (Coordinate c : check) {
            if (c != null) {
                retain.add(c);
            }
        }

        return retain.toArray(new Coordinate[0]);
    }

    private double calcEpsilon() {
        double eps = 1.0D;

        while ((1.0 + (eps / 2.0)) != 1.0) {
            eps /= 2.0;
        }

        return eps;
    }

    /**
     * Generate a basic mock nanoverse.runtime.geometry with a defined
     * set of canonical coordinates.
     */
    protected MockGeometry buildMockGeometry() {
        Coordinate[] canonicals = new Coordinate[]{
                new Coordinate3D(0, 0, 0, 0),
                new Coordinate3D(0, 0, 1, 0),
                new Coordinate3D(0, 1, 0, 0),
                new Coordinate3D(0, 1, 1, 0),
                new Coordinate3D(1, 0, 0, 0)
        };
        MockGeometry ret = new MockGeometry();
        ret.setCanonicalSites(canonicals);

        return ret;
    }

    protected MockGeneralParameters makeMockGeneralParameters() {
        MockGeneralParameters ret = new MockGeneralParameters();
        ret.setInstancePath(outputPath);
        ret.setPath(outputPath);
        ret.initializeRandom(RANDOM_SEED);
        return ret;
    }

    protected BaseProcessArguments makeBaseProcessArguments(LayerManager layerManager, GeneralParameters p) {
        IntegerArgument start = new ConstantInteger(0);
        IntegerArgument period = new ConstantInteger(1);
        return new BaseProcessArguments(layerManager, p, 0, start, period);
    }

    protected CellProcessArguments makeCellProcessArguments(Geometry geom) {
        CoordinateSet activeSites = new CompleteSet(geom);
        IntegerArgument maxTargets = new ConstantInteger(-1);
        return new CellProcessArguments(activeSites, maxTargets);
    }

    protected boolean arraysEqual(Object[] expected, Object[] actual) {
        if (expected == null && actual != null) {
            return false;
        }

        if (actual == null && expected != null) {
            return false;
        }

        if (actual.length != expected.length) {
            return false;
        }

        for (int i = 0; i < expected.length; i++) {
            if (expected[i] == null && actual[i] != null) {
                return false;
            }

            if (actual[i] == null && expected[i] != null) {
                return false;
            }

            if (!expected[i].equals(actual[i])) {
                return false;
            }
        }
        return true;
    }

    protected void assertMapsEqual(Map p, Map q) {
        assertCollectionsEqual(p.keySet(), q.keySet());

        for (Object o : p.keySet()) {
            Object pVal = p.get(o);
            Object qVal = q.get(o);

            if (!pVal.equals(qVal)) {
                fail("Value of key" + o + " does not match");
            }
        }
    }

    protected void assertCollectionsEqual(Collection p, Collection q) {
        if (p.size() != q.size()) {
            fail("Expected " + p.size() + " elements but found " + q.size());
        }

        if (!p.containsAll(q)) {
            fail("Contents of sets do not match");
        }
    }

    protected Geometry makeLinearGeometry(int length) {
        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, length);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geometry = new Geometry(lattice, shape, boundary);
        return geometry;
    }
}
