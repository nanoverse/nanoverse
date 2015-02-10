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

package io.serialize.binary;

import geometry.MockGeometry;
import io.visual.MockVisualization;
import layers.MockLayerManager;
import layers.cell.CellLayer;
import structural.MockGeneralParameters;
import test.EslimeTestCase;

import java.io.File;

/**
 * As a graphics I/O class, this seemed better suited to an integration
 * test than to a system of unit tests. The fixtures are based on those
 * of the SystemStateReader class, and the output utilizes a base version
 * of the MapVisualization class.
 * <p>
 * Created by dbborens on 4/2/14.
 */
public class VisualizationSerializerTest extends EslimeTestCase {
    private MockVisualization visualization;
    private VisualizationSerializer query;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MockLayerManager lm = new MockLayerManager();
        MockGeometry geom = buildMockGeometry();
        CellLayer layer = new CellLayer(geom);
        lm.setCellLayer(layer);
        visualization = new MockVisualization();
        MockGeneralParameters p = new MockGeneralParameters();

        // The class attempts to slurp in data from a simulation, and throws an
        // exception if it isn't there. We use the SystemStateReader data
        // because it exists.
        p.setInstancePath(fixturePath + "SystemStateReader/");
        String prefix = "../../output/test";
        query = new VisualizationSerializer(p, visualization, prefix, lm);
        query.init();
    }

    public void testLifeCycle() {
        query.dispatchHalt(null);
        assertTrue(visualization.isInit());
        assertTrue(visualization.isRender());
        assertTrue(visualization.isConclude());
        checkFileExists("test1.7.png");
        checkFileExists("test4.8.png");
    }

    private void checkFileExists(String fn) {
        File file = new File(outputPath + fn);
        assertTrue(file.exists());
    }
}
