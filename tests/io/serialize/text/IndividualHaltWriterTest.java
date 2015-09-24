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

package io.serialize.text;

import control.GeneralParameters;
import control.halt.ManualHaltEvent;
import io.serialize.Serializer;
import org.junit.Test;
import processes.StepState;
import test.*;

public class IndividualHaltWriterTest extends EslimeLatticeTestCase {
    @Test
    public void testLifeCycle() throws Exception {
        GeneralParameters p = makeMockGeneralParameters();
        IndividualHaltWriter writer = new IndividualHaltWriter(p, layerManager);
        runCycle(writer, 0.0);
        FileAssertions.assertOutputMatchesFixture("serializations/halt.txt", "halt.txt", true);
//        assertFilesEqual("serializations/halt.txt", "halt.txt");
    }

    private void runCycle(Serializer writer, double time) {
        writer.init();
        StepState state = new StepState(time, (int) Math.round(time));
        state.record(layerManager);
        writer.flush(state);

        ManualHaltEvent haltEvent = createHaltEvent(time);
        writer.dispatchHalt(haltEvent);
        writer.close();
    }

    private ManualHaltEvent createHaltEvent(double time) {
        ManualHaltEvent ret = new ManualHaltEvent("TestSuccessful" + Math.round(time));
        ret.setGillespie(time);
        return ret;
    }
}