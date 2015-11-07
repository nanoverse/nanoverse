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

import nanoverse.runtime.io.serialize.binary.BinaryOutputHandle;
import org.junit.*;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import test.TestBase;

import java.io.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

// We have to use PowerMockito because DataInputStream is final
@RunWith(PowerMockRunner.class)
@PrepareForTest(BinaryInputHandle.class)
public class BinaryInputHandleTest extends TestBase {

    private DataInputStream dis;
    private BinaryInputHandle query;

    @Before
    public void before() throws Exception {
        dis = mock(DataInputStream.class);
        query = new BinaryInputHandle(dis);
    }

    @Test
    public void readDouble() throws Exception {
        when(dis.readDouble()).thenReturn(1.0);
        double actual = query.readDouble();
        assertEquals(1.0, actual, epsilon);
    }

    @Test(expected = RuntimeException.class)
    public void readDoubleIOExceptionCaught() throws Exception {
        doThrow(IOException.class).when(dis).readDouble();
        query.readDouble();
    }

    @Test
    public void readFloat() throws Exception {
        when(dis.readFloat()).thenReturn(1.0F);
        float actual = query.readFloat();
        assertEquals(1.0F, actual, floatEpsilon);
    }

    @Test(expected = RuntimeException.class)
    public void readFloatIOExceptionCaught() throws Exception {
        doThrow(IOException.class).when(dis).readFloat();
        query.readFloat();
    }


    @Test
    public void readInt() throws Exception {
        when(dis.readInt()).thenReturn(1);
        int actual = query.readInt();
        assertEquals(1, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readIntIOExceptionCaught() throws Exception {
        doThrow(IOException.class).when(dis).readInt();
        query.readInt();
    }

    @Test
    public void readBoolean() throws Exception {
        when(dis.readBoolean()).thenReturn(true);
        boolean actual = query.readBoolean();
        assertEquals(true, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readBooleanIOExceptionCaught() throws Exception {
        doThrow(IOException.class).when(dis).readBoolean();
        query.readBoolean();
    }

    @Test
    public void readShort() throws Exception {
        short value = (short) 1;
        when(dis.readShort()).thenReturn(value);
        query.readShort();
        short actual = query.readShort();
        assertEquals(value, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readShortIOExceptionCaught() throws Exception {
        doThrow(IOException.class).when(dis).readShort();
        query.readShort();
    }

    @Test
    public void close() throws Exception {
        query.close();
        verify(dis).close();
    }

    @Test(expected = RuntimeException.class)
    public void closeIOExceptionCaught() throws Exception {
        doThrow(IOException.class).when(dis).close();
        query.close();
    }

}