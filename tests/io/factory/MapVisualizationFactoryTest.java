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

package io.factory;

import factory.io.visual.map.MapVisualizationFactory;
import io.visual.VisualizationProperties;
import io.visual.color.*;
import io.visual.glyph.MockGlyph;
import io.visual.highlight.HighlightManager;
import io.visual.map.MapVisualization;
import org.dom4j.Element;
import org.junit.*;
import structural.MockGeneralParameters;
import test.LegacyLatticeTest;

import static org.junit.Assert.assertEquals;
/**
 * Created by dbborens on 4/3/14.
 */
public class MapVisualizationFactoryTest extends LegacyLatticeTest {
    private Element root;
    private MockGeneralParameters p;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        setUpGeometry();
        root = readXmlFile("factories/MapVisualizationFactoryTest.xml");
        p = makeMockGeneralParameters();
    }

    private void setUpGeometry() {
        geom.setDimensionality(2);
        geom.setConnectivity(3);
    }

    @Test
    public void testTypicalCase() throws Exception {
        Element typicalCase = root.element("typical-case");
        MapVisualization actual = MapVisualizationFactory.instantiate(typicalCase, p);
        actual.init(geom, null, null);

        MapVisualization expected = makeTypicalCase();
        assertEquals(expected, actual);
    }

    private MapVisualization makeTypicalCase() {
        ColorManager colorManager = new DefaultColorManager();
        HighlightManager highlightManager = new HighlightManager();
        highlightManager.setGlyph(0, new MockGlyph());
        int edge = 5;

        VisualizationProperties mapState = new VisualizationProperties(colorManager, edge, 1);
        mapState.setHighlightManager(highlightManager);

        MapVisualization mapVisualization = new MapVisualization(mapState);
        mapVisualization.init(geom, null, null);
        return mapVisualization;
    }

    @Test
    public void testMinimalCase() throws Exception {
        Element minimalCase = root.element("minimal-case");
        MapVisualization actual = MapVisualizationFactory.instantiate(minimalCase, p);
        actual.init(geom, null, null);

        MapVisualization expected = makeMinimalCase();
        assertEquals(expected, actual);
    }

    private MapVisualization makeMinimalCase() {
        ColorManager colorManager = new DefaultColorManager();
        HighlightManager highlightManager = new HighlightManager();
        int edge = 10;

        VisualizationProperties mapState = new VisualizationProperties(colorManager, edge, 1);
        mapState.setHighlightManager(highlightManager);

        MapVisualization mapVisualization = new MapVisualization(mapState);
        mapVisualization.init(geom, null, null);
        return mapVisualization;
    }

}
