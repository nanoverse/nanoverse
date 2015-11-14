package nanoverse.compiler.pipeline.translate.symbol;

import junit.framework.TestCase;
import org.junit.Test;
import test.TestBase;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.stream.Stream;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by lizzybradley on 11/12/15.
 */
public class MapSymbolTableParentTest extends TestBase {
    @Test
    public void testGetMemberNames() throws Exception {
        HashMap<String, MemberSymbol> members = new HashMap<>();
        members.put("a", null);
        members.put("b", null);
        members.put("c", null);

        MapSymbolTable mst = mock(MapSymbolTable.class);
        when(mst.getMemberNames()).thenCallRealMethod();

        Field f = MapSymbolTable.class.getDeclaredField("requiredMembers");
        f.setAccessible(true);
        f.set(mst, members);

        Stream testStream = Stream.of("a", "b", "c");

        assertStreamsEqual(mst.getMemberNames(), testStream);
        TestCase.assertEquals(mst.getMemberNames().count(), 3);
    }

    @Test
    public void testGetMemberDescription() throws Exception {
        HashMap<String, MemberSymbol> members = new HashMap<>();
        insertMemberSymbol(members, "a", "1");
        insertMemberSymbol(members, "b", "2");
        insertMemberSymbol(members, "c", "3");

        MapSymbolTable mst = mock(MapSymbolTable.class);
        when(mst.getMemberDescription(any(String.class))).thenCallRealMethod();

        Field f = MapSymbolTable.class.getDeclaredField("requiredMembers");
        f.setAccessible(true);
        f.set(mst, members);

        TestCase.assertEquals(mst.getMemberDescription("a"), "1");
        TestCase.assertEquals(mst.getMemberDescription("b"), "2");
        TestCase.assertEquals(mst.getMemberDescription("c"), "3");
    }

    private void insertMemberSymbol(HashMap<String, MemberSymbol> members,
                                    String key, String value) {
        MemberSymbol mock = mock(MemberSymbol.class);
        when(mock.getDescription()).thenReturn(value);

        members.put(key, mock);
    }
}
