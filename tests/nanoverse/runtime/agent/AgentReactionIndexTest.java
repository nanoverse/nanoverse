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

package nanoverse.runtime.agent;

import nanoverse.runtime.layers.continuum.*;
import org.junit.*;

import java.util.HashSet;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class AgentReactionIndexTest {

    private HashSet<String> reactionIds;
    private HashSet<Runnable> index;
    private Agent cell;

    private AgentReactionIndex query;

    @Before
    public void before() throws Exception {
        reactionIds = mock(HashSet.class);
        index = mock(HashSet.class);
        cell = mock(Agent.class);

        query = new AgentReactionIndex(reactionIds, index, cell);
    }

    @Test
    public void schedule() throws Exception {
        ContinuumAgentLinker linker = mock(ContinuumAgentLinker.class);
        Supplier<RelationshipTuple> supplier = mock(Supplier.class);
        String id = "test";

        Runnable remover = mock(Runnable.class);
        when(linker.getRemover(cell)).thenReturn(remover);

        query.schedule(linker, supplier, id);
        verify(linker).add(cell, supplier);

        verify(index).add(remover);

        verify(reactionIds).add(id);
    }

    @Test
    public void removeFromAll() throws Exception {
        query.removeFromAll();
        verify(index).forEach(any());
    }

    @Test
    public void getReactionIds() throws Exception {
        Stream<String> expected = mock(Stream.class);
        when(reactionIds.stream()).thenReturn(expected);

        Stream<String> actual = query.getReactionIds();
        assertSame(expected, actual);
    }
}