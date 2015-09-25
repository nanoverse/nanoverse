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

package nanoverse.runtime.structural.utilities;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class ParityIOTest {

    protected final String eslimeRoot = "./";
    protected final String outputPath = eslimeRoot + "/output/";
    protected final String fn = outputPath + "parity.txt";

    @Test
    public void testLifeCycle() throws Exception {
        ParityIO query = new ParityIO();
        doWrite(query);

        DataInputStream dis = makeDataInputStream();
        assertTrue(query.readStartOrEOF(dis));
        query.readEnd(dis);
        assertFalse(query.readStartOrEOF(dis));
        dis.close();
    }

    private DataInputStream makeDataInputStream() throws IOException {
        File file = new File(fn);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        DataInputStream dis = new DataInputStream(bis);
        return dis;

    }

    private void doWrite(ParityIO query) throws IOException {
        DataOutputStream dos = makeDataOutputStream();
        query.writeStart(dos);
        query.writeEnd(dos);
        query.writeEOF(dos);
        dos.close();
    }

    private DataOutputStream makeDataOutputStream() throws IOException {
        File file = new File(fn);
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        DataOutputStream dos = new DataOutputStream(bos);
        return dos;
    }
}