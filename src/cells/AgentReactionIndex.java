package cells;

import layers.continuum.*;

import java.util.HashSet;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by dbborens on 9/25/2015.
 */
public class AgentReactionIndex {

    private final HashSet<String> reactionIds;
    private final HashSet<Runnable> index;
    private final BehaviorCell cell;

    public AgentReactionIndex(HashSet<String> reactionIds,
                              HashSet<Runnable> index,
                              BehaviorCell cell) {

        this.reactionIds = reactionIds;
        this.index = index;
        this.cell = cell;
    }

    public AgentReactionIndex(BehaviorCell cell) {

        reactionIds = new HashSet<>();
        index = new HashSet<>();
        this.cell = cell;
    }

    public void schedule(ContinuumAgentLinker linker, Supplier<RelationshipTuple> supplier, String id) {
        linker.add(cell, supplier);

        Runnable remover = linker.getRemover(cell);
        index.add(remover);

        reactionIds.add(id);
    }

    public void removeFromAll() {
        index.forEach(Runnable::run);
    }

    public Stream<String> getReactionIds() {
        return reactionIds.stream();
    }
}
