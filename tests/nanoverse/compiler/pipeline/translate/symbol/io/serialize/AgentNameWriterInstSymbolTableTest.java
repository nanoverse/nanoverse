package nanoverse.compiler.pipeline.translate.symbol.io.serialize;

import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.tables.MapSymbolTableTest;
import nanoverse.runtime.io.serialize.binary.AgentNameWriter;
import org.junit.Before;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AgentNameWriterInstSymbolTableTest extends MapSymbolTableTest {

    @Override
    protected MapSymbolTable getQuery() {
        return new AgentNameWriterInstSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return AgentNameWriter.class;
    }

}