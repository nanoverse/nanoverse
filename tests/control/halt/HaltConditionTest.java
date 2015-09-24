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

package control.halt;

import org.junit.*;
import test.EslimeTestCase;

import static org.junit.Assert.assertEquals;

public class HaltConditionTest extends EslimeTestCase {
    private HaltCondition query;

    @Before
    public void setUp() throws Exception {
        query = new HaltCondition();
    }

    @Test
    public void testGillespie() {
        query.setGillespie(1.0);
        assertEquals(1.0, query.getGillespie(), epsilon);
    }

    @Test
    public void testToString() {
        assertEquals("HaltCondition", query.toString());
    }
}