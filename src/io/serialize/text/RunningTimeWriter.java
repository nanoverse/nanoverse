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
import control.halt.HaltCondition;
import io.serialize.Serializer;
import layers.LayerManager;
import processes.StepState;
import structural.annotations.FactoryTarget;

import java.io.BufferedWriter;

/**
 * Writes out the number of each "state" as a function of time.
 *
 * @author dbborens
 */
public class RunningTimeWriter extends Serializer {

    private static final String FILENAME = "runtime.txt";
    private double prevTime;

    private BufferedWriter bw;

    @FactoryTarget
    public RunningTimeWriter(GeneralParameters p, LayerManager lm) {
        super(p, lm);
    }

    @Override
    public void init() {
        super.init();
        String filename = p.getInstancePath() + '/' + FILENAME;
        mkDir(p.getInstancePath(), true);
        bw = makeBufferedWriter(filename);
        prevTime = System.currentTimeMillis();
    }

    @Override
    public void flush(StepState stepState) {
        double curTime = System.currentTimeMillis();

        double dt = curTime - prevTime;

        StringBuilder sb = new StringBuilder();
        sb.append(stepState.getFrame());
        sb.append("\t");
        sb.append(dt);
        sb.append("\n");
        hAppend(bw, sb);

        prevTime = curTime;
    }

    public void dispatchHalt(HaltCondition ex) {
        conclude();
        closed = true;
    }

    private void conclude() {
        hClose(bw);
    }

    public void close() {
        // Doesn't do anything.
    }
}
