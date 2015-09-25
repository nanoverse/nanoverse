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

package nanoverse.runtime.io.serialize;

import nanoverse.runtime.factory.io.serialize.SerializationFactory;
import nanoverse.runtime.io.serialize.binary.*;
import nanoverse.runtime.io.serialize.interactive.ProgressReporter;
import nanoverse.runtime.io.serialize.text.*;
import org.dom4j.Element;
import org.dom4j.tree.BaseElement;
import org.junit.*;
import nanoverse.runtime.structural.MockGeneralParameters;
import test.LegacyLatticeTest;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
/**
 * Created by dbborens on 1/17/14.
 */
public class SerializationFactoryTest extends LegacyLatticeTest {

    private MockGeneralParameters p;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        p = new MockGeneralParameters();
        p.setInstancePath(outputPath);
        p.setPath(outputPath);
    }

    @Test
    public void testCellStateWriter() {
        doTest("cell-state-writer", LegacyCellStateWriter.class);
    }

    private void doTest(String elementName, Class expected) {
        Element e = new BaseElement(elementName);
        Serializer result = SerializationFactory.instantiate(e, p, layerManager);
        Class actual = result.getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void testHaltTimeWriter() {
        doTest("halt-time-writer", HaltTimeWriter.class);
    }

    @Test
    public void testProgressReporter() {
        doTest("progress-reporter", ProgressReporter.class);
    }

    @Test
    public void testCensusWriter() {
        doTest("census-writer", CensusWriter.class);

    }

    @Test
    public void testIntervalWriter() {
        doTest("interval-writer", IntervalWriter.class);
    }

//    public void testContinuumStateWriter() {
//        doTest("continuum-state-writer", ContinuumStateWriter.class);
//    }

    @Test
    public void testTimeWriter() {
        doTest("time-writer", TimeWriter.class);
    }

    @Test
    public void testCoordinateIndexer() {
        doTest("coordinate-indexer", CoordinateIndexer.class);
    }

    @Test
    public void testHighlightWriter() {
        Element e = new BaseElement("highlight-writer");
        // Create channels
        Element channels = new BaseElement("channels");
        Element c1 = new BaseElement("channel");
        c1.setText("3");
        Element c2 = new BaseElement("channel");
        c2.setText("5");
        channels.add(c1);
        channels.add(c2);
        e.add(channels);

        Serializer actual = SerializationFactory.instantiate(e, p, layerManager);
        Serializer expected = new HighlightWriter(p, Stream.of(3, 5), layerManager);
//        Serializer expected = new HighlightWriter(p, new int[]{3, 5}, layerManager);
        assertEquals(expected, actual);
    }

    @Test
    public void testVisualizationSerializer() {
        Element e = new BaseElement("visualization-serializer");
        Element vp = new BaseElement("visualization");
        Element m = new BaseElement("class");
        m.setText("mock");
        vp.add(m);
        e.add(vp);
        Serializer result = SerializationFactory.instantiate(e, p, layerManager);
        Class actual = result.getClass();
        assertEquals(VisualizationSerializer.class, actual);
    }

    @Test
    public void testIndividualHaltWriter() {
        doTest("individual-halt-writer", IndividualHaltWriter.class);
    }

    @Test
    public void testRunningTimeWriter() {
        doTest("running-time-writer", RunningTimeWriter.class);
    }

    @Test
    public void testRandomSeedWriter() {
        doTest("random-seed-writer", RandomSeedWriter.class);
    }

    @Test
    public void testSurfaceCensusWriter() {
        doTest("surface-census-writer", SurfaceCensusWriter.class);

    }

}
