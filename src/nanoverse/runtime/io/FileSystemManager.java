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
import nanoverse.runtime.io.serialize.text.TextOutputHandle;

/**
 * Created by dbborens on 10/22/2015.
 */
public class FileSystemManager {

    private final FileSystemOutputManager outputManager;
    private final FileSystemInputManager inputManager;

    /**
     * Main constructor
     */
    public FileSystemManager(GeneralParameters p) {
        outputManager = new FileSystemOutputManager(p);
        inputManager = new FileSystemInputManager(p);
    }

    public FileSystemManager(FileSystemOutputManager outputManager,
                             FileSystemInputManager inputManager) {

        this.outputManager = outputManager;
        this.inputManager = inputManager;
    }

    /**
     * Create a binary file in the project's base directory,
     * creating subdirectories if needed.
     */
    public BinaryOutputHandle makeProjectBinaryFile(String filename) {
        return outputManager.makeProjectBinaryFile(filename);
    }

    /**
     * Create a binary file in the current instance's directory,
     * creating subdirectories if needed.
     */
    public BinaryOutputHandle makeInstanceBinaryFile(String filename) {
        return outputManager.makeInstanceBinaryFile(filename);
    }

    /**
     * Create a text file in the project's base directory,
     * creating subdirectories if needed.
     */
    public TextOutputHandle makeProjectTextFile(String filename) {
        return outputManager.makeProjectTextFile(filename);
    }

    /**
     * Create a text file in the current instance's directory,
     * creating subdirectories if needed.
     */
    public TextOutputHandle makeInstanceTextFile(String filename) {
        return outputManager.makeInstanceTextFile(filename);
    }

    /**
     * Read a binary file in the project's base directory.
     */
    public BinaryInputHandle readProjectBinaryFile(String filename) {
        return inputManager.readProjectBinaryFile(filename);
    }

    /**
     * Read a binary file in the project's base directory.
     */
    public BinaryInputHandle readInstanceBinaryFile(String filename) {
        return inputManager.readInstanceBinaryFile(filename);
    }

    /**
     * Read a text file in the project's base directory.
     */
    public TextInputHandle readProjectTextFile(String filename) {
        return inputManager.readProjectTextFile(filename);
    }

    /**
     * Read a text file in the project's base directory.
     */
    public TextInputHandle readInstanceTextFile(String filename) {
        return inputManager.readInstanceTextFile(filename);
    }

}
