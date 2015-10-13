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

import nanoverse.runtime.cells.MockAgent;
import nanoverse.runtime.control.halt.*;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.MockGeometry;
import nanoverse.runtime.layers.MockLayerManager;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.processes.*;
import nanoverse.runtime.processes.discrete.check.CheckForFixation;
import nanoverse.runtime.processes.gillespie.GillespieState;
import org.junit.Test;
import test.LegacyTest;

import static org.junit.Assert.*;

/**
 * Fixation is defined as only one cell type existing in the system. The
 * CheckForFixation process should throw a HaltCondition if and only if this
 * definition is met.
 * <p>
 * Created by dbborens on 1/13/14.
 */
public class CheckForFixationTest extends LegacyTest {
    private MockGeometry geometry;
    private AgentLayer layer;
    private MockLayerManager layerManager;
    private CheckForFixation query;

    @Test
    public void testTargetSimple() throws Exception {
        makeTwoCanonicalSites();
        query.target(null);
    }

    private void makeTwoCanonicalSites() {
        Coordinate[] cc = new Coordinate2D[]{
            new Coordinate2D(0, 0, 1),
            new Coordinate2D(1, 0, 1)
        };
        setup(cc);
    }

    private void setup(Coordinate[] cc) {
        geometry = new MockGeometry();
        geometry.setCanonicalSites(cc);

        layer = new AgentLayer(geometry);
        layerManager = new MockLayerManager();
        layerManager.setAgentLayer(layer);
        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, null);
        AgentProcessArguments cpArguments = makeAgentProcessArguments(geometry);
        query = new CheckForFixation(arguments, cpArguments);
    }

    @Test
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

    // There's only one site -- automatically fixed once filled
    @Test
    public void testFixationCaseSingle() throws Exception {
        makeTwoCanonicalSites();
        Coordinate coord = new Coordinate2D(0, 0, 1);
        MockAgent cell = new MockAgent();
        cell.setState(1);
        layer.getUpdateManager().place(cell, coord);
        doTest(true);
    }

    private void doTest(boolean expectFixation) {
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

    // There are two sites, but they're both the same type
    @Test
    public void testFixationCaseMulti() throws Exception {
        makeTwoCanonicalSites();
        for (int i = 0; i < 2; i++) {
            Coordinate coord = new Coordinate2D(i, 0, 0);
            MockAgent cell = new MockAgent();
            cell.setState(1);
            layer.getUpdateManager().place(cell, coord);
        }
        doTest(true);
    }

    // The lattice is full, but there are at least two
    // kinds of nanoverse.runtime.cells -- should not result in a thrown HaltCondition
    @Test
    public void testFullNonFixationCase() throws Exception {
        setUpMixedCase();
        doTest(false);
    }

    private void setUpMixedCase() throws Exception {
        makeTwoCanonicalSites();
        for (int i = 0; i < 2; i++) {
            Coordinate coord = new Coordinate2D(i, 0, 0);
            MockAgent cell = new MockAgent();
            // state 0 is reserved for death / nullity
            cell.setState(i + 1);
            layer.getUpdateManager().place(cell, coord);
        }
    }

    // There's only one species, but there's still room
    // to grow -- should still be considered "fixation"
    @Test
    public void testOpenSpaceCase() throws Exception {
        makeTwoCanonicalSites();
        Coordinate coord = new Coordinate2D(0, 0, 0);
        MockAgent cell = new MockAgent();
        cell.setState(1);
        layer.getUpdateManager().place(cell, coord);
        doTest(true);
    }

    /**
     * Make sure that, after the extinction of all but one type of site, a
     * fixation event is triggered.
     */
    @Test
    public void testTwoToOneStateRegression() throws Exception {
        // This test should start with two nanoverse.runtime.cells, each of a different type.
        setUpMixedCase();

        // We don't expect a fixation state exception.
        doTest(false);

        // Remove one of the nanoverse.runtime.cells. Now there's only one cell type in the system.
        layer.getUpdateManager().banish(new Coordinate2D(0, 0, 0));

        // The state should now reflect fixation.
        doTest(true);
    }

}
