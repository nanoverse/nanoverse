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

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.FileSystemManager;
import nanoverse.runtime.processes.StepState;

/**
 * Created by dbborens on 10/23/2015.
 */
public class AgentNameIOHelper {

    public static final String VECTOR_FILENAME = "nameVector.bin";

    private final FileSystemManager fsManager;
    private final AgentNameIndexWriter indexWriter;
    private final AgentNameCommitHelper commitHelper;

    private BinaryOutputHandle nameVectorFile;

    public AgentNameIOHelper(GeneralParameters p, AgentNameIndexManager indexManager) {
        commitHelper = new AgentNameCommitHelper(indexManager);
        fsManager = new FileSystemManager(p);
        indexWriter = new AgentNameIndexWriter(fsManager, indexManager);
    }

    public AgentNameIOHelper(AgentNameCommitHelper commitHelper,
                             FileSystemManager fsManager,
                             AgentNameIndexWriter indexWriter) {
        this.commitHelper = commitHelper;
        this.fsManager = fsManager;
        this.indexWriter = indexWriter;
    }

    public void init() {
        nameVectorFile = fsManager.makeInstanceBinaryFile(VECTOR_FILENAME);
    }

    public void commit(StepState state) {
        commitHelper.commit(nameVectorFile, state);
    }

    public void conclude() {
        commitHelper.close(nameVectorFile);
        nameVectorFile.close();
        indexWriter.writeNameIndex();
    }


}
