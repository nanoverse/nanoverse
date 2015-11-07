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

package nanoverse.runtime.control;

import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.control.halt.*;
import nanoverse.runtime.io.serialize.*;
import nanoverse.runtime.processes.*;
import nanoverse.runtime.processes.discrete.*;
import nanoverse.runtime.processes.temporal.Tick;
import nanoverse.runtime.structural.MockGeneralParameters;
import org.junit.*;
import test.LegacyLatticeTest;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by David B Borenstein on 1/7/14.
 */
public class IntegratorTest extends LegacyLatticeTest {

    // Items used during construction
    private MockGeneralParameters p;
    private MockSerializationManager sm;
    private MockProcessManager mgr;
    private BaseProcessArguments arguments;
    private AgentProcessArguments cpArguments;
    // And now, the thing to be tested...
    private Integrator integrator;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        // Initialize infrastructure objects
        p = new MockGeneralParameters();
        sm = new MockSerializationManager(layerManager);
        mgr = new MockProcessManager();
        integrator = new Integrator(p, mgr, sm);
        arguments = makeBaseProcessArguments(layerManager, p);
        cpArguments = new AgentProcessArguments(null, new ConstantInteger(-1));
    }

    @Test
    public void testDoNext() throws Exception {
        // Set T to 5 loops.
        p.setT(5);
        List<NanoverseProcess> processes = new ArrayList<>(0);
        mgr.setTriggeredProcesses(processes);
        // Each of the following should have been called 5 times:
        HaltCondition halt = integrator.doNext();

        assertTrue(halt instanceof StepMaxReachedEvent);
        assertEquals(5, mgr.getTimesIterated());
    }

    @Test
    public void testTimeAppliedAtHaltNoAdvance() throws Exception {
        p.setT(10000);
        List<NanoverseProcess> processes = new ArrayList<>(1);


        processes.add(new ManualHalt(arguments, cpArguments, ""));
        mgr.setTriggeredProcesses(processes);
        ExposedIntegrator query = new ExposedIntegrator(p, mgr, sm);
        query.setTime(3.0);

        HaltCondition halt = query.doNext();
        assertTrue(halt instanceof ManualHaltEvent);
        assertEquals(3.0, halt.getGillespie(), epsilon);
    }

    @Test
    public void testTimeAppliedAtHaltAfterClockAdvance() throws Exception {
        p.setT(10000);
        List<NanoverseProcess> processes = new ArrayList<>(2);
        processes.add(new Tick(arguments, new ConstantDouble(1.0)));
        processes.add(new ManualHalt(arguments, cpArguments, ""));
        mgr.setTriggeredProcesses(processes);
        ExposedIntegrator query = new ExposedIntegrator(p, mgr, sm);
        query.setTime(3.0);

        HaltCondition halt = query.doNext();
        assertTrue(halt instanceof ManualHaltEvent);
        assertEquals(4.0, halt.getGillespie(), epsilon);
    }

    @Test
    public void testTimeAppliedAtMaxStep() throws Exception {
        p.setT(5);
        mgr.setStepStateDt(2.0);
        List<NanoverseProcess> processes = new ArrayList<>(0);
        mgr.setTriggeredProcesses(processes);
        ExposedIntegrator query = new ExposedIntegrator(p, mgr, sm);
        HaltCondition ret = query.doNext();
        assertTrue(ret instanceof StepMaxReachedEvent);
        assertEquals(10.0, ret.getGillespie(), epsilon);
    }

    private class ExposedIntegrator extends Integrator {

        public ExposedIntegrator(GeneralParameters p, ProcessManager processManager, SerializationManager serializationManager) {
            super(p, processManager, serializationManager);
        }

        public void setTime(double time) {
            this.time = time;
        }
    }

}
