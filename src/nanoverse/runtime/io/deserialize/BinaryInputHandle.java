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

package nanoverse.runtime.io.deserialize;

import java.io.*;

/**
 * Created by dbborens on 10/24/2015.
 */
public class BinaryInputHandle {
    private final DataInputStream dis;

    public BinaryInputHandle(DataInputStream dis) {
        this.dis = dis;
    }

    public double readDouble() {
        try {
            return dis.readDouble();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public float readFloat() {
        try {
            return dis.readFloat();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int readInt() {
        try {
            return dis.readInt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean readBoolean() {
        try {
            return dis.readBoolean();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public short readShort() {
        try {
            return dis.readShort();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            dis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
