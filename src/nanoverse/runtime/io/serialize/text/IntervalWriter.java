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
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.io.serialize.Serializer;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class IntervalWriter extends Serializer {

    private final String INTERVAL_FILENAME = "interval.txt";
    // I/O handle for the interval file (What changed at each time step, and how long it took)
    private BufferedWriter intervalWriter;
    private long prevTime;

    @FactoryTarget
    public IntervalWriter(GeneralParameters p, LayerManager lm) {
        super(p, lm);


    }

    public void init() {
        super.init();
        String intervalFileStr = p.getInstancePath() + '/' + INTERVAL_FILENAME;

        try {
            File intervalFile = new File(intervalFileStr);
            FileWriter ifw = new FileWriter(intervalFile);
            intervalWriter = new BufferedWriter(ifw, 1048576);
            intervalWriter.append("Step,Gillespie,Running time\n");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        prevTime = System.currentTimeMillis();
    }

    @Override
    public void flush(StepState stepState) {
        Long interval = System.currentTimeMillis() - prevTime;
        interval(stepState.getFrame(), stepState.getTime(), interval);

        prevTime = System.currentTimeMillis();
    }

    @Override
    public void dispatchHalt(HaltCondition ex) {
        // TODO Auto-generated method stub

    }

    @Override
    public void close() {
        try {
            intervalWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Wall clock time and simulation time for last time step.
     */
    private void interval(int n, double gillespie, long interval) {
        StringBuilder sb = new StringBuilder();
        sb.append(n);
        sb.append(',');
        sb.append(gillespie);
        sb.append(',');
        sb.append(interval);
        sb.append('\n');
        try {
            intervalWriter.append(sb.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }
}
