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

package io.factory;

import geometry.MockGeometry;
import io.visual.map.*;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.assertEquals;

/**
 * Created by dbborens on 4/3/14.
 */
public class PixelTranslatorFactoryTest extends LegacyTest {
    private MockGeometry geom;

    @Before
    public void setUp() throws Exception {
        geom = new MockGeometry();
    }

    @Test
    public void testTriangularCase() {
        geom.setDimensionality(2);
        geom.setConnectivity(3);

        PixelTranslator actual = PixelTranslatorFactory.instantiate(geom);
        PixelTranslator expected = new TriPixelTranslator();

        assertEquals(expected, actual);
    }

}
