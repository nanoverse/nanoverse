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

package io.visual.kymograph;

import control.identifiers.Coordinate;
import io.visual.VisualizationProperties;
import test.EslimeTestCase;

public class KymoPixelTranslatorTest extends EslimeTestCase {
    private KymoPixelTranslator query;
    private Coordinate c0, c1;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        c0 = new Coordinate(0, 0, 0);
        c1 = new Coordinate(0, 1, 0);
        Coordinate[] cc = new Coordinate[]{c0, c1};
        VisualizationProperties properties = new VisualizationProperties(null, 10, 1);
        properties.setCoordinates(cc);
        properties.setFrames(new int[]{0, 1});
        query = new KymoPixelTranslator();
        query.init(properties);
    }

    public void testOrigin() throws Exception {
        Coordinate expected = new Coordinate(5, 15, 0);
        Coordinate actual = query.resolve(c0, 0, 0.0);
        assertEquals(expected, actual);
    }

    public void testIndexToPixels() throws Exception {
        Coordinate actual = query.resolve(c0, 1, 0.0);
        Coordinate expected = new Coordinate(15, 15, 0);
        assertEquals(expected, actual);

        actual = query.resolve(c1, 0, 0.0);
        expected = new Coordinate(5, 5, 0);
        assertEquals(expected, actual);
    }


    public void testGetImageDims() throws Exception {
        Coordinate actual = query.getImageDims();
        Coordinate expected = new Coordinate(20, 20, 0);
        assertEquals(expected, actual);
    }

    public void testGetDiagonal() throws Exception {
        double expected = Math.sqrt(2.0) * 10.0;
        double actual = query.getDiagonal();
        assertEquals(expected, actual, epsilon);
    }

}