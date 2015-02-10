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

package io.deserialize;

import java.io.*;
import java.util.Iterator;

/**
 * Created by dbborens on 12/11/13.
 */
public class ContinuumStateReader implements Iterator<double[]> {

    private DataInputStream input;

    private double[] current;

    public ContinuumStateReader(File file) {
        // Open the file.
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            input = new DataInputStream(bufferedInputStream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // Load the first record.
        advance();
    }

    /**
     * Advances the cursor by one record and loads that
     * record into the "current" field.
     */
    private void advance() {
        try {
            // Typical case -- read a record and load it
            double[] observed = read();
            current = observed;
        } catch (EOFException ex) {
            // End of file? Fine, stop
            current = null;
        } catch (IOException ex) {
            // Some other I/O error? Throw it
            throw new RuntimeException(ex);
        }

    }

    @Override
    public boolean hasNext() {
        // If the last attempt to load a record resulted in a null record,
        // we have reached the end of the file; there is no next record.
        if (current == null) {
            return false;
        }

        return true;
    }


    @Override
    public double[] next() {
        // Store the current record for return
        double[] ret = current;

        // Advance to the next record
        advance();

        // Return the stored record
        return ret;
    }

    private double[] read() throws IOException {
        // Check start sequence
        checkStartParitySequence();

        // Read header information
        double gillespie = input.readDouble();
        int frame = input.readInt();

        // Read data sequence
        double[] data = readData();

        // Check end sequence
        checkEndParitySequence();

        // Return state object
        return data;
    }

    private double[] readData() throws IOException {
        double[] data = PrimitiveDeserializer.readDoubleVector(input);
        return data;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks the starting parity sequence for a record, and throws an exception if the
     * parity sequence is incorrect.
     *
     * @throws IOException
     */
    private void checkStartParitySequence() throws IOException {
        // The starting parity sequence is two 1 bits.
        for (int i = 0; i < 2; i++) {
            boolean obs = input.readBoolean();
            if (!obs) {
                throw new IOException("Continuum state file is corrupt.");
            }
        }
    }

    /**
     * Checks the ending parity sequence for a record, and throws an exception if the
     * parity sequence is incorrect.
     *
     * @throws IOException
     */
    private void checkEndParitySequence() throws IOException {
        // The ending parity sequence is two 0 bits.
        for (int i = 0; i < 2; i++) {
            boolean obs = input.readBoolean();
            if (obs) {
                throw new IOException("Continuum state file is corrupt.");
            }
        }
    }

    /**
     * Close the input stream.
     */
    public void close() {
        try {
            input.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
