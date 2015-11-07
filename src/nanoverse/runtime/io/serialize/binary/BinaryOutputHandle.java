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

import java.io.*;

/**
 * Created by dbborens on 10/22/2015.
 */
public class BinaryOutputHandle {

    private final DataOutputStream stream;

    public BinaryOutputHandle(DataOutputStream stream) {
        this.stream = stream;
    }

    public void writeDouble(double toWrite) {
        try {
            stream.writeDouble(toWrite);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeFloat(float toWrite) {
        try {
            stream.writeFloat(toWrite);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeInt(int toWrite) {
        try {
            stream.writeInt(toWrite);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeBoolean(boolean toWrite) {
        try {
            stream.writeBoolean(toWrite);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeShort(short toWrite) {
        try {
            stream.writeShort(toWrite);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void flush() {
        try {
            stream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
