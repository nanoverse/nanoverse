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

package io.serialize;

import factory.io.serialize.SerializationFactory;
import io.serialize.binary.HighlightWriter;
import io.serialize.binary.TimeWriter;
import io.serialize.binary.VisualizationSerializer;
import io.serialize.interactive.ProgressReporter;
import io.serialize.text.*;
import org.dom4j.Element;
import org.dom4j.tree.BaseElement;
import structural.MockGeneralParameters;
import test.EslimeLatticeTestCase;

/**
 * Created by dbborens on 1/17/14.
 */
public class SerializationFactoryTest extends EslimeLatticeTestCase {

    private MockGeneralParameters p;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        p = new MockGeneralParameters();
        p.setInstancePath(outputPath);
        p.setPath(outputPath);
    }

    private void doTest(String elementName, Class expected) {
        Element e = new BaseElement(elementName);
        Serializer result = SerializationFactory.instantiate(e, p, layerManager);
        Class actual = result.getClass();
        assertEquals(expected, actual);
    }

    public void testCellStateWriter() {
        doTest("cell-state-writer", LegacyCellStateWriter.class);
    }

    public void testHaltTimeWriter() {
        doTest("halt-time-writer", HaltTimeWriter.class);
    }

    public void testProgressReporter() {
        doTest("progress-reporter", ProgressReporter.class);
    }

    public void testCensusWriter() {
        doTest("census-writer", CensusWriter.class);

    }

    public void testIntervalWriter() {
        doTest("interval-writer", IntervalWriter.class);
    }

//    public void testContinuumStateWriter() {
//        doTest("continuum-state-writer", ContinuumStateWriter.class);
//    }

    public void testTimeWriter() {
        doTest("time-writer", TimeWriter.class);
    }

    public void testCoordinateIndexer() {
        doTest("coordinate-indexer", CoordinateIndexer.class);
    }

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
        Serializer expected = new HighlightWriter(p, new int[]{3, 5}, layerManager);
        assertEquals(expected, actual);
    }

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

    public void testIndividualHaltWriter() {
        doTest("individual-halt-writer", IndividualHaltWriter.class);
    }

    public void testRunningTimeWriter() {
        doTest("running-time-writer", RunningTimeWriter.class);
    }

    public void testRandomSeedWriter() {
        doTest("random-seed-writer", RandomSeedWriter.class);
    }

    public void testSurfaceCensusWriter() {
        doTest("surface-census-writer", SurfaceCensusWriter.class);

    }

}
