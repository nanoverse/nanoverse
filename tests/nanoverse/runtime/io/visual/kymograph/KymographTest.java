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

package nanoverse.runtime.io.visual.kymograph;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;
import nanoverse.runtime.io.deserialize.MockCoordinateDeindexer;
import nanoverse.runtime.io.visual.VisualizationProperties;
import nanoverse.runtime.io.visual.color.*;
import nanoverse.runtime.io.visual.highlight.HighlightManager;
import nanoverse.runtime.layers.LightweightSystemState;
import org.junit.Test;
import test.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

import static org.junit.Assert.fail;

public class KymographTest extends LegacyTest {

    @Test
    public void testWithOutline() throws Exception {
        fail("Rewrite as modern test");
//        doTest(1, "KymographOutline.png");
    }

    private void doTest(int outline, String filename) throws Exception {
        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, 5);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        ColorManager colorManager = new DefaultColorManager();
        VisualizationProperties mapState = new VisualizationProperties(colorManager, 25, outline);
        HighlightManager highlightManager = new HighlightManager();
        mapState.setHighlightManager(highlightManager);
        Kymograph map = new Kymograph(mapState);

        double[] times = new double[]{0.0};
        int[] frames = new int[]{0};
        map.init(geom, times, frames);
        LightweightSystemState systemState = makeSystemState(geom);

        BufferedImage result = map.render(systemState);
        File file = new File(outputPath + filename);
        ImageIO.write(result, "png", file);

        FileAssertions.assertOutputMatchesFixture("glyphs/" + filename, filename, false);
//        assertBinaryFilesEqual("glyphs/" + filename, filename);

    }

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
//        int n = geom.getCanonicalSites().length;
//        int[] state = new int[n];
//
//        for (int i = 0; i < n; i++) {
//            state[i] = i % 3;
//        }
//        systemState.initAgentLayer(state);

    }

    @Test
    public void testNoOutline() throws Exception {
        fail("Rewrite as modern test");
//        doTest(0, "KymographNoOutline.png");
    }
}