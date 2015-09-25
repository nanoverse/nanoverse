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

/**
 * Writes out the number of each "state" as a function of time.
 *
 * @author dbborens
 */
public class RandomSeedWriter extends Serializer {

    private static final String FILENAME = "random.txt";

    @FactoryTarget
    public RandomSeedWriter(GeneralParameters p, LayerManager lm) {
        super(p, lm);
    }

    @Override
    public void init() {
        super.init();
        String filename = p.getInstancePath() + '/' + FILENAME;
        mkDir(p.getInstancePath(), true);
        BufferedWriter bw = makeBufferedWriter(filename);
        long seed = p.getRandomSeed();
        StringBuilder sb = new StringBuilder();
        sb.append(seed);
        hAppend(bw, sb);
        hClose(bw);
    }

    public void dispatchHalt(HaltCondition ex) {
        closed = true;
    }

    public void close() {
        // Doesn't do anything.
    }

    @Override
    public void flush(StepState stepState) {
    }
}
