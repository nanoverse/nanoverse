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
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.*;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mock;

// We have to use PowerMockito because BinaryOutputStream is final
@RunWith(PowerMockRunner.class)
@PrepareForTest(TextOutputHandle.class)
public class TextOutputHandleTest {

    private BufferedWriter bw;
    private TextOutputHandle query;

    @Before
    public void before() throws Exception {
        bw = mock(BufferedWriter.class);
        query = new TextOutputHandle(bw);
    }

    @Test
    public void newLine() throws Exception {
        query.newLine();
        verify(bw).newLine();
    }

    @Test(expected = RuntimeException.class)
    public void newLineIOExceptionHandled() throws Exception {
        doThrow(IOException.class).when(bw).newLine();
        query.newLine();
    }

    @Test
    public void flush() throws Exception {
        query.flush();
        verify(bw).flush();
    }

    @Test(expected = RuntimeException.class)
    public void flushIOExceptionHandled() throws Exception {
        doThrow(IOException.class).when(bw).flush();
        query.flush();
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

    @Test
    public void write() throws Exception {
        String input = "test";
        query.write(input);
        verify(bw).write(input);
    }

    @Test(expected = RuntimeException.class)
    public void writeIOExceptionHandled() throws Exception {
        doThrow(IOException.class).when(bw).write(anyString());
        query.write("");
    }
}