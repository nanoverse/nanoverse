package nanoverse.compiler.pipeline.translate.symbol.agent.targets;

import nanoverse.compiler.pipeline.translate.symbol.tables.MapSymbolTableTest;
import nanoverse.runtime.processes.discrete.filter.Filter;
import org.junit.Test;

/**
 * Created by dbborens on 11/19/2015.
 */
public abstract class TargetRuleInstSymbolTableTest extends MapSymbolTableTest {

    @Test
    public void filter() throws Exception {
        verifyReturnSymbol("filter", Filter.class);
    }
}
