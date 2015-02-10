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

package control;

import control.arguments.Argument;
import control.arguments.ConstantInteger;
import layers.MockLayerManager;
import processes.BaseProcessArguments;
import processes.EcoProcess;
import processes.MockProcess;
import processes.StepState;
import test.EslimeTestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David B Borenstein on 1/7/14.
 */
public class ProcessManagerTest extends EslimeTestCase {

    private static final int CURRENT_N = 2;
    private static final double CURRENT_TIME = 1.7;
    MockProcess yes, no;
    ProcessManager query;
    MockLayerManager layerManager;

    @Override
    protected void setUp() throws Exception {
        layerManager = new MockLayerManager();
        initYesAndNo();
        buildQuery();
    }

    private void buildQuery() {
        List<EcoProcess> processes = new ArrayList<>(2);
        processes.add(yes);
        processes.add(no);
        query = new ProcessManager(processes, layerManager);
    }

    /**
     * Construct two mock processes, only one of which should be triggered.
     */
    private void initYesAndNo() {

        GeneralParameters p = makeMockGeneralParameters();
        Argument<Integer> period = new ConstantInteger(0);
        Argument<Integer> yesStart = new ConstantInteger(CURRENT_N);
        Argument<Integer> noStart = new ConstantInteger(CURRENT_N + 1);

        BaseProcessArguments yesArgs = new BaseProcessArguments(layerManager, p, 0, yesStart, period);
        BaseProcessArguments noArgs = new BaseProcessArguments(layerManager, p, 0, noStart, period);


        yes = new MockProcess(yesArgs, "yes", 0.0, 1);
        no = new MockProcess(noArgs, "no", 0.0, 1);
    }

    public void testGetTriggeredProcesses() throws Exception {
        // Call getTriggeredProcesses.
        List<EcoProcess> triggeredProcesses = query.getTriggeredProcesses(CURRENT_N);

        assertEquals(1, triggeredProcesses.size());
        assertEquals(yes, triggeredProcesses.get(0));
    }

    public void testTriggered() throws Exception {
        // Case 1a: a 1-time event that has not yet been triggered.
        assertFalse(triggerTest(3, 0));

        // Case 1b: a 1 time event that has alrady been triggered.
        assertFalse(triggerTest(1, 0));

        // Case 2: a 1-time event that is currently triggered.
        assertTrue(triggerTest(2, 0));

        // Case 3: an ongoing event that has not yet started.
        assertFalse(triggerTest(3, 1));

        // Case 4a: an ongoing event that has started, and should fire now.
        assertTrue(triggerTest(1, 1));

        // Case 4b: an ongoing event that has started, but is out of phase.
        assertFalse(triggerTest(1, 2));
    }

    public boolean triggerTest(int start, int period) throws Exception {
        GeneralParameters p = makeMockGeneralParameters();
        Argument<Integer> periodArg = new ConstantInteger(period);
        Argument<Integer> startArg = new ConstantInteger(start);

        BaseProcessArguments args = new BaseProcessArguments(layerManager, p, 0, startArg, periodArg);

        MockProcess process = new MockProcess(args, "", 0.0, 1);

        // Run ProcessManager::triggered().
        return query.triggered(CURRENT_N, process);
    }

    public void testDoTriggeredProcesses() throws Exception {
        // Execute doTriggeredProcesses.
        query.doTriggeredProcesses(new StepState(CURRENT_TIME, CURRENT_N));

        // Verify that only the triggered process actually took place.
        assertEquals(0, no.getTimesFired());
        assertEquals(1, yes.getTimesFired());
    }

    public void testStepStateRenewal() throws Exception {
        StepState first = query.doTriggeredProcesses(new StepState(0.0, 0));
        StepState second = query.doTriggeredProcesses(new StepState(0.0, 0));

        // First and second should be distinct objects (reference inequality)
        assertFalse(first == second);
    }
}
