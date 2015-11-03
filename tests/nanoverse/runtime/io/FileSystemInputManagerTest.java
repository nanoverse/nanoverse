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
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FileSystemInputManagerTest {

    private static final String PATH = "path";
    private static final String INST_PATH = "instPath";
    private static final String NAME = "name";

    private GeneralParameters p;
    private DiskInputManager manager;
    private FileSystemInputManager query;

    @Before
    public void before() throws Exception {
        p = mock(GeneralParameters.class);
        when(p.getPath()).thenReturn(PATH);
        when(p.getInstancePath()).thenReturn(INST_PATH);

        manager = mock(DiskInputManager.class);
        query = new FileSystemInputManager(p, manager);
    }

    @Test
    public void readProjectBinaryFile() throws Exception {
        BinaryInputHandle expected = mock(BinaryInputHandle.class);
        when(manager.doMakeBinaryReader(PATH, NAME)).thenReturn(expected);

        BinaryInputHandle actual = query.readProjectBinaryFile(NAME);
        assertSame(expected, actual);
    }

    @Test
    public void readInstanceBinaryFile() throws Exception {
        BinaryInputHandle expected = mock(BinaryInputHandle.class);
        when(manager.doMakeBinaryReader(INST_PATH, NAME)).thenReturn(expected);

        BinaryInputHandle actual = query.readInstanceBinaryFile(NAME);
        assertSame(expected, actual);
    }

    @Test
    public void readProjectTextFile() throws Exception {
        TextInputHandle expected = mock(TextInputHandle.class);
        when(manager.doMakeTextReader(PATH, NAME)).thenReturn(expected);

        TextInputHandle actual = query.readProjectTextFile(NAME);
        assertSame(expected, actual);
    }

    @Test
    public void readInstanceTextFile() throws Exception {
        TextInputHandle expected = mock(TextInputHandle.class);
        when(manager.doMakeTextReader(INST_PATH, NAME)).thenReturn(expected);

        TextInputHandle actual = query.readInstanceTextFile(NAME);
        assertSame(expected, actual);
    }
}