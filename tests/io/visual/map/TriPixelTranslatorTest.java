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
import io.visual.VisualizationProperties;
import test.EslimeTestCase;

/**
 * Created by dbborens on 4/1/14.
 */
public class TriPixelTranslatorTest extends EslimeTestCase {

    private TriPixelTranslator query;
    private Coordinate c0, c1;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        c0 = new Coordinate(0, 0, 0);
        c1 = new Coordinate(1, 0, 0);

        Coordinate[] cc = new Coordinate[]{c0, c1};
        VisualizationProperties mapState = new VisualizationProperties(null, 10, 1);
        mapState.setCoordinates(cc);
        query = new TriPixelTranslator();
        query.init(mapState);
    }

    public void testOrigin() throws Exception {
        Coordinate expected = new Coordinate(10, 9, 0);
        Coordinate actual = query.indexToPixels(c0);
        assertEquals(expected, actual);
    }

    public void testIndexToPixels() throws Exception {
        Coordinate actual = query.indexToPixels(c1);
        Coordinate expected = new Coordinate(25, 18, 0);
        assertEquals(expected, actual);
    }


    public void testGetImageDims() throws Exception {
        Coordinate actual = query.getImageDims();
        Coordinate expected = new Coordinate(36, 27, 0);
        assertEquals(expected, actual);
    }

    public void testGetDiagonal() throws Exception {
        double expected = 20.0;
        double actual = query.getDiagonal();
        assertEquals(expected, actual, epsilon);
    }
}
