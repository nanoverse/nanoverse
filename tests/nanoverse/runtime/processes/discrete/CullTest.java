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

package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.cells.MockCell;
import nanoverse.runtime.processes.*;
import org.junit.*;
import test.LegacyLatticeTest;

import static org.junit.Assert.*;

/**
 * Created by dbborens on 3/5/14.
 */
public class CullTest extends LegacyLatticeTest {

    private final double THRESHOLD = 0.5;

    private Cull query;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, null);
        CellProcessArguments cpArguments = makeCellProcessArguments(geom);
        query = new Cull(arguments, cpArguments, 0.5);
    }

    /**
     * Verify that nanoverse.runtime.cells clearly above and below the threshold are handled as
     * expected.
     *
     * @throws Exception
     */
    @Test
    public void testLifeCycle() throws Exception {
        MockCell live = new MockCell();
        MockCell die = new MockCell();

        live.setHealth(THRESHOLD + 0.1);
        die.setHealth(THRESHOLD - 0.1);

        // Place a cell above the threshold.
        cellLayer.getUpdateManager().place(live, x);

        // Place a cell below the threshold.
        cellLayer.getUpdateManager().place(die, y);

        // Verify that there are two live nanoverse.runtime.cells.
        assertEquals(2, cellLayer.getViewer().getOccupiedSites().size());

        // Cull.
        query.target();
        query.fire(new MockStepState());

        // Only the one above the threshold should survive.
        assertTrue(cellLayer.getViewer().isOccupied(x));
        assertTrue(die.died());
    }

    /**
     * Cull is supposed to kill any nanoverse.runtime.cells that are less than or equl to the
     * threshold. This test verifies that nanoverse.runtime.cells at the threshold are culled.
     *
     * @throws Exception
     */
    @Test
    public void testBorderlineCase() throws Exception {
        MockCell borderline = new MockCell();
        borderline.setHealth(THRESHOLD);
        cellLayer.getUpdateManager().place(borderline, x);

        assertTrue(cellLayer.getViewer().isOccupied(x));

        // Cull.
        query.target();
        query.fire(new MockStepState());
        assertTrue(borderline.died());
    }
}
