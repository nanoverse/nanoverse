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

package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.cells.MockAgent;
import nanoverse.runtime.control.arguments.ConstantDouble;
import nanoverse.runtime.control.halt.*;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.MockGeometry;
import nanoverse.runtime.layers.MockLayerManager;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.processes.*;
import nanoverse.runtime.processes.discrete.check.CheckForExtinction;
import nanoverse.runtime.structural.MockGeneralParameters;
import org.junit.Test;
import test.LegacyTest;

import static org.junit.Assert.*;

/**
 * Created by dbborens on 3/5/14.
 */
public class CheckForExtinctionTest extends LegacyTest {
    private MockGeometry geometry;
    private AgentLayer layer;
    private MockLayerManager layerManager;
    private CheckForExtinction query;
    private MockGeneralParameters p;

    @Test
    public void testExtinctionCase() {
        makeOneCanonicalSite();
        doTest(true);
    }

    private void doTest(boolean expected) {
        boolean actual = false;

        try {
            MockStepState state = new MockStepState();
            query.fire(state);
        } catch (ExtinctionEvent event) {
            actual = true;

            // Nothing else is supposed to be thrown!
        } catch (HaltCondition condition) {
            fail("Unexpected halt condition " + condition.getClass().getSimpleName());
        }

        assertEquals(expected, actual);

    }

    private void makeOneCanonicalSite() {
        Coordinate[] cc = new Coordinate[]{
            new Coordinate2D(0, 0, 1)
        };
        setup(cc);
    }

    private void setup(Coordinate[] cc) {
        p = makeMockGeneralParameters();
        geometry = new MockGeometry();
        geometry.setCanonicalSites(cc);

        layer = new AgentLayer(geometry);
        layerManager = new MockLayerManager();
        layerManager.setAgentLayer(layer);
        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, p);
        AgentProcessArguments cpArguments = makeAgentProcessArguments(geometry);
        query = new CheckForExtinction(arguments, cpArguments, new ConstantDouble(0.0));
        query.init();
    }

    @Test
    public void testNonExtinctionCase() throws Exception {
        populateSingletonCase();
        doTest(false);
    }

    private Coordinate populateSingletonCase() throws Exception {
        makeOneCanonicalSite();
        Coordinate coord = new Coordinate2D(0, 0, 1);
        MockAgent cell = new MockAgent();
        cell.setName("1");
        layer.getUpdateManager().place(cell, coord);
        return coord;
    }

    /**
     * Make sure that, if there are occupied sites and they subsequently all
     * become vacant, this is still recorded as an exitnction event.
     */
    @Test
    public void testTransitionToExtinction() throws Exception {
        Coordinate coord = populateSingletonCase();
        doTest(false);
        layer.getUpdateManager().banish(coord);
        doTest(true);
    }
}
