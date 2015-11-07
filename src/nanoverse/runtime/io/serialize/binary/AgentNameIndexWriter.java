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

package nanoverse.runtime.io.serialize.binary;

import nanoverse.runtime.io.FileSystemManager;
import nanoverse.runtime.io.serialize.text.TextOutputHandle;

/**
 * Created by dbborens on 10/23/2015.
 */
public class AgentNameIndexWriter {

    public static final String INDEX_FILENAME = "nameIndex.txt";

    private final FileSystemManager fsManager;
    private final AgentNameIndexManager indexManager;

    public AgentNameIndexWriter(FileSystemManager fsManager, AgentNameIndexManager indexManager) {
        this.fsManager = fsManager;
        this.indexManager = indexManager;
    }

    public void writeNameIndex() {
        TextOutputHandle handle = fsManager.makeInstanceTextFile(INDEX_FILENAME);
        indexManager.getNameStream()
            .filter(name -> name != null)
            .forEach(name -> writeLine(name, handle));
        handle.close();
    }

    private void writeLine(String name, TextOutputHandle handle) {
        int index = indexManager.getIndex(name);
        String line = createLine(name, index);
        handle.write(line);
    }

    private String createLine(String name, int index) {
        StringBuilder sb = new StringBuilder();
        sb.append(index);
        sb.append('\t');
        sb.append(name);
        sb.append('\n');
        return sb.toString();
    }
}
