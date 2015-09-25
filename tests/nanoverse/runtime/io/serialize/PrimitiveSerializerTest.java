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

package nanoverse.runtime.io.serialize;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.MockGeometry;
import nanoverse.runtime.structural.utilities.PrimitiveSerializer;
import test.*;

import java.io.*;
import java.util.*;

/**
 * Created by David B Borenstein on 3/25/14.
 */
public class PrimitiveSerializerTest extends LegacyTest {
    public void testWriteDoubleVector() throws Exception {
        DataOutputStream output = makeOutput("doubleListVector.bin");
        List<Double> test = new ArrayList<>(5);
        test.add(5.1);
        test.add(-3.0);
        test.add(0.0);
        test.add(-0.7);
        test.add(1e-7);
        PrimitiveSerializer.writeDoubleVector(output, test);
        output.close();
        FileAssertions.assertOutputMatchesFixture("doubleVector.bin", "doubleListVector.bin", false);
//        assertBinaryFilesEqual("doubleVector.bin", "doubleListVector.bin");
    }

    private DataOutputStream makeOutput(String filename) throws Exception {
        String fullName = this.outputPath + filename;
        File file = new File(fullName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        DataOutputStream output = new DataOutputStream(bufferedOutputStream);
        return output;
    }

    public void testWriteDoubleVector1() throws Exception {
        DataOutputStream output = makeOutput("doubleArrayVector.bin");
        double[] test = new double[]{5.1, -3.0, 0.0, -0.7, 1e-7};
        PrimitiveSerializer.writeDoubleVector(output, test);
        output.close();
        FileAssertions.assertOutputMatchesFixture("doubleVector.bin", "doubleArrayVector.bin", false);
//        assertBinaryFilesEqual("doubleVector.bin", "doubleArrayVector.bin");
    }

    public void testWriteIntegerVector() throws Exception {
        DataOutputStream output = makeOutput("integerVector.bin");
        List<Integer> test = new ArrayList<>(5);
        test.add(5);
        test.add(-3);
        test.add(0);
        test.add(-7);
        test.add(2);
        PrimitiveSerializer.writeIntegerVector(output, test);
        output.close();
        FileAssertions.assertOutputMatchesFixture("integerVector.bin", false);
//        assertBinaryFilesEqual("integerVector.bin");
    }

    public void testWriteBooleanVector() throws Exception {
        DataOutputStream output = makeOutput("booleanVector.bin");
        List<Boolean> test = new ArrayList<>(5);
        test.add(true);
        test.add(false);
        test.add(true);
        test.add(true);
        test.add(false);
        PrimitiveSerializer.writeBooleanVector(output, test);
        output.close();
        FileAssertions.assertOutputMatchesFixture("booleanVector.bin", false);
//        assertBinaryFilesEqual("booleanVector.bin");
    }

    public void testWriteCoercedCoordinateVector() throws Exception {
        DataOutputStream output = makeOutput("coordinateVector.bin");
        MockGeometry geom = buildMockGeometry();
        List<Coordinate> test = new ArrayList<>(5);
        for (int i = 3; i <= 0; i--) {
            Coordinate coord = geom.getCanonicalSites()[i];
            test.add(coord);
        }
        test.add(geom.getCanonicalSites()[2]);
        test.add(geom.getCanonicalSites()[1]);
        test.add(geom.getCanonicalSites()[0]);
        test.add(geom.getCanonicalSites()[3]);
        test.add(geom.getCanonicalSites()[2]);
        PrimitiveSerializer.writeCoercedCoordinateVector(output, test, geom);
        output.close();
        FileAssertions.assertOutputMatchesFixture("coordinateVector.bin", false);
//        assertBinaryFilesEqual("coordinateVector.bin");
    }
}
