package nanoverse.compiler.pipeline.translate.symbol;

import junit.framework.TestCase;
import org.junit.Test;
import test.TestBase;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClassSymbolTableParentTest extends TestBase {
    @Test
    public void testGetMemberNames() throws Exception {
        HashMap<String, Supplier<InstantiableSymbolTable>> members = new HashMap<>();
        members.put("a", null);
        members.put("b", null);
        members.put("c", null);

        ClassSymbolTable cst = mock(ClassSymbolTable.class);

        Field f = ClassSymbolTable.class.getDeclaredField("members");
        f.setAccessible(true);
        f.set(cst, members);

        when(cst.getMemberNames()).thenCallRealMethod();

        Stream testStream = Stream.of("a", "b", "c");
        assertStreamsEqual(cst.getMemberNames(), testStream);
        TestCase.assertEquals(cst.getMemberNames().count(), 3);
    }
}
