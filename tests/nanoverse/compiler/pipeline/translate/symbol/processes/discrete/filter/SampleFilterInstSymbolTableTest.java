package nanoverse.compiler.pipeline.translate.symbol.processes.discrete.filter;

import nanoverse.compiler.pipeline.translate.symbol.InstantiableSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.tables.MapSymbolTableTest;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.processes.discrete.filter.SampleFilter;

public class SampleFilterInstSymbolTableTest extends MapSymbolTableTest {

    @Override
    protected InstantiableSymbolTable getQuery() {
        return new SampleFilterInstSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return SampleFilter.class;
    }

    protected void maximum() {
        verifyReturnSymbol("maximum", IntegerArgument.class);
    }
}