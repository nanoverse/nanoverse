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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CensusFrameLineBuilderTest {

    private HashMap<Integer, HashMap<String, Integer>> histo;
    private CensusFrameLineBuilder query;
    private HashMap<String, Integer> observations;

    @Before
    public void before() throws Exception {
        histo = mock(HashMap.class);
        query = new CensusFrameLineBuilder(histo);
        observations = makeObservations();
        when(histo.get(1)).thenReturn(observations);
    }

    private HashMap<String, Integer> makeObservations() {
        HashMap<String, Integer> observations = new HashMap<>();
        observations.put("a", 1);
        observations.put("c", 3);
        return observations;
    }

    @Test
    public void frameLine() throws Exception {
        List<String> names = Stream.of("a", "b", "c").collect(Collectors.toList());
        String actual = query.frameLine(1, names);
        String expected = "1\t1\t0\t3\n";

        assertEquals(expected, actual);
    }
}