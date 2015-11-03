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

import nanoverse.runtime.layers.cell.*;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.structural.utilities.ParityIO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dbborens on 10/23/2015.
 */
public class AgentNameCommitHelper {

    private final ParityIO parity;
    private final AgentNameIndexManager indexManager;

    public AgentNameCommitHelper(AgentNameIndexManager indexManager) {
        this(new ParityIO(), indexManager);
    }

    public AgentNameCommitHelper(ParityIO parity, AgentNameIndexManager indexManager) {
        this.parity = parity;
        this.indexManager = indexManager;
    }

    public void commit(BinaryOutputHandle file, StepState state) {
        AgentLayer layer = state.getRecordedAgentLayer();
        AgentLayerViewer viewer = layer.getViewer();
        List<Integer> indexedNameVector = indexManager
            .getIndexStream(viewer)
            .collect(Collectors.toList());
        double time = state.getTime();
        int frame = state.getFrame();

        parity.writeStart(file);
        file.writeDouble(time);
        file.writeInt(frame);
        file.writeInt(indexedNameVector.size());
        indexedNameVector.stream()
            .forEach(file::writeInt);

        parity.writeEnd(file);
    }

    public void close(BinaryOutputHandle handle) {
        parity.writeEOF(handle);
    }
}
