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

import nanoverse.runtime.io.FileSystemManager;
import nanoverse.runtime.io.deserialize.TextInputHandle;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/25/2015.
 */
public class AgentNameDeindexerTest {

    private AgentNameDeindexer query;
    private Map<Integer, String> reverseIndex;

    @Before
    public void before() throws Exception {
        FileSystemManager fsManager = mock(FileSystemManager.class);
        TextInputHandle handle = mock(TextInputHandle.class);
        when(fsManager.readInstanceTextFile(anyString())).thenReturn(handle);

        reverseIndex = mock(Map.class);
        NameIndexReader reader = mock(NameIndexReader.class);
        when(reader.readReverseIndex(handle)).thenReturn(reverseIndex);
        query = new AgentNameDeindexer(fsManager, reader);
    }

    @Test
    public void testLifeCycle() throws Exception {
        String expected = "a";
        when(reverseIndex.get(1)).thenReturn(expected);
        String actual = query.deindex(1);
        assertSame(expected, actual);
    }

    @Test
    public void nullDeindexSpecial() throws Exception {
        when(reverseIndex.get(anyInt())).thenThrow(new RuntimeException());
        assertNull(query.deindex(0));
    }
}