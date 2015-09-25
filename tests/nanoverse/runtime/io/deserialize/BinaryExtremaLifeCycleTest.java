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

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.io.serialize.binary.BinaryExtremaWriter;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by dbborens on 6/1/2015.
 */
public class BinaryExtremaLifeCycleTest {

    @Test
    public void lifeCycle2D() throws Exception {
        Coordinate cMin = new Coordinate2D(1, 2, 0);
        Coordinate cMax = new Coordinate2D(2, 1, Flags.BEYOND_BOUNDS);
        doTest(cMin, cMax);
    }

    private void doTest(Coordinate cMin, Coordinate cMax) throws Exception {
        TemporalCoordinate tcMin = new TemporalCoordinate(cMin, 3.0);
        TemporalCoordinate tcMax = new TemporalCoordinate(cMax, 2.0);
        Extrema output = new Extrema(-1.2, tcMin, 4e-13, tcMax);

        BinaryExtremaWriter writer = new BinaryExtremaWriter();
        DataOutputStream dos = makeOutputStream();
        writer.write(dos, output);

        BinaryExtremaReader reader = new BinaryExtremaReader();
        DataInputStream dis = makeInputStream();
        Extrema input = reader.read(dis);
        assertEquals(output, input);
    }

    private DataInputStream makeInputStream() throws Exception {
        String filePath = "./output/extrema.bin";
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        DataInputStream dis = new DataInputStream(bis);
        return dis;
    }

    private DataOutputStream makeOutputStream() throws Exception {
        String filePath = "./output/extrema.bin";
        File file = new File(filePath);
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        DataOutputStream dos = new DataOutputStream(bos);
        return dos;
    }

    @Test
    public void lifeCycle3D() throws Exception {
        Coordinate cMin = new Coordinate3D(1, 2, 3, 0);
        Coordinate cMax = new Coordinate3D(2, 1, 0, Flags.BEYOND_BOUNDS);
        doTest(cMin, cMax);
    }

}
