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

package nanoverse.runtime.layers.continuum;

import nanoverse.runtime.agent.Agent;
import org.junit.*;

import java.util.IdentityHashMap;
import java.util.function.Supplier;

import static org.mockito.Mockito.*;

public class ContinuumAgentIndexTest {

    private IdentityHashMap<Agent, Supplier<RelationshipTuple>> map;
    private Agent cell;
    private Supplier<RelationshipTuple> supplier;
    private ContinuumAgentIndex query;

    @Before
    public void init() throws Exception {
        map = (IdentityHashMap<Agent, Supplier<RelationshipTuple>>) mock(IdentityHashMap.class);
        cell = mock(Agent.class);
        supplier = (Supplier<RelationshipTuple>) mock(Supplier.class);
        query = new ContinuumAgentIndex(map);

    }

    @Test
    public void add() throws Exception {
        query.getNotifier().add(cell, supplier);
        verify(map).put(cell, supplier);
    }

    @Test(expected = IllegalStateException.class)
    public void addExistingThrows() throws Exception {
        when(map.containsKey(any())).thenReturn(true);
        query.getNotifier().add(cell, supplier);
    }

    @Test
    public void remove() throws Exception {
        when(map.containsKey(any())).thenReturn(true);
        query.getNotifier().remove(cell);
        verify(map).remove(cell);
    }

    @Test(expected = IllegalStateException.class)
    public void removeAbsentThrows() throws Exception {
        when(map.containsKey(any())).thenReturn(false);
        query.getNotifier().remove(cell);
    }


    // I can't figure out how to mock this. Revisit later.
//    @Test
//    public void getRelationships() throws Exception {
//        map = new IdentityHashMap<>();
//        query = new ContinuumAgentIndex(map);
//        map.put(cell, supplier);
//        when(supplier.get()).thenReturn(tuple);
//
//        Stream<RelationshipTuple> expected = Stream.of(tuple);
//        Stream<RelationshipTuple> actual = query.getRelationships();
//        assertEquals(expected, actual);
//    }
}