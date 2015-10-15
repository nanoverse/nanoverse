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

package nanoverse.runtime.agent.action;

import nanoverse.runtime.agent.targets.*;
import nanoverse.runtime.cells.MockAgent;
import nanoverse.runtime.control.arguments.ConstantInteger;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.MockGeometry;
import nanoverse.runtime.layers.MockLayerManager;
import nanoverse.runtime.layers.cell.*;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.processes.discrete.filter.*;
import org.junit.*;
import test.LegacyTest;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Created by dbborens on 2/11/14.
 */
public class TriggerTest extends LegacyTest {

    private Action query;
    private MockAgent causeAgent, effectAgent;
    private MockLayerManager layerManager;
    private String effectName;
    private MockTargetRule targetRule;
    private AgentLayer cellLayer;
    private MockGeometry geom;
    private Coordinate o, p, q;
    private Random random;
//    private ConstantInteger selfChannel, targetChannel;

    @Before
    public void setUp() throws Exception {
        // Restart "random" number generator with fixed seed
        random = new Random(RANDOM_SEED);

        // Construct base supporting objects.
        causeAgent = new MockAgent();
        effectAgent = new MockAgent();
        targetRule = new MockTargetRule();
        layerManager = new MockLayerManager();
        effectName = "effect";

        // Configure geometric name.
        geom = buildMockGeometry();
        o = geom.getCanonicalSites()[0];
        p = geom.getCanonicalSites()[1];
        q = geom.getCanonicalSites()[2];
        cellLayer = new AgentLayer(geom);
        cellLayer.getUpdateManager().place(effectAgent, o);
        cellLayer.getUpdateManager().place(causeAgent, q);
        layerManager.setAgentLayer(cellLayer);
        List<Coordinate> targets = new ArrayList<>(1);
        targets.add(o);
        targetRule.setTargets(targets);

        query = new Trigger(causeAgent, layerManager, effectName, targetRule, null, null);
    }

    @Test
    public void testRun() throws Exception {
        /*
          A note on "callers" in this test: the trigger action causes
          some named behavior to take place in the target cell(s). The
          cause of the trigger action is therefore distinct from the
          cause of the behavior that it triggers. Agents can of course
          trigger their own behaviors, depending on the specified target.
        */

        // Set up a calling cell at some site.
        MockAgent dummy = new MockAgent();

        AgentUpdateManager updateManager = cellLayer.getUpdateManager();
        updateManager.place(dummy, p);

        // Run the proces originating at the dummy calling cell.
        query.run(p);

        // "dummy" should be the caller of the trigger() event.
        // (The caller of the targeter is the cause of the Trigger event.)
        assertEquals(dummy, targetRule.getLastCaller());

        // The target cell's "effect" behavior should have fired.
        assertEquals(effectName, effectAgent.getLastTriggeredBehaviorName());

        // "causeAgent", which causes the target cell to execute the effect
        // behavior, should be the caller of the effect behavior.
        assertEquals(q, effectAgent.getLastTriggeredCaller());
    }

    @Test
    public void testEquals() throws Exception {
        /*
         Trigger actions
         */
        Action identical, differentBehavior, differentTargeter;

        MockAgent dummyAgent1 = new MockAgent();
        MockAgent dummyAgent2 = new MockAgent();

        Filter filter = new NullFilter();
        TargetRule sameTargetRule = new TargetOccupiedNeighbors(dummyAgent1, layerManager, filter, -1, random);
        TargetRule differentTargetRule = new TargetOccupiedNeighbors(dummyAgent2, layerManager, filter, -1, random);
        String differentEffectName = "not the same as effectName";

        identical = new Trigger(dummyAgent1, layerManager, effectName, targetRule, null, null);
        differentBehavior = new Trigger(dummyAgent1, layerManager, differentEffectName, sameTargetRule, null, null);
        differentTargeter = new Trigger(dummyAgent2, layerManager, effectName, differentTargetRule, null, null);

        assertEquals(query, identical);
        assertNotEquals(query, differentBehavior);
        assertNotEquals(query, differentTargeter);
    }

    @Test
    public void testClone() throws Exception {
        MockAgent cloneAgent = new MockAgent();
        Action cloned = query.clone(cloneAgent);
        assert (cloned != query);
        assertEquals(query, cloned);
        assertEquals(cloneAgent, cloned.getCallback());
        assertEquals(causeAgent, query.getCallback());
    }

    @Test
    public void testHighlighting() throws Exception {
        StepState stepState = new StepState(0.0, 0);
        layerManager.setStepState(stepState);
        ConstantInteger selfChannel = new ConstantInteger(2);
        ConstantInteger targetChannel = new ConstantInteger(4);
        query = new Trigger(causeAgent, layerManager, effectName, targetRule, selfChannel, targetChannel);
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
    @Test
    public void testSkipNewlyVacant() throws Exception {
        // Begin as with testRun() above.
        MockAgent dummy = new MockAgent();
        AgentUpdateManager updateManager = cellLayer.getUpdateManager();
        updateManager.place(dummy, p);

        // Remove the target.
        updateManager.banish(o);

        // Run the action.
        query.run(p);

        // The action should still report that it was called.
        assertEquals(dummy, targetRule.getLastCaller());

        // ...however, the target was not on the lattice, and should not
        // have been affected.
        assertNull(effectAgent.getLastTriggeredBehaviorName());
    }

    /**
     * If a cell is set to trigger something after it's dead, it skips
     * that action instead. Regression test for issue #83973358.
     */
    @Test
    public void testDeadCannotTrigger() throws Exception {
        // Begin as with testRun() above.
        MockAgent dummy = new MockAgent();
        AgentUpdateManager updateManager = cellLayer.getUpdateManager();
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
        assertNull(effectAgent.getLastTriggeredBehaviorName());

    }
}
