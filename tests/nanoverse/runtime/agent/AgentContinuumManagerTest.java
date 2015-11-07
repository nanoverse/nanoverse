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

import nanoverse.runtime.agent.AgentContinuumManager;
import nanoverse.runtime.agent.AgentReactionIndex;
import nanoverse.runtime.agent.RelationshipResolver;
import nanoverse.runtime.layers.continuum.*;
import org.junit.*;

import java.util.function.*;
import java.util.stream.Stream;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class AgentContinuumManagerTest {

    public AgentContinuumManager query;
    private AgentReactionIndex index;
    private Function<String, ContinuumAgentLinker> layerResolver;
    private RelationshipResolver resolver;

    @Before
    public void before() throws Exception {
        index = mock(AgentReactionIndex.class);
        resolver = mock(RelationshipResolver.class);
        layerResolver = mock(Function.class);

        query = new AgentContinuumManager(index, resolver, layerResolver);
    }

    @Test
    public void schedule() throws Exception {
        Reaction reaction = mock(Reaction.class);
        String id = "test";
        when(reaction.getId()).thenReturn(id);

        ContinuumAgentLinker linker = mock(ContinuumAgentLinker.class);
        when(layerResolver.apply(id)).thenReturn(linker);

        Supplier<RelationshipTuple> supplier = mock(Supplier.class);
        when(resolver.resolve(reaction)).thenReturn(supplier);

        query.schedule(reaction);
        verify(index).schedule(linker, supplier, id);
    }

    @Test
    public void removeFromAll() throws Exception {
        query.removeFromAll();
        verify(index).removeFromAll();
    }

    @Test
    public void getReactionIds() throws Exception {
        Stream<String> expected = mock(Stream.class);
        when(index.getReactionIds()).thenReturn(expected);

        Stream<String> actual = query.getReactionIds();
        assertSame(expected, actual);
    }
}