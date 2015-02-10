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

package processes.discrete;

import cells.MockCell;
import control.halt.FixationEvent;
import control.halt.HaltCondition;
import control.identifiers.Coordinate;
import geometry.MockGeometry;
import layers.MockLayerManager;
import layers.cell.CellLayer;
import processes.BaseProcessArguments;
import processes.MockStepState;
import processes.discrete.check.CheckForFixation;
import processes.gillespie.GillespieState;
import test.EslimeTestCase;

/**
 * Fixation is defined as only one cell type existing in the system. The
 * CheckForFixation process should throw a HaltCondition if and only if this
 * definition is met.
 * <p>
 * Created by dbborens on 1/13/14.
 */
public class CheckForFixationTest extends EslimeTestCase {
    private MockGeometry geometry;
    private CellLayer layer;
    private MockLayerManager layerManager;
    private CheckForFixation query;

    public void testTargetSimple() throws Exception {
        makeTwoCanonicalSites();
        query.target(null);
    }

    public void testTargetGillespie() throws Exception {
        makeTwoCanonicalSites();
        Integer id = query.getID();
        Integer[] ids = new Integer[]{id};
        GillespieState gs = new GillespieState(ids);
        query.target(gs);
        gs.close();
        assertEquals(0.0, gs.getTotalWeight(), epsilon);
        assertEquals(1, gs.getTotalCount());
        assertEquals(1, gs.getEventCount(id));
    }

    public void doTest(boolean expectFixation) {
        boolean fixed = false;
        try {
            MockStepState state = new MockStepState();
            query.fire(state);

            // Fixation events halt flow in the simulation.
        } catch (FixationEvent event) {
            fixed = true;

            // Nothing else is supposed to be thrown!
        } catch (HaltCondition condition) {
            fail("Unexpected halt condition " + condition.getClass().getSimpleName());
        }

        assertEquals(expectFixation, fixed);
    }

//    public void testExtinctionCase() {
//        makeTwoCanonicalSites();
//        boolean extinct = false;
//
//        try {
//            StepState state = new StepState();
//            query.fire(state);
//        } catch (ExtinctionEvent event) {
//            extinct = true;
//
//            // Nothing else is supposed to be thrown!
//        } catch (HaltCondition condition) {
//            fail("Unexpected halt condition " + condition.getClass().getSimpleName());
//        }
//
//        assertTrue(extinct);
//    }

    // There's only one site -- automatically fixed once filled
    public void testFixationCaseSingle() throws Exception {
        makeTwoCanonicalSites();
        Coordinate coord = new Coordinate(0, 0, 1);
        MockCell cell = new MockCell();
        cell.setState(1);
        layer.getUpdateManager().place(cell, coord);
        doTest(true);
    }

    // There are two sites, but they're both the same type
    public void testFixationCaseMulti() throws Exception {
        makeTwoCanonicalSites();
        for (int i = 0; i < 2; i++) {
            Coordinate coord = new Coordinate(i, 0, 0);
            MockCell cell = new MockCell();
            cell.setState(1);
            layer.getUpdateManager().place(cell, coord);
        }
        doTest(true);
    }

    // The lattice is full, but there are at least two
    // kinds of cells -- should not result in a thrown HaltCondition
    public void testFullNonFixationCase() throws Exception {
        setUpMixedCase();
        doTest(false);
    }

    private void setUpMixedCase() throws Exception {
        makeTwoCanonicalSites();
        for (int i = 0; i < 2; i++) {
            Coordinate coord = new Coordinate(i, 0, 0);
            MockCell cell = new MockCell();
            // state 0 is reserved for death / nullity
            cell.setState(i + 1);
            layer.getUpdateManager().place(cell, coord);
        }
    }

    // There's only one species, but there's still room
    // to grow -- should still be considered "fixation"
    public void testOpenSpaceCase() throws Exception {
        makeTwoCanonicalSites();
        Coordinate coord = new Coordinate(0, 0, 0);
        MockCell cell = new MockCell();
        cell.setState(1);
        layer.getUpdateManager().place(cell, coord);
        doTest(true);
    }

    /**
     * Make sure that, after the extinction of all but one type of site, a
     * fixation event is triggered.
     */
    public void testTwoToOneStateRegression() throws Exception {
        // This test should start with two cells, each of a different type.
        setUpMixedCase();

        // We don't expect a fixation state exception.
        doTest(false);

        // Remove one of the cells. Now there's only one cell type in the system.
        layer.getUpdateManager().banish(new Coordinate(0, 0, 0));

        // The state should now reflect fixation.
        doTest(true);
    }

    private void makeTwoCanonicalSites() {
        Coordinate[] cc = new Coordinate[]{
                new Coordinate(0, 0, 1),
                new Coordinate(1, 0, 1)
        };
        setup(cc);
    }

    private void setup(Coordinate[] cc) {
        geometry = new MockGeometry();
        geometry.setCanonicalSites(cc);

        layer = new CellLayer(geometry);
        layerManager = new MockLayerManager();
        layerManager.setCellLayer(layer);
        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, null);
        CellProcessArguments cpArguments = makeCellProcessArguments(geometry);
        query = new CheckForFixation(arguments, cpArguments);
    }

}
