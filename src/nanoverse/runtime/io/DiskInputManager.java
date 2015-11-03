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

import nanoverse.runtime.io.deserialize.*;

import java.io.*;

/**
 * Created by dbborens on 10/24/2015.
 */
public class DiskInputManager {

    public BinaryInputHandle doMakeBinaryReader(String path, String filename) {
        String filePath = path + "/" + filename;

        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            return new BinaryInputHandle(dis);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public TextInputHandle doMakeTextReader(String path, String filename) {
        String filePath = path + "/" + filename;

        try {
            File file = new File(filePath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            return new TextInputHandle(br);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
