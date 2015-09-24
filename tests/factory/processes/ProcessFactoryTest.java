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
import org.junit.*;
import processes.*;
import processes.discrete.*;
import processes.discrete.check.*;
import processes.temporal.*;
import test.EslimeLatticeTestCase;

import static org.junit.Assert.assertEquals;
public class ProcessFactoryTest extends EslimeLatticeTestCase {

    private GeneralParameters p;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        p = makeMockGeneralParameters();
    }

    @Test
    public void testExponentialInverse() throws Exception {
        doTest("exponential-inverse", ExponentialInverse.class);
    }

    private void doTest(String nodeName, Class expected) {
        NanoverseProcess process = make(nodeName);
        Class actual = process.getClass();
        assertEquals(expected, actual);
    }

    private NanoverseProcess make(String nodeName) {
        Element element = new BaseElement(nodeName);
        NanoverseProcess process = ProcessFactory.instantiate(element, layerManager, p, 0);
        return process;
    }

    @Test
    public void testTick() throws Exception {
        doTest("tick", Tick.class);
    }

    @Test
    public void testDivide() throws Exception {
        doTest("divide", Divide.class);
    }

    @Test
    public void testOccupiedNeighborSwap() throws Exception {
        doTest("occupied-neighbor-swap", OccupiedNeighborSwap.class);
    }

    @Test
    public void testGeneralNeighborSwap() throws Exception {
        doTest("general-neighbor-swap", GeneralNeighborSwap.class);
    }

    @Test
    public void testScatter() throws Exception {
        doTest("scatter", Scatter.class);
    }

    @Test
    public void testFill() throws Exception {
        doTest("fill", Fill.class);
    }

    @Test
    public void testMockProcess() throws Exception {
        doTest("mock-process", MockProcess.class);
    }

    @Test
    public void testTrigger() throws Exception {
        doTest("trigger", TriggerProcess.class);
    }

    @Test
    public void testCull() throws Exception {
        doTest("cull", Cull.class);
    }

    @Test
    public void testCheckForFixation() throws Exception {
        doTest("check-for-fixation", CheckForFixation.class);
    }

    @Test
    public void testCheckThresholdOccupancy() throws Exception {
        doTest("check-threshold-occupancy", CheckForThresholdOccupancy.class);
    }

    @Test
    public void testCheckForDomination() throws Exception {
        doTest("check-for-domination", CheckForDomination.class);
    }

    @Test
    public void testCheckForExtinction() throws Exception {
        doTest("check-for-extinction", CheckForExtinction.class);
    }

    @Test
    public void testRecord() throws Exception {
        doTest("record", Record.class);
    }
}