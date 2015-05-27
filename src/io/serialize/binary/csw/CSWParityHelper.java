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

import java.io.*;

/**
 * Created by dbborens on 5/26/2015.
 */
public class CSWParityHelper {

    public void writeStartParitySequence(DataOutputStream stream) throws IOException {
        writeParitySequence(true, stream);
    }

    public void writeEndParitySequence(DataOutputStream stream) throws IOException {
        writeParitySequence(false, stream);
    }

    private void writeParitySequence(boolean start, DataOutputStream stream) throws IOException {
        for (int i = 0; i < 8; i++) {
            stream.writeBoolean(start);
        }
    }
}
