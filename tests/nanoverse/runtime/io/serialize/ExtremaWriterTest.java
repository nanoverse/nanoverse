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

package nanoverse.runtime.io.serialize;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.io.serialize.text.ExtremaWriter;
import org.junit.*;
import test.LegacyTest;

import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

/**
 * Created by dbborens on 12/11/13.
 */
public class ExtremaWriterTest extends LegacyTest {

    private StringWriter sw;
    private ExtremaWriter helper;

    @Before
    public void setUp() throws Exception {
        sw = new StringWriter();
        helper = new ExtremaWriter(sw);
    }

    @Test
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
