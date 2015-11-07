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

import org.junit.*;

import java.util.*;
import java.util.stream.*;

import static org.mockito.Mockito.*;

public class CensusLineManagerTest {

    private HashMap<Integer, HashMap<String, Integer>> histo;
    private CensusFrameLineBuilder builder;
    private CensusLineManager query;
    private TextOutputHandle handle;

    @Before
    public void before() throws Exception {
        histo = mock(HashMap.class);
        builder = mock(CensusFrameLineBuilder.class);
        query = new CensusLineManager(histo, builder);

        handle = mock(TextOutputHandle.class);
    }

    @Test
    public void writeFrames() throws Exception {
        Set<Integer> keySet = Stream.of(1).collect(Collectors.toSet());
        when(histo.keySet()).thenReturn(keySet);

        List<String> names = mock(List.class);
        String line = "test";
        when(builder.frameLine(1, names)).thenReturn(line);

        query.writeFrames(handle, names);
        verify(handle).write(line);
    }

    @Test
    public void writeHeader() throws Exception {
        List<String> sortedNames = Stream.of("a", "b", "c")
            .collect(Collectors.toList());

        query.writeHeader(handle, sortedNames);
        verify(handle).write("frame\ta\tb\tc\n");
    }
}