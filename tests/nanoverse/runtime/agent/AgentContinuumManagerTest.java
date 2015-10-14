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