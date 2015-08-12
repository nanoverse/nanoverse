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

package cells;

import control.identifiers.Coordinate;
import control.identifiers.Coordinate2D;

import java.util.HashMap;

/**
 * Created by dbborens on 1/2/14.
 */
public class SourceCellTest extends CellTest {

    private SourceCell a, b;

    @Override
    public void setUp() throws Exception {
        HashMap<String, Double> aProduction = new HashMap<>();
        aProduction.put("alpha", 1.0);
        aProduction.put("beta", 2.0);

        HashMap<String, Double> bProduction = new HashMap<>();
        bProduction.put("beta", 0.5);

        a = new SourceCell(1, aProduction);
        b = new SourceCell(2, bProduction);
    }

    @Override
    public void testGetState() throws Exception {
        assertEquals(1, a.getState());
        assertEquals(2, b.getState());
    }

    @Override
    public void testGetHealth() throws Exception {
        assertEquals(0.0, a.getHealth(), epsilon);
        assertEquals(0.0, b.getHealth(), epsilon);
    }

    @Override
    public void testIsDivisible() throws Exception {
        // SourceCells are never divisible
        assertFalse(a.isDivisible());
        assertFalse(b.isDivisible());
    }

    @Override
    public void testFeedConsiderApply() throws Exception {
        // Feed, consider and apply should have no effect on SourceCells
        int result = a.consider();
        assertEquals(1, result);
    }

    @Override
    public void testDivide() throws Exception {
        boolean thrown = false;
        try {
            a.divide();
        } catch (Exception e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Override
    public void testClone() throws Exception {
        // There should be two identical objects in memory, not one
        Cell c = a.replicate();
        assertEquals(a, c);
        assert (a != c);
    }

    @Override
    public void testGetProduction() throws Exception {
        assertEquals(1.0, a.getProduction("alpha"));
        assertEquals(2.0, a.getProduction("beta"));
        assertEquals(0.5, b.getProduction("beta"));
    }

    public void testTriggerThrowsException() throws Exception {
        boolean thrown = false;

        try {
            a.trigger("a", new Coordinate2D(0, 0, 0));
        } catch (UnsupportedOperationException ex) {
            thrown = true;
        }
        assertTrue(thrown);
    }

}
