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

package nanoverse.runtime.io.serialize.binary;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.io.serialize.Serializer;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.structural.annotations.FactoryTarget;
import nanoverse.runtime.structural.utilities.FileConventions;

import java.io.*;

/**
 * Created by dbborens on 3/28/14.
 */
public class TimeWriter extends Serializer {

    private DataOutputStream stream;

    @FactoryTarget
    public TimeWriter(GeneralParameters p, LayerManager lm) {
        super(p, lm);
        makeFiles();
    }

    @Override
    public void init() {
        super.init();
        String path = makeFilePath();
        stream = FileConventions.makeDataOutputStream(path);
    }

    private String makeFilePath() {
        String baseFileName = FileConventions.TIME_FILENAME;
        String absoluteName = p.getInstancePath() + baseFileName;
        return absoluteName;
    }

    @Override
    public void dispatchHalt(HaltCondition ex) {
        try {
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
    }

    @Override
    public void flush(StepState stepState) {
        try {
            stream.writeInt(stepState.getFrame());
            stream.writeDouble(stepState.getTime());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
