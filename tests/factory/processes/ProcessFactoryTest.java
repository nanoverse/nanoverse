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

package factory.processes;

import control.GeneralParameters;
import org.dom4j.Element;
import org.dom4j.tree.BaseElement;
import processes.NanoverseProcess;
import processes.MockProcess;
import processes.discrete.*;
import processes.discrete.check.CheckForDomination;
import processes.discrete.check.CheckForExtinction;
import processes.discrete.check.CheckForFixation;
import processes.discrete.check.CheckForThresholdOccupancy;
import processes.temporal.ExponentialInverse;
import processes.temporal.Tick;
import test.EslimeLatticeTestCase;

public class ProcessFactoryTest extends EslimeLatticeTestCase {

    private GeneralParameters p;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        p = makeMockGeneralParameters();
    }

    private NanoverseProcess make(String nodeName) {
        Element element = new BaseElement(nodeName);
        NanoverseProcess process = ProcessFactory.instantiate(element, layerManager, p, 0);
        return process;
    }

    private void doTest(String nodeName, Class expected) {
        NanoverseProcess process = make(nodeName);
        Class actual = process.getClass();
        assertEquals(expected, actual);
    }

    public void testExponentialInverse() throws Exception {
        doTest("exponential-inverse", ExponentialInverse.class);
    }

    public void testTick() throws Exception {
        doTest("tick", Tick.class);
    }

    public void testDivide() throws Exception {
        doTest("divide", Divide.class);
    }

    public void testOccupiedNeighborSwap() throws Exception {
        doTest("occupied-neighbor-swap", OccupiedNeighborSwap.class);
    }

    public void testGeneralNeighborSwap() throws Exception {
        doTest("general-neighbor-swap", GeneralNeighborSwap.class);
    }

    public void testScatter() throws Exception {
        doTest("scatter", Scatter.class);
    }

    public void testFill() throws Exception {
        doTest("fill", Fill.class);
    }

    public void testMockProcess() throws Exception {
        doTest("mock-process", MockProcess.class);
    }

    public void testTrigger() throws Exception {
        doTest("trigger", TriggerProcess.class);
    }

    public void testCull() throws Exception {
        doTest("cull", Cull.class);
    }

    public void testDiagnostic() throws Exception {
        doTest("diagnostic", DiagnosticProcess.class);
    }

    public void testCheckForFixation() throws Exception {
        doTest("check-for-fixation", CheckForFixation.class);
    }

    public void testCheckThresholdOccupancy() throws Exception {
        doTest("check-threshold-occupancy", CheckForThresholdOccupancy.class);
    }

    public void testCheckForDomination() throws Exception {
        doTest("check-for-domination", CheckForDomination.class);
    }

    public void testCheckForExtinction() throws Exception {
        doTest("check-for-extinction", CheckForExtinction.class);
    }

    public void testRecord() throws Exception {
        doTest("record", Record.class);
    }
}