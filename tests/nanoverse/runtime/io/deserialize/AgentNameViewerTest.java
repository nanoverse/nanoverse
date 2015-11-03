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

package nanoverse.runtime.io.deserialize;

import nanoverse.runtime.io.deserialize.agent.AgentNameViewer;
import org.junit.Before;
import org.junit.Test;
import test.TestBase;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/25/2015.
 */
public class AgentNameViewerTest extends TestBase {

    private List<String> names;
    private double time;
    private int frameNumber;
    private AgentNameViewer query;

    @Before
    public void before() throws Exception {
        names = mock(List.class);
        time = 1.0;
        frameNumber = 1;
        query = new AgentNameViewer(names, time, frameNumber);
    }

    @Test
    public void getNames() throws Exception {
        Stream<String> expected = mock(Stream.class);
        when(names.stream()).thenReturn(expected);
        Stream<String> actual = query.getNames();
        assertSame(expected, actual);
    }

    @Test
    public void getTime() throws Exception {
        assertEquals(time, query.getTime(), epsilon);
    }

    @Test
    public void getFrameNumber() throws Exception {
        assertEquals(frameNumber, query.getFrameNumber());
    }

    @Test
    public void getName() throws Exception {
        when(names.get(3)).thenReturn("test");
        assertEquals("test", query.getName(3));
    }
}