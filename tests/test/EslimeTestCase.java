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
import control.GeneralParameters;
import control.arguments.Argument;
import control.arguments.ConstantInteger;
import control.identifiers.Coordinate;
import geometry.Geometry;
import geometry.MockGeometry;
import geometry.boundaries.Arena;
import geometry.boundaries.Boundary;
import geometry.lattice.Lattice;
import geometry.lattice.LinearLattice;
import geometry.set.CompleteSet;
import geometry.set.CoordinateSet;
import geometry.shape.Line;
import geometry.shape.Shape;
import junit.framework.TestCase;
import layers.LayerManager;
import no.uib.cipr.matrix.Vector;
import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.BaseElement;
import processes.BaseProcessArguments;
import processes.discrete.CellProcessArguments;
import structural.MockGeneralParameters;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.*;

public abstract class EslimeTestCase extends TestCase {

    protected final String eslimeRoot = "./";
    protected final String outputPath = eslimeRoot + "/output/";
    protected final String fixturePath = eslimeRoot + "/fixtures/";
    protected final int RANDOM_SEED = 0;
    protected double epsilon = calcEpsilon();

    protected static void assertNotEquals(Object p, Object q) {
        boolean equality = p.equals(q);
        assertFalse(equality);
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

    protected void addElement(Element e, String name, String text) {
        Element rElem = new BaseElement(name);
        rElem.setText(text);
        e.add(rElem);
    }

    /**
     * Generate a basic mock geometry with a defined
     * set of canonical coordinates.
     */
    protected MockGeometry buildMockGeometry() {
        Coordinate[] canonicals = new Coordinate[]{
                new Coordinate(0, 0, 0, 0),
                new Coordinate(0, 0, 1, 0),
                new Coordinate(0, 1, 0, 0),
                new Coordinate(0, 1, 1, 0),
                new Coordinate(1, 0, 0, 0)
        };
        MockGeometry ret = new MockGeometry();
        ret.setCanonicalSites(canonicals);

        return ret;
    }

    protected Element readXmlFile(String fileName) throws IOException, DocumentException {

        String path = fixturePath + fileName;
        File file = new File(path);
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        Element root = document.getRootElement();
        return root;
    }

    protected MockGeneralParameters makeMockGeneralParameters() {
        MockGeneralParameters ret = new MockGeneralParameters();
        ret.setInstancePath(outputPath);
        ret.setPath(outputPath);
        ret.initializeRandom(RANDOM_SEED);
        return ret;
    }

    protected BaseProcessArguments makeBaseProcessArguments(LayerManager layerManager, GeneralParameters p) {
        Argument<Integer> start = new ConstantInteger(0);
        Argument<Integer> period = new ConstantInteger(1);
        return new BaseProcessArguments(layerManager, p, 0, start, period);
    }

    protected CellProcessArguments makeCellProcessArguments(Geometry geom) {
        CoordinateSet activeSites = new CompleteSet(geom);
        Argument<Integer> maxTargets = new ConstantInteger(-1);
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

    protected void assertCollectionsEqual(Collection p, Collection q) {
        if (p.size() != q.size()) {
            fail("Expected " + p.size() + " elements but found " + q.size());
        }

        if (!p.containsAll(q)) {
            fail("Contents of sets do not match");
        }
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

    protected Geometry makeLinearGeometry(int length) {
        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, length);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geometry = new Geometry(lattice, shape, boundary);
        return geometry;
    }
}
