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

package processes;

import org.junit.Test;
import processes.gillespie.*;
import test.EslimeTestCase;

import static org.junit.Assert.assertEquals;
public class GillespieTest extends EslimeTestCase {

    /**
     * Test the GillespieState object.
     */
    @Test
    public void testGillespieStateLifeCycle() {
        Integer[] arr = new Integer[]{100, 200};
        GillespieState gs = new GillespieState(arr);

        // Setters should work right now
        gs.add(100, 5, 0.7);
        gs.add(200, 3, 0.4);

        // Close it so we can use getters
        gs.close();

        assertEquals(gs.getTotalWeight(), 1.1, epsilon);
        assertEquals(2, gs.getKeys().length);
        assertEquals(5, gs.getEventCount(100));
        assertEquals(3, gs.getEventCount(200));
        assertEquals(0.7, gs.getWeight(100), epsilon);
        assertEquals(0.4, gs.getWeight(200), epsilon);
    }


    /**
     * Test that the weighted target selector works.
     */
    @Test
    public void testSelectTarget() {
        Integer[] arr = new Integer[]{100, 200, 300};
        GillespieState gs = new GillespieState(arr);

        gs.add(100, 5, 2.0);
        gs.add(200, 3, 1.0);
        gs.add(300, 3, 7.0);

        gs.close();
        GillespieChooser gc = new GillespieChooser(gs);
        assertEquals(100, (int) gc.selectTarget(0.0));
        assertEquals(100, (int) gc.selectTarget(0.5));
        assertEquals(200, (int) gc.selectTarget(2.0));
        assertEquals(200, (int) gc.selectTarget(2.5));
        assertEquals(300, (int) gc.selectTarget(3.0));
        assertEquals(300, (int) gc.selectTarget(5.0));
        assertEquals(300, (int) gc.selectTarget(7.0));
        assertEquals(300, (int) gc.selectTarget(9.9));
    }


}
