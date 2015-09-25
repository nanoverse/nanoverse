package cells;

import layers.continuum.*;
import org.junit.*;

import java.util.HashSet;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class AgentReactionIndexTest {

    private HashSet<String> reactionIds;
    private HashSet<Runnable> index;
    private BehaviorCell cell;

    private AgentReactionIndex query;

    @Before
    public void before() throws Exception {
        reactionIds = mock(HashSet.class);
        index = mock(HashSet.class);
        cell = mock(BehaviorCell.class);

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