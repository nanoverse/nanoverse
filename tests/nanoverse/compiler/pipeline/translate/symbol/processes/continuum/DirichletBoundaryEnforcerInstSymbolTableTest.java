package nanoverse.compiler.pipeline.translate.symbol.processes.continuum;

import nanoverse.compiler.pipeline.translate.symbol.InstantiableSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.tables.MapSymbolTableTest;
import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.control.arguments.StringArgument;
import nanoverse.runtime.geometry.set.CoordinateSet;
import nanoverse.runtime.processes.continuum.DirichletBoundaryEnforcer;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/5/2015.
 */
public class DirichletBoundaryEnforcerInstSymbolTableTest extends MapSymbolTableTest {

    @Test
    public void testValue() throws Exception {
        verifyReturnSymbol("value", DoubleArgument.class);
    }

    @Test
    public void testLayer() throws Exception {
        verifyReturnSymbol("layer", StringArgument.class);

    }

    @Test
    public void testActiveSites() throws Exception {
        verifyReturnSymbol("activeSites", CoordinateSet.class);
    }

    @Override
    protected InstantiableSymbolTable getQuery() {
        return new DirichletBoundaryEnforcerInstSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return DirichletBoundaryEnforcer.class;
    }
}