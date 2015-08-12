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

package io.serialize;

import control.identifiers.Coordinate;
import control.identifiers.Coordinate3D;
import control.identifiers.Extrema;
import io.serialize.text.ExtremaWriter;
import test.EslimeTestCase;

import java.io.StringWriter;

/**
 * Created by dbborens on 12/11/13.
 */
public class ExtremaWriterTest extends EslimeTestCase {

    private StringWriter sw;
    private ExtremaWriter helper;

    @Override
    protected void setUp() throws Exception {
        sw = new StringWriter();
        helper = new ExtremaWriter(sw);
    }

    public void testPush() throws Exception {
        Extrema a = new Extrema();
        a.consider(0.0, new Coordinate3D(0, 0, 0, 0), 2.0);
        a.consider(7.0, new Coordinate3D(1, 0, 0, 0), 1.0);
        helper.push("a", a);

        String expected = "a>0.0@(0, 0, 0 | 0 | 2.0):7.0@(1, 0, 0 | 0 | 1.0)";
        String actual = sw.toString();

        assertEquals(expected, actual);
    }
}
