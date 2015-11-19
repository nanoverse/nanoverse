package nanoverse.compiler.pipeline.translate.symbol;

import junit.framework.TestCase;
import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import org.junit.Test;
import test.TestBase;

import java.util.HashMap;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListSymbolTableTest extends TestBase {
    @Test
    public void getMemberNames() throws Exception {
        HashMap<String, Supplier<InstantiableSymbolTable>> members = new HashMap<>();
        members.put("a", null);
        members.put("b", null);
        members.put("c", null);

        ClassSymbolTable cst = mock(ClassSymbolTable.class);
        when(cst.resolveSubclasses()).thenReturn(members);

        Supplier<Loader> loaderSupplier = mock(Supplier.class);

        ListSymbolTable lst = new ListSymbolTable(cst, loaderSupplier);
        when(lst.getMemberNames()).thenCallRealMethod();

        Stream testStream = Stream.of("a", "b", "c");
        assertStreamsEqual(lst.getMemberNames(), testStream);
        TestCase.assertEquals(lst.getMemberNames().count(), 3);
    }
}
