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

package nanoverse.runtime.io;

import nanoverse.runtime.io.serialize.binary.BinaryOutputHandle;
import nanoverse.runtime.io.serialize.text.TextOutputHandle;

import java.io.*;

/**
 * Created by dbborens on 10/22/2015.
 */
public class DiskOutputManager {

    public BinaryOutputHandle getBinaryHandle(String filename) {
        try {
            File file = new File(filename);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            DataOutputStream dataStream = new DataOutputStream(bufferedOutputStream);
            BinaryOutputHandle handle = new BinaryOutputHandle(dataStream);
            return handle;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public TextOutputHandle getTextHandle(String filename) {
        try {
            File file = new File(filename);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            TextOutputHandle handle = new TextOutputHandle(bw);
            return handle;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
