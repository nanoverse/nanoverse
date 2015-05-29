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

package structural.utilities;

import java.io.*;

/**
 * Created by dbborens on 5/26/2015.
 */
public class ParityIO {

//    private final short end   = 0b00000000;
    private final short end     = 0b01010101;
    private final short start   = 0b11001100;
    private final short eof     = 0b10101010;

    public void writeStart(DataOutputStream stream) throws IOException {
        stream.writeShort(start);
    }

    public void writeEnd(DataOutputStream stream) throws IOException {
        stream.writeShort(end);

    }

    public void writeEOF(DataOutputStream stream) throws IOException {
        stream.writeShort(eof);
    }

    /**
     * Returns true if sequence is a start sequence.
     * Returns false if sequence is an end sequence.
     * Throws an IO exception otherwise.
     *
     * @param stream
     * @return
     */
    public boolean readStartOrEOF(DataInputStream stream) throws IOException {
        short sequence = stream.readShort();
        if (sequence == start) {
            return true;
        } else if (sequence == eof) {
            return false;
        } else if (sequence == end) {
            System.out.println(sequence);
            while(true) {
                System.out.println(stream.readShort());
            }

        }

        throw new IOException("Start sequence parity error");
    }

    public void readEnd(DataInputStream stream) throws IOException {
        short sequence = stream.readShort();
        if (sequence != end) {
            throw new IOException("End sequence parity error");
        }
    }
}
