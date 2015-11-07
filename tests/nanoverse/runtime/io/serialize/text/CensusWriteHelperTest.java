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

package nanoverse.runtime.io.serialize.text;

import nanoverse.runtime.io.FileSystemManager;
import nanoverse.runtime.structural.utilities.NanoCollections;
import org.junit.*;

import java.util.*;
import java.util.stream.*;

import static org.mockito.Mockito.*;

public class CensusWriteHelperTest {

    private HashSet<String> observedNames;
    private FileSystemManager fsManager;
    private CensusLineManager lineBuilder;
    private NanoCollections collections;
    private CensusWriteHelper query;

    @Before
    public void before() throws Exception {
        observedNames = mock(HashSet.class);
        fsManager = mock(FileSystemManager.class);
        lineBuilder = mock(CensusLineManager.class);
        collections = mock(NanoCollections.class);
        query = new CensusWriteHelper(observedNames, fsManager, lineBuilder, collections);
    }

    @Test
    public void commit() throws Exception {
        List<String> names = Stream.of("a", "b", "c").collect(Collectors.toList());
        when(observedNames.stream()).thenReturn(names.stream());

        String filename = CensusWriteHelper.FILENAME;
        TextOutputHandle handle = mock(TextOutputHandle.class);
        when(fsManager.makeInstanceTextFile(filename)).thenReturn(handle);

        query.commit();
        verify(lineBuilder).writeHeader(handle, names);
        verify(lineBuilder).writeFrames(handle, names);
    }
}