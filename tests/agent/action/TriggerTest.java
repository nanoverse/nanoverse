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

package agent.action;

import agent.targets.MockTargetRule;
import agent.targets.TargetOccupiedNeighbors;
import agent.targets.TargetRule;
import cells.MockCell;
import control.arguments.ConstantInteger;
import control.identifiers.Coordinate;
import geometry.MockGeometry;
import layers.MockLayerManager;
import layers.cell.CellLayer;
import layers.cell.CellUpdateManager;
import processes.StepState;
import processes.discrete.filter.Filter;
import processes.discrete.filter.NullFilter;
import test.EslimeTestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Created by dbborens on 2/11/14.
 */
public class TriggerTest extends EslimeTestCase {

    private Action query;
    private MockCell causeCell, effectCell;
    private MockLayerManager layerManager;
    private String effectName;
    private MockTargetRule targetRule;
    private CellLayer cellLayer;
    private MockGeometry geom;
    private Coordinate o, p, q;
    private Random random;
//    private ConstantInteger selfChannel, targetChannel;

    @Override
    protected void setUp() throws Exception {
        // Restart "random" number generator with fixed seed
        random = new Random(RANDOM_SEED);

        // Construct base supporting objects.
        causeCell = new MockCell();
        effectCell = new MockCell();
        targetRule = new MockTargetRule();
        layerManager = new MockLayerManager();
        effectName = "effect";

        // Configure geometric state.
        geom = buildMockGeometry();
        o = geom.getCanonicalSites()[0];
        p = geom.getCanonicalSites()[1];
        q = geom.getCanonicalSites()[2];
        cellLayer = new CellLayer(geom);
        cellLayer.getUpdateManager().place(effectCell, o);
        cellLayer.getUpdateManager().place(causeCell, q);
        layerManager.setCellLayer(cellLayer);
        List<Coordinate> targets = new ArrayList<>(1);
        targets.add(o);
        targetRule.setTargets(targets);

        query = new Trigger(causeCell, layerManager, effectName, targetRule, null, null);
    }

    public void testRun() throws Exception {
        /*
          A note on "callers" in this test: the trigger action causes
          some named behavior to take place in the target cell(s). The
          cause of the trigger action is therefore distinct from the
          cause of the behavior that it triggers. Cells can of course
          trigger their own behaviors, depending on the specified target.
        */

        // Set up a calling cell at some site.
        MockCell dummy = new MockCell();

        CellUpdateManager updateManager = cellLayer.getUpdateManager();
        updateManager.place(dummy, p);

        // Run the proces originating at the dummy calling cell.
        query.run(p);

        // "dummy" should be the caller of the trigger() event.
        // (The caller of the targeter is the cause of the Trigger event.)
        assertEquals(dummy, targetRule.getLastCaller());

        // The target cell's "effect" behavior should have fired.
        assertEquals(effectName, effectCell.getLastTriggeredBehaviorName());

        // "causeCell", which causes the target cell to execute the effect
        // behavior, should be the caller of the effect behavior.
        assertEquals(q, effectCell.getLastTriggeredCaller());
    }

    public void testEquals() throws Exception {
        /*
         Trigger actions
         */
        Action identical, differentBehavior, differentTargeter;

        MockCell dummyCell1 = new MockCell();
        MockCell dummyCell2 = new MockCell();

        Filter filter = new NullFilter();
        TargetRule sameTargetRule = new TargetOccupiedNeighbors(dummyCell1, layerManager, filter, -1, random);
        TargetRule differentTargetRule = new TargetOccupiedNeighbors(dummyCell2, layerManager, filter, -1, random);
        String differentEffectName = "not the same as effectName";

        identical = new Trigger(dummyCell1, layerManager, effectName, targetRule, null, null);
        differentBehavior = new Trigger(dummyCell1, layerManager, differentEffectName, sameTargetRule, null, null);
        differentTargeter = new Trigger(dummyCell2, layerManager, effectName, differentTargetRule, null, null);

        assertEquals(query, identical);
        assertNotEquals(query, differentBehavior);
        assertNotEquals(query, differentTargeter);
    }

    public void testClone() throws Exception {
        MockCell cloneCell = new MockCell();
        Action cloned = query.clone(cloneCell);
        assert (cloned != query);
        assertEquals(query, cloned);
        assertEquals(cloneCell, cloned.getCallback());
        assertEquals(causeCell, query.getCallback());
    }

    public void testHighlighting() throws Exception {
        StepState stepState = new StepState(0.0, 0);
        layerManager.setStepState(stepState);
        ConstantInteger selfChannel = new ConstantInteger(2);
        ConstantInteger targetChannel = new ConstantInteger(4);
        query = new Trigger(causeCell, layerManager, effectName, targetRule, selfChannel, targetChannel);
        query.run(null);

        Stream<Coordinate> expected, actual;

        // Check target highlights
//        expected = new Coordinate[]{q};
        expected = Stream.of(q);
        actual = stepState.getHighlights(2);
        assertStreamsEqual(expected, actual);

        // Check cause highlights
//        expected = new Coordinate[]{o};
        expected = Stream.of(o);
        actual = stepState.getHighlights(4);
        assertStreamsEqual(expected, actual);
    }

    /**
     * If a location was chosen as a target, and it is subsequently
     * vacated, do nothing.
     * <p>
     * Regression test for ticket #83330824.
     *
     * @throws Exception
     */
    public void testSkipNewlyVacant() throws Exception {
        // Begin as with testRun() above.
        MockCell dummy = new MockCell();
        CellUpdateManager updateManager = cellLayer.getUpdateManager();
        updateManager.place(dummy, p);

        // Remove the target.
        updateManager.banish(o);

        // Run the action.
        query.run(p);

        // The action should still report that it was called.
        assertEquals(dummy, targetRule.getLastCaller());

        // ...however, the target was not on the lattice, and should not
        // have been affected.
        assertNull(effectCell.getLastTriggeredBehaviorName());
    }

    /**
     * If a cell is set to trigger something after it's dead, it skips
     * that action instead. Regression test for issue #83973358.
     */
    public void testDeadCannotTrigger() throws Exception {
        // Begin as with testRun() above.
        MockCell dummy = new MockCell();
        CellUpdateManager updateManager = cellLayer.getUpdateManager();
        updateManager.place(dummy, p);

        // Remove the target.
        updateManager.banish(q);

        // Run the action.
        query.run(p);

        // The entire action was aborted, so the target rule should
        // never have been called.
        // The action should still report that it was called.
        assertNull(targetRule.getLastCaller());

        // The target should not have been affected.
        assertNull(effectCell.getLastTriggeredBehaviorName());

    }
}
