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

package nanoverse.runtime.structural.utilities;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;

import java.io.*;
import java.util.*;

/**
 * Contains utility methods for serializing vectors of primitive data types.
 * <p>
 * Created by David B Borenstein on 3/25/14.
 */
public abstract class PrimitiveSerializer {

    public static void writeDoubleVector(DataOutputStream dataOutputStream, double[] vector) {
        List<Double> list = new ArrayList<Double>(vector.length);

        for (double datum : vector) {
            list.add(datum);
        }

        writeDoubleVector(dataOutputStream, list);
    }

    /**
     * Append a vector of double values to a data output stream.
     *
     * @param dataOutputStream
     * @param vector
     */
    public static void writeDoubleVector(DataOutputStream dataOutputStream, List<Double> vector) {

        try {
            // Encode length (as a reality check)
            int length = vector.size();
            dataOutputStream.writeInt(length);
            for (int i = 0; i < vector.size(); i++) {
                double datum = vector.get(i);
                dataOutputStream.writeDouble(datum);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Append a vector of boolean values to a data outpute stream.
     *
     * @param dataOutputStream
     * @param vector
     */
    public static void writeBooleanVector(DataOutputStream dataOutputStream,
                                          List<Boolean> vector) {
        try {
            // Encode length (as a reality check)
            int length = vector.size();
            dataOutputStream.writeInt(length);
            for (int i = 0; i < vector.size(); i++) {
                Boolean datum = vector.get(i);
                dataOutputStream.writeBoolean(datum);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Convert a vector of coordinates to integers, and then append to a
     * data output stream.
     */
    public static void writeCoercedCoordinateVector(DataOutputStream dataOutputStream,
                                                    List<Coordinate> vector,
                                                    Geometry geometry) {

        ArrayList<Integer> intVector = new ArrayList<>(vector.size());
        for (Coordinate coord : vector) {
            Integer index = geometry.getIndexer().apply(coord);
            intVector.add(index);
        }

        writeIntegerVector(dataOutputStream, intVector);
    }

    /**
     * Append a vector of integer values to a data output stream.
     *
     * @param dataOutputStream
     * @param vector
     */
    public static void writeIntegerVector(DataOutputStream dataOutputStream, List<Integer> vector) {
        try {
            // Encode length (as a reality check)
            int length = vector.size();
            dataOutputStream.writeInt(length);
            for (int i = 0; i < vector.size(); i++) {
                Integer datum = vector.get(i);
                dataOutputStream.writeInt(datum);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
}
