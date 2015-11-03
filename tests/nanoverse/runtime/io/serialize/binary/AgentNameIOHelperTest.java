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
import nanoverse.runtime.processes.StepState;
import org.junit.*;

import static org.mockito.Mockito.*;

public class AgentNameIOHelperTest {

    private FileSystemManager fsManager;
    private AgentNameIndexWriter indexWriter;
    private AgentNameIOHelper query;
    private BinaryOutputHandle nameVectorFile;
    private AgentNameCommitHelper commitHelper;

    @Before
    public void before() throws Exception {
        commitHelper = mock(AgentNameCommitHelper.class);
        fsManager = mock(FileSystemManager.class);
        indexWriter = mock(AgentNameIndexWriter.class);
        nameVectorFile = mock(BinaryOutputHandle.class);
        query = new AgentNameIOHelper(commitHelper, fsManager, indexWriter);

        String name = AgentNameIOHelper.VECTOR_FILENAME;
        when(fsManager.makeInstanceBinaryFile(name))
            .thenReturn(nameVectorFile);
    }

    @Test
    public void initCommit() throws Exception {
        query.init();
        StepState state = mock(StepState.class);
        query.commit(state);
        verify(commitHelper).commit(nameVectorFile, state);
    }

    @Test
    public void initConclude() throws Exception {
        query.init();
        query.conclude();
        verify(nameVectorFile).close();
        verify(indexWriter).writeNameIndex();
    }
}