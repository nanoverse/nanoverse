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

import nanoverse.runtime.io.serialize.text.TextOutputHandle;
import org.junit.*;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.*;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextInputHandle.class)
public class TextInputHandleTest {

    private BufferedReader bw;
    private TextInputHandle query;

    @Before
    public void before() throws Exception {
        bw = mock(BufferedReader.class);
        query = new TextInputHandle(bw);
    }

    @Test
    public void readLine() throws Exception {
        String expected = "line";
        when(bw.readLine()).thenReturn(expected);
        String actual = query.readLine();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readLineIOExceptionHandled() throws Exception {
        doThrow(RuntimeException.class).when(bw.readLine());
        query.readLine();
    }

    @Test
    public void lines() throws Exception {
        Stream expected = mock(Stream.class);
        when(bw.lines()).thenReturn(expected);
        Stream actual = query.lines();
        assertSame(expected, actual);
    }

    @Test
    public void close() throws Exception {
        query.close();
        verify(bw).close();
    }

    @Test(expected = RuntimeException.class)
    public void closeIOExceptionHandled() throws Exception {
        doThrow(IOException.class).when(bw).close();
        query.close();
    }
}