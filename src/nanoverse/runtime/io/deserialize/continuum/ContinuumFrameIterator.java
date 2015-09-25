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

package nanoverse.runtime.io.deserialize.continuum;

import nanoverse.runtime.structural.utilities.ParityIO;

import java.io.*;
import java.util.*;

/**
 * Created by dbborens on 5/27/2015.
 */
public class ContinuumFrameIterator implements Iterator<ContinuumFrame> {

    private final DataInputStream stream;
    private final int numSites;
    private final ParityIO parityIO;
    private boolean hasNext;

    public ContinuumFrameIterator(DataInputStream stream, int numSites) {
        try {
            this.stream = stream;
            this.numSites = numSites;
            parityIO = new ParityIO();
            hasNext = parityIO.readStartOrEOF(stream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public ContinuumFrame next() {
        if (!hasNext) {
            throw new IllegalStateException("Attempted to read past end of file in continuum state iterator");
        }

        try {
            List<Double> values = new ArrayList<>(numSites);
            double time = stream.readDouble();
            int frame = stream.readInt();
            int reportedNumSites = stream.readInt();
            if (reportedNumSites != numSites) {
                throw new IllegalStateException("Consistency error: " +
                    "unexpected coordinate number of sites in continuum " +
                    "state file");
            }
            for (int i = 0; i < numSites; i++) {
                values.add(stream.readDouble());
            }

            // Verify parity byte
            parityIO.readEnd(stream);
            hasNext = parityIO.readStartOrEOF(stream);

            if (!hasNext) {
                stream.close();
            }

            return new ContinuumFrame(values, frame, time);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
