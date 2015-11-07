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

package nanoverse.runtime.io.serialize.text;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.halt.ManualHaltEvent;
import nanoverse.runtime.io.serialize.Serializer;
import nanoverse.runtime.processes.StepState;
import org.junit.Test;
import test.*;

public class HaltTimeWriterTest extends LegacyLatticeTest {

    @Test
    public void testLifeCycle() throws Exception {
        GeneralParameters p = makeMockGeneralParameters();
        HaltTimeWriter writer = new HaltTimeWriter(p, layerManager);
        for (double t = 0; t < 10.0; t += 1.0) {
            runCycle(writer, t);
        }
        writer.close();
        FileAssertions.assertOutputMatchesFixture("serializations/tth.txt", "tth.txt", true);
//        assertFilesEqual("serializations/tth.txt", "tth.txt");
    }

    private void runCycle(Serializer writer, double time) {
        writer.init();
        StepState state = new StepState(time, (int) Math.round(time));
        state.record(layerManager);
        writer.flush(state);

        ManualHaltEvent haltEvent = createHaltEvent(time);
        writer.dispatchHalt(haltEvent);
    }

    private ManualHaltEvent createHaltEvent(double time) {
        ManualHaltEvent ret = new ManualHaltEvent("TestSuccessful" + Math.round(time));
        ret.setGillespie(time);
        return ret;
    }

}