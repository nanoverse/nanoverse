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
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by dbborens on 5/27/2015.
 */
public class ContinuumStateSpliterator extends AbstractSpliterator<Stream<Double>> {

    private final DataInputStream stream;
    private final int numSites;
    private final ParityIO parityIO;

    public ContinuumStateSpliterator(DataInputStream stream, int numSites) {
        super(Long.MAX_VALUE, Spliterator.ORDERED);
        this.stream = stream;
        this.numSites = numSites;
        parityIO = new ParityIO();
    }

    @Override
    public boolean tryAdvance(Consumer<? super Stream<Double>> action) {
        try {
            boolean hasNext = parityIO.readStartOrEOF(stream);

            // End of data file? Return false.
            if (hasNext == false) {
                return false;
            }

            // Otherwise, load next batch of values
            acceptStateVector(action);
            return true;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void forEachRemaining(Consumer<? super Stream<Double>> action) {
        try {
            boolean hasNext = parityIO.readStartOrEOF(stream);
            if (hasNext) {
                acceptStateVector(action);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void acceptStateVector(Consumer<? super Stream<Double>> action) throws IOException {
        List<Double> values = new ArrayList<>(numSites);

        for (int i = 0; i < numSites; i++) {
            values.add(stream.readDouble());
        }

        // Verify parity byte
        parityIO.readEnd(stream);

        action.accept(values.stream());
    }

}
