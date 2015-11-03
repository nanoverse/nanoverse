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

package nanoverse.runtime.io.deserialize;

import nanoverse.runtime.control.identifiers.*;
import org.junit.Test;
import test.LegacyTest;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by dbborens on 12/11/13.
 */
public class ExtremaReaderTest extends LegacyTest {
    /**
     * Read from a fixture file containing a single extreme value pair.
     *
     * @throws Exception
     */
    @Test
    public void testReadSingleton() throws Exception {
        String filename = fixturePath + "solute42.metadata.txt";

        File singletonFixture = new File(filename);
        ExtremaReader reader = new ExtremaReader(singletonFixture);

        Extrema actual = reader.get("extrema");

        Extrema expected = new Extrema();
        expected.consider(0, new Coordinate3D(0, 0, 0, 0), 2.0);
        expected.consider(5.0, new Coordinate3D(1, 0, 0, 0), 1.0);
        assertEquals(expected, actual);
    }

    /**
     * Read from a fixture file containing multiple extreme value pairs.
     *
     * @throws Exception
     */
    @Test
    public void testReadMulti() throws Exception {
        Extrema a, c, actual;

        // Set up expected values
        a = new Extrema();
        a.consider(0.0, new Coordinate3D(0, 0, 0, 0), 2.0);
        a.consider(7.0, new Coordinate3D(1, 0, 0, 0), 1.0);

        c = new Extrema();
        c.consider(2.0, new Coordinate3D(0, 2, 0, 0), 2.0);
        c.consider(9.0, new Coordinate3D(1, 0, 0, 0), 1.0);

        // Read file
        String filename = fixturePath + "multi.metadata.txt";

        File singletonFixture = new File(filename);
        ExtremaReader reader = new ExtremaReader(singletonFixture);

        // Check "a"
        actual = reader.get("a");
        assertEquals(a, actual);

        // Check "c"
        actual = reader.get("c");
        assertEquals(c, actual);
    }
}
