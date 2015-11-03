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

import org.junit.*;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.*;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.anyShort;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mock;

// We have to use PowerMockito because DataOutputStream is final
@RunWith(PowerMockRunner.class)
@PrepareForTest(BinaryOutputHandle.class)
public class BinaryOutputHandleTest {

    private DataOutputStream stream;
    private BinaryOutputHandle query;

    @Before
    public void before() throws Exception {
        stream = mock(DataOutputStream.class);
        query = new BinaryOutputHandle(stream);
    }

    @Test
    public void writeDouble() throws Exception {
        query.writeDouble(1.0);
        verify(stream).writeDouble(1.0);
    }

    @Test(expected = RuntimeException.class)
    public void writeDoubleIOExceptionCaught() throws Exception {
        doThrow(IOException.class).when(stream).writeDouble(anyDouble());
        query.writeDouble(1.0);
    }

    @Test
    public void writeFloat() throws Exception {
        query.writeFloat(1.0F);
        verify(stream).writeFloat(1.0F);
    }

    @Test(expected = RuntimeException.class)
    public void writeFloatIOExceptionCaught() throws Exception {
        doThrow(IOException.class).when(stream).writeFloat(anyFloat());
        query.writeFloat(1.0F);
    }


    @Test
    public void writeInt() throws Exception {
        query.writeInt(1);
        verify(stream).writeInt(1);
    }

    @Test(expected = RuntimeException.class)
    public void writeIntIOExceptionCaught() throws Exception {
        doThrow(IOException.class).when(stream).writeInt(anyInt());
        query.writeInt(1);
    }

    @Test
    public void writeBoolean() throws Exception {
        query.writeBoolean(true);
        verify(stream).writeBoolean(true);
    }

    @Test(expected = RuntimeException.class)
    public void writeBooleanIOExceptionCaught() throws Exception {
        doThrow(IOException.class).when(stream).writeBoolean(anyBoolean());
        query.writeBoolean(true);
    }

    @Test
    public void writeShort() throws Exception {
        short value = (short) 1;
        query.writeShort(value);
        verify(stream).writeShort(value);
    }

    @Test(expected = RuntimeException.class)
    public void writeShortIOExceptionCaught() throws Exception {
        short value = (short) 1;
        doThrow(IOException.class).when(stream).writeShort(anyShort());
        query.writeShort(value);
    }

    @Test
    public void flush() throws Exception {
        query.flush();
        verify(stream).flush();
    }

    @Test(expected = RuntimeException.class)
    public void flushIOExceptionCaught() throws Exception {
        doThrow(IOException.class).when(stream).flush();
        query.flush();
    }

    @Test
    public void close() throws Exception {
        query.close();
        verify(stream).close();
    }

    @Test(expected = RuntimeException.class)
    public void closeIOExceptionCaught() throws Exception {
        doThrow(IOException.class).when(stream).close();
        query.close();
    }

}