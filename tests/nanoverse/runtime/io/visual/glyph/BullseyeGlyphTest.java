/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package nanoverse.runtime.io.visual.glyph;

import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.Rectangle;
import nanoverse.runtime.geometry.shape.Shape;
import nanoverse.runtime.io.visual.VisualizationProperties;
import nanoverse.runtime.io.visual.color.*;
import nanoverse.runtime.io.visual.color.palettes.*;
import nanoverse.runtime.io.visual.highlight.*;
import nanoverse.runtime.io.visual.map.MapVisualization;
import nanoverse.runtime.layers.*;
import org.junit.Before;
import test.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by dbborens on 4/4/14.
 */
public class BullseyeGlyphTest extends LegacyTest {

    Geometry geometry;
    private HighlightManager highlightManager;
    private MapVisualization map;
    private SystemState systemState;

    @Before
    public void setUp() throws Exception {
        geometry = makeGeometry();

        // Create 10x10 triangular lattice.
        Palette palette = new RainbowColorPalette<>(Color.BLACK, Color.DARK_GRAY);
        ColorManager colorManager = new IndexedColorModel(palette);

        // Create a 10 x 10 hexagonal map.
        VisualizationProperties mapState = new VisualizationProperties(colorManager, 50, 1);

        // Create highlight manager.
        highlightManager = new HighlightManager();
        mapState.setHighlightManager(highlightManager);

        // Channel 0 has a small glyph.
        Color offRed = Color.decode("0xFF2020");
        Glyph small = new BullseyeGlyph(offRed, Color.WHITE, 0.3);
        highlightManager.setGlyph(0, small);

        // Create map visualization.
        map = new MapVisualization(mapState);
        map.init(geometry, null, null);

        // Create system name
        systemState = makeSystemState();

    }

    private LightweightSystemState makeSystemState() {
//        MockCoordinateDeindexer deindexer = new MockCoordinateDeindexer();
//        deindexer.setUnderlying(geometry.getCanonicalSites());
//
//        int n = makeGeometry().getCanonicalSites().length;
//
//        String[] state = new String[n];
//
//        for (int i = 0; i < n; i++) {
//            state[i] = "test";
//        }
//
//        LightweightSystemState ret = new LightweightSystemState(geometry);
//        ret.setAgentNames(state);
//
//        Set<Coordinate> highlights = new HashSet<>();
//        for (Coordinate c : geometry.getCanonicalSites()) {
//            highlights.add(c);
//        }
//        ret.setHighlights(0, highlights);
//        ret.setTime(0.0);
//        ret.setFrame(0);
//
//        return ret;
        return null;
    }

    private Geometry makeGeometry() {
        Lattice lattice = new TriangularLattice();
        Shape shape = new Rectangle(lattice, 10, 10);
        Boundary boundary = new Absorbing(shape, lattice);
        Geometry geometry = new Geometry(lattice, shape, boundary);
        return geometry;
    }

    @Before
    public void testOverlay() throws Exception {
        // Render the frame.
        BufferedImage result = map.render(systemState);

        File file = new File(outputPath + "bullseyeGlyph.png");
        ImageIO.write(result, "png", file);

        FileAssertions.assertOutputMatchesFixture("glyphs/bullseyeGlyph.png", "bullseyeGlyph.png", false);
    }
}
