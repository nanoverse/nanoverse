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
import nanoverse.compiler.pipeline.translate.nodes.ObjectNode;
import nanoverse.compiler.pipeline.translate.symbol.*;
import org.junit.*;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class DictionaryChildTranslatorTest {

    private TranslationCallback walker;
    private GrandchildResolver resolver;

    private DictionaryChildTranslator query;

    private int lineNumber = 1;

    @Before
    public void before() throws Exception {
        walker = mock(TranslationCallback.class);
        resolver = mock(GrandchildResolver.class);
        query = new DictionaryChildTranslator(walker, resolver);
    }

    @Test
    public void translateChild() throws Exception {
        ASTNode child = mock(ASTNode.class);
        when(child.getLineNumber()).thenReturn(lineNumber);

        ASTNode grandchild = mock(ASTNode.class);
        when(resolver.getChildValue(child)).thenReturn(grandchild);

        String classId = "test";
        when(grandchild.getIdentifier()).thenReturn(classId);

        DictionarySymbolTable dst = mock(DictionarySymbolTable.class);
        InstantiableSymbolTable ist = mock(InstantiableSymbolTable.class);
        when(dst.getSymbolTable(classId, lineNumber)).thenReturn(ist);

        ObjectNode expected = mock(ObjectNode.class);
        when(walker.walk(grandchild, ist)).thenReturn(expected);

        ObjectNode actual = query.translateChild(child, dst);
        assertSame(expected, actual);
    }
}