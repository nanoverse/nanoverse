/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein and the
 * Trustees of Princeton University.
 *
 * This file is part of the Nanoverse simulation framework
 * (patent pending).
 *
 * This program is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Affero General
 * Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package nanoverse.compiler.pipeline.translate.symbol;

import junit.framework.TestCase;
import org.junit.Test;
import test.TestBase;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapSymbolTableParentTest extends TestBase {
    @Test
    public void testGetMemberNames() throws Exception {
        HashMap<String, MemberSymbol> members = new HashMap<>();
        members.put("a", null);
        members.put("b", null);
        members.put("c", null);

        MapSymbolTable mst = mock(MapSymbolTable.class);
        when(mst.getMemberNames()).thenCallRealMethod();
        when(mst.resolveMembers()).thenReturn(members);

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
        when(mst.resolveMembers()).thenReturn(members);

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
