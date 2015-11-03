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

package nanoverse.runtime.io.visual.map;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;
import nanoverse.runtime.io.deserialize.MockCoordinateDeindexer;
import nanoverse.runtime.io.visual.VisualizationProperties;
import nanoverse.runtime.io.visual.color.*;
import nanoverse.runtime.io.visual.glyph.*;
import nanoverse.runtime.io.visual.highlight.*;
import nanoverse.runtime.layers.LightweightSystemState;
import org.junit.Test;
import test.FileAssertions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.fail;

/**
 * Created by dbborens on 4/1/14.
 */
public class MapVisualizationTest extends GlyphTest {
    @Override
    protected Glyph makeGlyph() {
        return new MockGlyph();
    }

    @Override
    protected void populateStateAndHealth(Geometry geom, LightweightSystemState systemState) {
//        fail("Rewrite me");
        int n = geom.getCanonicalSites().length;
        Stream<String> nameStream = IntStream.range(0, n)
                .map(this::alternate)
                .mapToObj(String::valueOf);

        systemState.setAgentNames(nameStream);
    }

    private Integer alternate(int i) {
        return ((i + 1) % 2) + 1;
    }

    @Override
    protected String getFileName() {
        return "mapVisualizationTest.png";
    }

    // Regression test for issues with incorrect bounds for hexagonal
    // geometries. (Really a graphical test for HexPixelTranslator.)
    @Test
    public void testHexagon() throws Exception {
        for (int r = 6; r <= 24; r += 6) {
            Lattice lattice = new TriangularLattice();
            Shape shape = new Hexagon(lattice, 10);
            Boundary boundary = new Arena(shape, lattice);
            Geometry geom = new Geometry(lattice, shape, boundary);
            ColorManager colorManager = new IndexedColorModel();
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

            FileAssertions.assertOutputMatchesFixture("glyphs/" + fn, fn, false);
//            assertBinaryFilesEqual("glyphs/" + fn, fn);
        }
    }

    @Test
    public void testHexagonNoOutline() throws Exception {
        for (int r = 6; r <= 24; r += 6) {
            Lattice lattice = new TriangularLattice();
            Shape shape = new Hexagon(lattice, 10);
            Boundary boundary = new Arena(shape, lattice);
            Geometry geom = new Geometry(lattice, shape, boundary);
            ColorManager colorManager = new IndexedColorModel();
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
            FileAssertions.assertOutputMatchesFixture("glyphs/" + fn, fn, false);
        }
    }

    // As above, but for rectangular nanoverse.runtime.geometry.
    @Test
    public void testRectangle() throws Exception {
        doRectangleTest(1, "RectangularMap.png");
    }

    private void doRectangleTest(int outline, String filename) throws Exception {
        Lattice lattice = new RectangularLattice();
        Shape shape = new Rectangle(lattice, 5, 5);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        ColorManager colorManager = new IndexedColorModel();
        VisualizationProperties mapState = new VisualizationProperties(colorManager, 25, outline);
        HighlightManager highlightManager = new HighlightManager();
        mapState.setHighlightManager(highlightManager);
        MapVisualization map = new MapVisualization(mapState);
        map.init(geom, null, null);
        systemState = makeSystemState(geom);
        BufferedImage result = map.render(systemState);
        File file = new File(outputPath + filename);
        ImageIO.write(result, "png", file);
        FileAssertions.assertOutputMatchesFixture("glyphs/" + filename, filename, false);

    }

    @Test
    public void testNoOutline() throws Exception {
        doRectangleTest(0, "RectangularMapNoOutline.png");
    }

    /**
     * The cube visualization shows only the middle slice. So we populate
     * every slice but the middle slice with a checkerboard of red and blue,
     * and the middle slice with only yellow. We expect an image containing
     * only yellow tiles.
     *
     * @throws Exception
     */
    @Test
    public void testCube() throws Exception {
        Lattice lattice = new CubicLattice();
        Shape shape = new Cuboid(lattice, 5, 5, 5);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        ColorManager colorManager = new IndexedColorModel();
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
        FileAssertions.assertOutputMatchesFixture("glyphs/cube.png", "cube.png", false);
    }

    private void remakeStatesForCube(Geometry geom) {
//        fail("rewrite me");
        MockCoordinateDeindexer deindexer = new MockCoordinateDeindexer();
        deindexer.setUnderlying(geom.getCanonicalSites());
        int n = geom.getCanonicalSites().length;

        Stream<String> nameStream = IntStream.range(0, n).map(i -> {
            Coordinate c = deindexer.getCoordinate(i);
            if (c.z() == 2) {
                return 3;
            } else {
                return alternate(i);
            }
        }).mapToObj(String::valueOf);

        ((LightweightSystemState) systemState).setAgentNames(nameStream);
    }
}
