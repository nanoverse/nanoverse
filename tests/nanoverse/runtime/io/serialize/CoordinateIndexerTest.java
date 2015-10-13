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

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.MockGeometry;
import nanoverse.runtime.io.serialize.text.CoordinateIndexer;
import nanoverse.runtime.layers.MockLayerManager;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.structural.MockGeneralParameters;
import nanoverse.runtime.structural.utilities.FileConventions;
import org.junit.*;
import test.FileAssertions;

/**
 * Created by dbborens on 12/10/13.
 */
public class CoordinateIndexerTest extends FileAssertions {

    private MockGeometry geom;
    private MockGeneralParameters params;
    private CoordinateIndexer indexer;
    private MockLayerManager lm;

    @Before
    public void setUp() {
        geom = new MockGeometry();

        Coordinate[] canonicals = new Coordinate[]{
            new Coordinate2D(0, 0, 0),
            new Coordinate2D(0, 1, 0),
            new Coordinate2D(1, 0, 0),
            new Coordinate2D(1, 1, 0)
        };

        geom.setCanonicalSites(canonicals);
        params = new MockGeneralParameters();
        params.setInstancePath(outputPath);
        lm = new MockLayerManager();
        AgentLayer layer = new AgentLayer(geom);
        lm.setAgentLayer(layer);
        indexer = new CoordinateIndexer(params, lm);
        indexer.init();
    }

    @Test
    public void testCoordinateIndexer() throws Exception {
        // dispatchHalt instructs the indexer to build the index
        indexer.dispatchHalt(null);

//        // Compare output to fixture
//        String fixture = fixturePath + FileConventions.COORDINATE_FILENAME;
//        String output = outputPath + FileConventions.COORDINATE_FILENAME;

        assertOutputMatchesFixture(FileConventions.COORDINATE_FILENAME, true);
//        assertFilesEqual(FileConventions.COORDINATE_FILENAME);
    }

}
