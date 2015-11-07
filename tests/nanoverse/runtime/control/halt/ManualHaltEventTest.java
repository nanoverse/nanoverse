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

package nanoverse.runtime.control.halt;

import org.junit.Test;
import test.LegacyTest;

import static org.junit.Assert.assertEquals;

public class ManualHaltEventTest extends LegacyTest {

    @Test
    public void testLifeCycle() throws Exception {
        ManualHaltEvent query = new ManualHaltEvent("test123");
        query.setGillespie(1.0);
        assertEquals("test123", query.toString());
        assertEquals(1.0, query.getGillespie(), epsilon);
    }
}