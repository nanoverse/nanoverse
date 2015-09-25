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

package nanoverse.runtime.io.visual.glyph;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;
import nanoverse.runtime.io.deserialize.MockCoordinateDeindexer;
import nanoverse.runtime.io.visual.VisualizationProperties;
import nanoverse.runtime.io.visual.color.*;
import nanoverse.runtime.io.visual.highlight.*;
import nanoverse.runtime.io.visual.map.MapVisualization;
import nanoverse.runtime.layers.*;
import org.junit.*;
import test.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

/**
 * Integration test for glyphs.
 * <p>
 * Created by dbborens on 4/3/14.
 */
public abstract class GlyphTest extends LegacyTest {

    protected SystemState systemState;
    protected VisualizationProperties mapState;
    Geometry geometry;
    private HighlightManager highlightManager;
    private MapVisualization map;

    @Before
    public void setUp() throws Exception {
        geometry = makeGeometry();

        // Create 10x10 triangular lattice.
        ColorManager colorManager = new DefaultColorManager();

        // Create a 10 x 10 hexagonal map.
        mapState = new VisualizationProperties(colorManager, 50, 1);

        // Create highlight manager.
        highlightManager = new HighlightManager();
        mapState.setHighlightManager(highlightManager);

        // Channel 0 has a small glyph.
        Glyph small = makeGlyph();
        highlightManager.setGlyph(0, small);

        // Create map visualization.
        map = new MapVisualization(mapState);
        map.init(geometry, null, null);

        // Create system state
        systemState = makeSystemState(geometry);

    }

    protected abstract Glyph makeGlyph();

    protected LightweightSystemState makeSystemState(Geometry geom) {
        MockCoordinateDeindexer deindexer = new MockCoordinateDeindexer();
        deindexer.setUnderlying(geom.getCanonicalSites());


        LightweightSystemState ret = new LightweightSystemState(geom);
        populateStateAndHealth(geom, ret);
        Set<Coordinate> highlights = new HashSet<>();
        for (Coordinate c : geom.getCanonicalSites()) {
            highlights.add(c);
        }
        ret.setHighlights(0, highlights);
        ret.setTime(0.0);
        ret.setFrame(0);

        return ret;
    }

    protected void populateStateAndHealth(Geometry geom, LightweightSystemState systemState) {
        int n = geom.getCanonicalSites().length;
        int[] state = new int[n];

        for (int i = 0; i < n; i++) {
            state[i] = 0;
        }
        systemState.initCellLayer(state);

    }

    protected Geometry makeGeometry() {
        Lattice lattice = new TriangularLattice();
        Shape shape = new Rectangle(lattice, 10, 10);
        Boundary boundary = new Absorbing(shape, lattice);
        Geometry geometry = new Geometry(lattice, shape, boundary);
        return geometry;
    }

    @Test
    public void testOverlay() throws Exception {
        // Render the frame.
        BufferedImage result = map.render(systemState);

        File file = new File(outputPath + getFileName());
        System.out.println(file.getAbsolutePath());
        ImageIO.write(result, "png", file);

//        assertBinaryFilesEqual("glyphs/" + getFileName(), getFileName());
        FileAssertions.assertOutputMatchesFixture("glyphs/" + getFileName(), getFileName(), false);
    }


    protected abstract String getFileName();
}

