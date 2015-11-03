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
import org.junit.*;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class AgentNameIndexWriterTest {
    private FileSystemManager fsManager;
    private AgentNameIndexManager indexManager;
    private AgentNameIndexWriter query;
    private TextOutputHandle handle;

    @Before
    public void before() throws Exception {
        fsManager = mock(FileSystemManager.class);
        indexManager = mock(AgentNameIndexManager.class);
        query = new AgentNameIndexWriter(fsManager, indexManager);
        handle = mock(TextOutputHandle.class);
        when(fsManager.makeInstanceTextFile(AgentNameIndexWriter.INDEX_FILENAME))
                .thenReturn(handle);
    }

    @Test
    public void nullNameExcluded() throws Exception {
        // Stream.of(null) throws an error
        List<String> list = new ArrayList<>(1);
        when(indexManager.getNameStream()).thenReturn(list.stream());
        query.writeNameIndex();
        verify(handle, never()).write(anyString());
        verify(handle).close();
    }

    @Test
    public void writeNameIndex() throws Exception {
        when(fsManager.makeInstanceTextFile(AgentNameIndexWriter.INDEX_FILENAME))
            .thenReturn(handle);

        String name = "name";
        Stream<String> nameStream = Stream.of(name);
        when(indexManager.getNameStream()).thenReturn(nameStream);
        when(indexManager.getIndex(name)).thenReturn(5);

        query.writeNameIndex();
        InOrder inOrder = inOrder(handle);
        inOrder.verify(handle).write("5\tname\n");
        inOrder.verify(handle).close();
        inOrder.verifyNoMoreInteractions();
    }
}