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
import nanoverse.runtime.control.halt.*;
import nanoverse.runtime.io.serialize.Serializer;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.io.BufferedWriter;

public class HaltTimeWriter extends Serializer {

    private static final String FILENAME = "tth.txt";
    private BufferedWriter bw;

    @FactoryTarget
    public HaltTimeWriter(GeneralParameters p, LayerManager lm) {
        super(p, lm);

        // We use the non-instance path because this metric aggregates over
        // all instances.
        String filename = p.getPath() + '/' + FILENAME;
        mkDir(p.getInstancePath(), true);
        bw = makeBufferedWriter(filename);

        hAppend(bw, new StringBuilder("gillespie\thalt_info\n"));
    }

    @Override
    public void dispatchHalt(HaltCondition ex) {
        StringBuilder line = new StringBuilder();
        line.append(ex.getGillespie());
        line.append("\t");
        line.append(ex.toString());
        line.append("\n");
        hAppend(bw, line);
        closed = true;
    }

    @Override
    public void close() {
        closed = true;
        hClose(bw);
    }

    @Override
    public void flush(StepState stepState) {

    }

    private StringBuilder fixationLine(FixationEvent fix) {
        StringBuilder sb = new StringBuilder();
        sb.append(fix.getGillespie());
        sb.append("\t");
        sb.append(fix.getFixationState());
        sb.append("\n");
        return sb;
    }

}
