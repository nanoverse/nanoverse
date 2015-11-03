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

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.io.visual.VisualizationProperties;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.assertEquals;

public class RectPixelTranslatorTest extends LegacyTest {
    private RectPixelTranslator query;
    private Coordinate c0, c1;

    @Before
    public void setUp() throws Exception {
        c0 = new Coordinate2D(0, 0, 0);
        c1 = new Coordinate2D(1, 0, 0);

        Coordinate[] cc = new Coordinate[]{c0, c1};
        VisualizationProperties mapState = new VisualizationProperties(null, 10, 1);
        mapState.setCoordinates(cc);
        query = new RectPixelTranslator();
        query.init(mapState);
    }

    @Test
    public void testOrigin() throws Exception {
        Coordinate expected = new Coordinate2D(5, 5, 0);
        Coordinate actual = query.indexToPixels(c0);
        assertEquals(expected, actual);
    }

    @Test
    public void testIndexToPixels() throws Exception {
        Coordinate actual = query.indexToPixels(c1);
        Coordinate expected = new Coordinate2D(15, 5, 0);
        assertEquals(expected, actual);
    }


    @Test
    public void testGetImageDims() throws Exception {
        Coordinate actual = query.getImageDims();
        Coordinate expected = new Coordinate2D(20, 10, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetDiagonal() throws Exception {
        double expected = Math.sqrt(2.0) * 10.0;
        double actual = query.getDiagonal();
        assertEquals(expected, actual, epsilon);
    }

}