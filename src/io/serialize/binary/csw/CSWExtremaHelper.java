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

package io.serialize.binary.csw;

import control.GeneralParameters;
import control.identifiers.*;
import processes.StepState;
import structural.utilities.FileConventions;

import java.io.*;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by dbborens on 5/22/2015.
 */
public class CSWExtremaHelper {
    private final HashMap<String, Extrema> extremaMap;
    private final CSWConsiderHelper considerHelper;
    private final Function<String, BufferedWriter> writerFunction;

    public CSWExtremaHelper(GeneralParameters p, Function<Integer, Coordinate> deindexer, Stream<String> idStream) {
        extremaMap = new HashMap<>();
        considerHelper = new CSWConsiderHelper(extremaMap, deindexer);
        writerFunction = id -> makeBufferedWriter(id, p);
        idStream.forEach(id -> extremaMap.put(id, new Extrema()));
    }

    public CSWExtremaHelper(CSWConsiderHelper considerHelper,
                            HashMap<String, Extrema> extremaMap,
                            Function<String, BufferedWriter> writerFunction) {
        this.writerFunction = writerFunction;
        this.considerHelper = considerHelper;
        this.extremaMap = extremaMap;
    }

    public void push(StepState stepState) {
        extremaMap.keySet().forEach(id ->{
            Stream<Double> values = stepState.getRecordedContinuumValues(id);
            considerHelper.consider(id, stepState.getFrame(), values);
        });
    }

    private BufferedWriter makeBufferedWriter(String id, GeneralParameters p) {
        try {
            String filename = FileConventions.makeContinuumMetadataFilename(id);
            File metadata = new File(filename);
            String filepath = p.getInstancePath() + '/' + metadata;
            FileWriter mfw = new FileWriter(filepath);
            BufferedWriter mbw = new BufferedWriter(mfw);
            return mbw;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void serialize() {
        for (String id : extremaMap.keySet()) {
            Extrema extrema = extremaMap.get(id);
            // Write the extrema file.
            try {
                BufferedWriter mbw = writerFunction.apply(id);
                mbw.write("extrema>");
                mbw.write(extrema.toString());
                mbw.write('\n');

                mbw.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
