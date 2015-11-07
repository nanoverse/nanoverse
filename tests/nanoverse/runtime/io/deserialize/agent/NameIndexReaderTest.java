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

import nanoverse.runtime.io.deserialize.TextInputHandle;
import org.junit.Before;
import org.junit.Test;
import test.TestBase;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/26/2015.
 */
public class NameIndexReaderTest extends TestBase {

    private TextInputHandle indexFile;
    private NameIndexReader query;

    @Before
    public void before() throws Exception {
        indexFile = mock(TextInputHandle.class);
        query = new NameIndexReader();
    }

    @Test
    public void testReadReverseIndex() throws Exception {
        String toSplit = "123\tabc\n";
        Stream<String> stream = Stream.of(toSplit);
        when(indexFile.lines()).thenReturn(stream);
        Map<Integer, String> expected = makeExpected();
        Map<Integer, String> actual = query.readReverseIndex(indexFile);
        assertEquals(expected, actual);
    }

    private Map<Integer, String> makeExpected() {
        Map<Integer, String> ret = new HashMap<>(1);
        ret.put(123, "abc");
        return ret;
    }
}