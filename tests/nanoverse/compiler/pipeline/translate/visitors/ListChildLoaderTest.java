/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package nanoverse.compiler.pipeline.translate.visitors;

import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import nanoverse.compiler.pipeline.translate.helpers.TranslationCallback;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.compiler.pipeline.translate.symbol.*;
import org.junit.*;

import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.*;

public class ListChildLoaderTest {

    private TranslationCallback walker;
    private ListChildLoader query;

    private int LINE = 0;

    @Before
    public void before() throws Exception {
        walker = mock(TranslationCallback.class);
        query = new ListChildLoader(walker);
    }

    @Test
    public void lineNumberForError() throws Exception {
        fail();
    }

    @Test
    public void loadChild() throws Exception {
        ASTNode child = mock(ASTNode.class);
        String id = "test";
        when(child.getIdentifier()).thenReturn(id);

        ListSymbolTable lst = mock(ListSymbolTable.class);
        when(lst.getBroadClass()).thenReturn(Object.class);
        InstantiableSymbolTable ist = mock(InstantiableSymbolTable.class);
        when(lst.getSymbolTable(id, LINE)).thenReturn(ist);

        ObjectNode childNode = mock(ObjectNode.class);
        when(childNode.getInstantiatingClass()).thenReturn(Object.class);
        when(walker.walk(child, ist)).thenReturn(childNode);

        ListObjectNode lNode = mock(ListObjectNode.class);
        query.loadChild(child, lst, lNode);
        verify(lNode).loadMember(childNode);
    }
}