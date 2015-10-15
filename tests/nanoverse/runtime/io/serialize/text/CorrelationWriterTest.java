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

package nanoverse.runtime.io.serialize.text;

import nanoverse.runtime.cells.MockAgent;
import nanoverse.runtime.control.arguments.ConstantDouble;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;
import nanoverse.runtime.layers.MockLayerManager;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.structural.MockGeneralParameters;
import org.junit.*;
import test.*;

/**
 * Created by dbborens on 4/22/14.
 */
public class CorrelationWriterTest extends LegacyTest {

    private static final int SIDE = 10;
    private Geometry geom;
    private MockLayerManager layerManager;
    private AgentLayer layer;

    @Before
    public void setUp() throws Exception {

        Lattice lattice = new RectangularLattice();
        Shape shape = new Rectangle(lattice, SIDE, SIDE);
        Boundary boundary = new Arena(shape, lattice);
        geom = new Geometry(lattice, shape, boundary);

        layer = new AgentLayer(geom);
        layerManager = new MockLayerManager();
        layerManager.setAgentLayer(layer);

    }

    @Test
    public void testTrivialCase() throws Exception {
        String filename = "TrivialCorrelation.txt";
        MockGeneralParameters p = makeMockGeneralParameters();
        CorrelationWriter query = new CorrelationWriter(p, filename, new ConstantDouble(1.0), layerManager);
        query.init();
        StepState state = new StepState(2.0, 1);
        state.record(layerManager);
        query.flush(state);
        query.dispatchHalt(null);
        query.close();
        FileAssertions.assertOutputMatchesFixture(filename, true);
    }

    @Test
    public void testCheckerboardCase() throws Exception {
        String filename = "CheckerboardCorrelation.txt";
        MockGeneralParameters p = makeMockGeneralParameters();
        CorrelationWriter query = new CorrelationWriter(p, filename, new ConstantDouble(1.0), layerManager);
        loadCheckerboard();
        query.init();
        StepState state = new StepState(2.0, 1);
        state.record(layerManager);
        query.flush(state);
        query.dispatchHalt(null);
        query.close();
        FileAssertions.assertOutputMatchesFixture(filename, true);
    }

    private void loadCheckerboard() throws Exception {
        for (int x = 0; x < SIDE; x += 2) {
            for (int y = 0; y < SIDE; y += 2) {
                Coordinate c0 = new Coordinate2D(x, y, 0);
                MockAgent cell = new MockAgent(1);
                layer.getUpdateManager().place(cell, c0);


                if ((x < SIDE - 1) && (y < SIDE - 1)) {
                    cell = new MockAgent(1);
                    Coordinate c1 = new Coordinate2D(x + 1, y + 1, 0);
                    layer.getUpdateManager().place(cell, c1);
                }
            }
        }
    }

    @Test
    public void testThreeStateCase() throws Exception {
        // Note that I have not verified that the fixture for this is exactly
        // right. It's a complicated case, but r=0 and r=1 are degenerate: you
        // never have a nearest neighbor the smae name as you, so you expect
        // anticorrelation. At r=2, the dead name has correlation of 1 while
        // the other two states would have 50% probability of identity, or a
        // correlation of zero. Thus, we would expect a correlation of 0.5 for
        // all states at r=2 in an infinite nanoverse.runtime.geometry. Since this nanoverse.runtime.geometry is
        // finite, we expect some weirdness. The result we got looked plausible
        // at longer lengths and exactly right at shorter states.
        String filename = "ThreeStateCorrelation.txt";
        MockGeneralParameters p = makeMockGeneralParameters();
        CorrelationWriter query = new CorrelationWriter(p, filename, new ConstantDouble(1.0), layerManager);
        loadThreeState();
        query.init();
        StepState state = new StepState(2.0, 1);
        state.record(layerManager);
        query.flush(state);
        query.dispatchHalt(null);
        query.close();
        FileAssertions.assertOutputMatchesFixture(filename, true);
    }

    // Empty, name 1 and name 2.
    private void loadThreeState() throws Exception {
        for (int x = 0; x < SIDE; x += 2) {
            for (int y = 0; y < SIDE; y += 2) {
                Coordinate c0 = new Coordinate2D(x, y, 0);
                MockAgent cell = new MockAgent(1);
                layer.getUpdateManager().place(cell, c0);


                if ((x < SIDE - 1) && (y < SIDE - 1)) {
                    cell = new MockAgent(2);
                    Coordinate c1 = new Coordinate2D(x + 1, y + 1, 0);
                    layer.getUpdateManager().place(cell, c1);
                }
            }
        }
    }

    /**
     * Makes sure that, if two simulations are run and a sample is taken from
     * each, the correlation function reflects the results of both. In this
     * case, the first instance is trivial (correlation of 1 everywhere) and
     * the second one alternates between correlated and anticorrelated. So we
     * expect the correlation to be 1 at even distances, and 0 at odd distances.
     *
     * @throws Exception
     */
    @Test
    public void testAggregation() throws Exception {
        String filename = "AggregateCorrelation.txt";
        MockGeneralParameters p = makeMockGeneralParameters();
        CorrelationWriter query = new CorrelationWriter(p, filename, new ConstantDouble(1.0), layerManager);
        query.init();
        StepState state = new StepState(2.0, 1);
        state.record(layerManager);
        query.flush(state);
        query.dispatchHalt(null);
        loadCheckerboard();
        query.init();
        state.record(layerManager);
        query.flush(state);
        query.dispatchHalt(null);
        query.close();
        FileAssertions.assertOutputMatchesFixture(filename, true);
    }
}
