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

package io.visual.map;

import control.identifiers.Coordinate;
import geometry.Geometry;
import geometry.boundaries.Arena;
import geometry.boundaries.Boundary;
import geometry.lattice.CubicLattice;
import geometry.lattice.Lattice;
import geometry.lattice.RectangularLattice;
import geometry.lattice.TriangularLattice;
import geometry.shape.Cuboid;
import geometry.shape.Hexagon;
import geometry.shape.Rectangle;
import geometry.shape.Shape;
import io.deserialize.MockCoordinateDeindexer;
import io.visual.VisualizationProperties;
import io.visual.color.ColorManager;
import io.visual.color.DefaultColorManager;
import io.visual.glyph.Glyph;
import io.visual.glyph.GlyphTest;
import io.visual.glyph.MockGlyph;
import io.visual.highlight.HighlightManager;
import layers.LightweightSystemState;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by dbborens on 4/1/14.
 */
public class MapVisualizationTest extends GlyphTest {
    @Override
    protected Glyph makeGlyph() {
        return new MockGlyph();
    }

    @Override
    protected String getFileName() {
        return "mapVisualizationTest.png";
    }

    @Override
    protected void populateStateAndHealth(Geometry geom, LightweightSystemState systemState) {
        int n = geom.getCanonicalSites().length;
        double[] health = new double[n];
        int[] state = new int[n];

        for (int i = 0; i < n; i++) {
            health[i] = (i % 2) + 1;
            state[i] = ((i + 1) % 2) + 1;
        }
        systemState.initCellLayer(state, health);
    }

    // Regression test for issues with incorrect bounds for hexagonal
    // geometries. (Really a graphical test for HexPixelTranslator.)
    public void testHexagon() throws Exception {
        for (int r = 6; r <= 24; r += 6) {
            Lattice lattice = new TriangularLattice();
            Shape shape = new Hexagon(lattice, 10);
            Boundary boundary = new Arena(shape, lattice);
            Geometry geom = new Geometry(lattice, shape, boundary);
            ColorManager colorManager = new DefaultColorManager();
            VisualizationProperties mapState = new VisualizationProperties(colorManager, r, 1);
            HighlightManager highlightManager = new HighlightManager();
            mapState.setHighlightManager(highlightManager);
            MapVisualization map = new MapVisualization(mapState);
            map.init(geom, null, null);
            systemState = makeSystemState(geom);
            BufferedImage result = map.render(systemState);
            String fn = "HexagonalMap" + r + ".png";
            File file = new File(outputPath + fn);
            ImageIO.write(result, "png", file);

            assertBinaryFilesEqual("glyphs/" + fn, fn);
        }
    }

    public void testHexagonNoOutline() throws Exception {
        for (int r = 6; r <= 24; r += 6) {
            Lattice lattice = new TriangularLattice();
            Shape shape = new Hexagon(lattice, 10);
            Boundary boundary = new Arena(shape, lattice);
            Geometry geom = new Geometry(lattice, shape, boundary);
            ColorManager colorManager = new DefaultColorManager();
            VisualizationProperties mapState = new VisualizationProperties(colorManager, r, 0);
            HighlightManager highlightManager = new HighlightManager();
            mapState.setHighlightManager(highlightManager);
            MapVisualization map = new MapVisualization(mapState);
            map.init(geom, null, null);
            systemState = makeSystemState(geom);
            BufferedImage result = map.render(systemState);
            String fn = "HexagonalMapNoOutline" + r + ".png";
            File file = new File(outputPath + fn);
            ImageIO.write(result, "png", file);
            assertBinaryFilesEqual("glyphs/" + fn, fn);
        }
    }

    // As above, but for rectangular geometry.
    public void testRectangle() throws Exception {
        doRectangleTest(1, "RectangularMap.png");
    }

    public void testNoOutline() throws Exception {
        doRectangleTest(0, "RectangularMapNoOutline.png");
    }

    private void doRectangleTest(int outline, String filename) throws Exception {
        Lattice lattice = new RectangularLattice();
        Shape shape = new Rectangle(lattice, 5, 5);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        ColorManager colorManager = new DefaultColorManager();
        VisualizationProperties mapState = new VisualizationProperties(colorManager, 25, outline);
        HighlightManager highlightManager = new HighlightManager();
        mapState.setHighlightManager(highlightManager);
        MapVisualization map = new MapVisualization(mapState);
        map.init(geom, null, null);
        systemState = makeSystemState(geom);
        BufferedImage result = map.render(systemState);
        File file = new File(outputPath + filename);
        ImageIO.write(result, "png", file);

        assertBinaryFilesEqual("glyphs/" + filename, filename);

    }

    /**
     * The cube visualization shows only the middle slice. So we populate
     * every slice but the middle slice with a checkerboard of red and blue,
     * and the middle slice with only yellow. We expect an image containing
     * only yellow tiles.
     *
     * @throws Exception
     */
    public void testCube() throws Exception {
        Lattice lattice = new CubicLattice();
        Shape shape = new Cuboid(lattice, 5, 5, 5);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        ColorManager colorManager = new DefaultColorManager();
        VisualizationProperties mapState = new VisualizationProperties(colorManager, 25, 1);
        HighlightManager highlightManager = new HighlightManager();
        mapState.setHighlightManager(highlightManager);
        MapVisualization map = new MapVisualization(mapState);
        map.init(geom, null, null);
        systemState = makeSystemState(geom);
        remakeStatesForCube(geom);
        BufferedImage result = map.render(systemState);
        File file = new File(outputPath + "cube.png");
        ImageIO.write(result, "png", file);

        assertBinaryFilesEqual("glyphs/cube.png", "cube.png");
    }

    private void remakeStatesForCube(Geometry geom) {
        MockCoordinateDeindexer deindexer = new MockCoordinateDeindexer();
        deindexer.setUnderlying(geom.getCanonicalSites());
        int n = geom.getCanonicalSites().length;
        double[] health = new double[n];
        int[] state = new int[n];
        for (int i = 0; i < n; i++) {
            health[i] = (i % 2) + 1;
            Coordinate c = deindexer.getCoordinate(i);
            if (c.z() == 2) {
                state[i] = 3;
            } else {
                state[i] = ((i + 1) % 2) + 1;
            }
        }
        ((LightweightSystemState) systemState).initCellLayer(state, health);
    }
}
