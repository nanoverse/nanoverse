package nanoverse.compiler.pipeline.instantiate.loader.agent.action;

import java.util.function.Supplier;

/**
 * Created by dbborens on 11/6/2015.
 */
public class StochasticChoiceDefaults {
    public Supplier<Boolean> normalized() {
        return () -> false;
    }
}
