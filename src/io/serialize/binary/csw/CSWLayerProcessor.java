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

import structural.utilities.ParityIO;

import java.io.*;
import java.util.*;
import java.util.stream.*;

/**
 * Created by dbborens on 5/26/2015.
 */
public class CSWLayerProcessor {

    private final ParityIO parityHelper;
    private final HashMap<String, DataOutputStream> streamMap;

    public CSWLayerProcessor(HashMap<String, DataOutputStream> streamMap) {
        this.streamMap = streamMap;
        parityHelper = new ParityIO();
    }

    public CSWLayerProcessor(HashMap<String, DataOutputStream> streamMap, ParityIO parityHelper) {
        this.parityHelper = parityHelper;
        this.streamMap = streamMap;
    }

    public void processLayer(String id, Stream<Double> stateStream, double time, int frame) {
        DataOutputStream dataStream = streamMap.get(id);
        try {
            // Write opening parity sequence
            parityHelper.writeStart(dataStream);

            // Write entry header
            dataStream.writeDouble(time);
            dataStream.writeInt(frame);

            // Process state vector
            CSWDataProcessor.processData(dataStream, stateStream);

            // Write closing parity sequence
            parityHelper.writeEnd(dataStream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }


    public void conclude() {
        try {
            for (DataOutputStream stream : streamMap.values()) {
                parityHelper.writeEOF(stream);
                stream.flush();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
