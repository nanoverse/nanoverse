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

package nanoverse.runtime.io.serialize;

import nanoverse.runtime.processes.MockStepState;
import org.junit.*;
import test.LegacyTest;

import java.util.*;

import static org.junit.Assert.*;

public class SerializationManagerTest extends LegacyTest {

    private MockSerializer serializer;
    private SerializationManager query;

    @Before
    public void setUp() throws Exception {
        // Create a SM with a mock serializer child. The tests just need to
        // make sure that the child's methods each get called.
        ArrayList<Serializer> writers = new ArrayList<>(1);

        serializer = new MockSerializer(null);
        writers.add(serializer);
        query = new SerializationManager(null, null, writers);
    }

    @Test
    public void testFlushRecorded() {
        MockStepState mss = new MockStepState();
        mss.setRecord(true);
        query.flush(mss);
        assertTrue(serializer.isFlush());
    }

    @Test
    public void testFlushNotRecorded() {
        MockStepState mss = new MockStepState();
        mss.setRecord(false);
        query.flush(mss);
        assertFalse(serializer.isFlush());

    }

    @Test
    public void testInit() {
        query.init();
        assertTrue(serializer.isInit());
    }

    @Test
    public void testClose() {
        query.close();
        assertTrue(serializer.isClose());
    }

    @Test
    public void testDispatchHalt() {
        query.dispatchHalt(null);
        assertTrue(serializer.isDispatchHalt());
    }

    @Test
    public void testEquals() {
        SerializationManager p = makeExample();
        SerializationManager q = makeExample();

        assertEquals(p, q);
        assertFalse(p == q);
    }

    private SerializationManager makeExample() {
        MockSerializer a = new MockSerializer(null);
        MockSerializer b = new MockSerializer(null);

        List<Serializer> writers = new ArrayList<>(2);
        writers.add(a);
        writers.add(b);

        SerializationManager ret = new SerializationManager(null, null, writers);

        return ret;
    }
}