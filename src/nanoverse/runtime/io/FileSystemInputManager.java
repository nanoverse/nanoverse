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

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.deserialize.*;
import nanoverse.runtime.io.serialize.binary.BinaryOutputHandle;

import java.io.*;

/**
 * Created by dbborens on 10/24/2015.
 */
public class FileSystemInputManager {

    private final GeneralParameters p;
    private final DiskInputManager manager;

    public FileSystemInputManager(GeneralParameters p) {
        this.p = p;
        manager = new DiskInputManager();
    }

    public FileSystemInputManager(GeneralParameters p, DiskInputManager manager) {
        this.p = p;
        this.manager = manager;
    }

    public BinaryInputHandle readProjectBinaryFile(String filename) {
        String path = p.getPath();
        return manager.doMakeBinaryReader(path, filename);
    }

    public BinaryInputHandle readInstanceBinaryFile(String filename) {
        String path = p.getInstancePath();
        return manager.doMakeBinaryReader(path, filename);
    }

    public TextInputHandle readProjectTextFile(String filename) {
        String path = p.getPath();
        return manager.doMakeTextReader(path, filename);
    }

    public TextInputHandle readInstanceTextFile(String filename) {
        String path = p.getInstancePath();
        return manager.doMakeTextReader(path, filename);
    }



}
