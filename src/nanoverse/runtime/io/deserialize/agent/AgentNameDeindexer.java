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

package nanoverse.runtime.io.deserialize.agent;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.FileSystemManager;
import nanoverse.runtime.io.deserialize.TextInputHandle;
import nanoverse.runtime.io.serialize.binary.AgentNameIndexWriter;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by dbborens on 10/25/2015.
 */
public class AgentNameDeindexer {

    private final Map<Integer, String> reverseIndex;

    public AgentNameDeindexer(GeneralParameters p) {
        this(new FileSystemManager(p), new NameIndexReader());
    }

    public AgentNameDeindexer(FileSystemManager fsManager, NameIndexReader reader) {
        String filename = AgentNameIndexWriter.INDEX_FILENAME;
        TextInputHandle indexFile = fsManager.readInstanceTextFile(filename);
        reverseIndex = reader.readReverseIndex(indexFile);
    }


    public String deindex(Integer index) {
        if (index == 0) {
            return null;
        }

        return reverseIndex.get(index);
    }
}
