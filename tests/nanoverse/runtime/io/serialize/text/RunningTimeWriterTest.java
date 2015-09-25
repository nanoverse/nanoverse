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

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.processes.StepState;
import org.junit.Test;
import test.LegacyLatticeTest;

import java.io.*;

import static org.junit.Assert.*;

/**
 * Since the running time writer actuall tracks wall clock time, it is
 * impossible to make a deterministic test. The only thing we can say
 * for certain is that a minimum time has elapsed, which we can nanoverse.runtime.control
 * by sleeping for a certain number of miliseconds.
 */
public class RunningTimeWriterTest extends LegacyLatticeTest {

    @Test
    public void testLifeCycle() throws Exception {
        runWithPauses();
        verifyFile();
    }

    private void verifyFile() throws Exception {
        String fn = outputPath + '/' + "runtime.txt";
        FileReader mfr = new FileReader(fn);
        BufferedReader mbr = new BufferedReader(mfr);
        String next = mbr.readLine().trim();
        checkLine(next, 0, 5.0);
        next = mbr.readLine().trim();
        checkLine(next, 1, 10.0);
    }

    private void checkLine(String next, int expectedFrame, double minimumRuntime) {
        // Lines are in the form
        // 		1   11.0
        //      2   23.4
        String[] mapping = next.split("\t");
        int frame = Integer.valueOf(mapping[0]);
        double runtime = Double.valueOf(mapping[1]);
        assertEquals(expectedFrame, frame);
        assertTrue(runtime >= minimumRuntime);
    }

    private void runWithPauses() throws Exception {
        GeneralParameters p = makeMockGeneralParameters();
        RunningTimeWriter writer = new RunningTimeWriter(p, layerManager);
        writer.init();
        StepState state = new StepState(0.0, 0);

        Thread.sleep(5);
        writer.flush(state);

        state = new StepState(1.0, 1);
        Thread.sleep(10);
        writer.flush(state);

        writer.dispatchHalt(null);
        writer.close();
    }
}