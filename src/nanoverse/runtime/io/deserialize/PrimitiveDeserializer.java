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

package nanoverse.runtime.io.deserialize;

import nanoverse.runtime.control.identifiers.Coordinate;

import java.io.*;

/**
 * Created by David B Borenstein on 3/25/14.
 */
public abstract class PrimitiveDeserializer {
    public static double[] readDoubleVector(DataInputStream input) throws IOException {
        // Length is encoded, redundantly, in each vector.
        int length = input.readInt();

        double[] ret = new double[length];

        for (int i = 0; i < length; i++) {
            double value = input.readDouble();
            ret[i] = value;
        }

        return ret;
    }

    public static int[] readIntegerVector(DataInputStream input) throws IOException {
        // Length is encoded, redundantly, in each vector.
        int length = input.readInt();

        int[] ret = new int[length];

        for (int i = 0; i < length; i++) {
            int value = input.readInt();
            ret[i] = value;
        }

        return ret;
    }

    public static boolean[] readBooleanVector(DataInputStream input) throws IOException {
        // Length is encoded, redundantly, in each vector.
        int length = input.readInt();

        boolean[] ret = new boolean[length];

        for (int i = 0; i < length; i++) {
            boolean value = input.readBoolean();
            ret[i] = value;
        }

        return ret;
    }

    public static Coordinate[] readCoordinateVector(DataInputStream input,
                                                    CoordinateDeindexer deindex)
        throws IOException {

        // Length is encoded, redundantly, in each vector.
        int length = input.readInt();

        Coordinate[] ret = new Coordinate[length];

        for (int i = 0; i < length; i++) {
            int index = input.readInt();
            Coordinate coord = deindex.getCoordinate(index);
            ret[i] = coord;
        }

        return ret;
    }
}
