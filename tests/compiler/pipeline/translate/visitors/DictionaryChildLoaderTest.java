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

package compiler.pipeline.translate.visitors;

import compiler.pipeline.interpret.nodes.ASTNode;
import compiler.pipeline.translate.nodes.*;
import compiler.pipeline.translate.symbol.DictionarySymbolTable;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DictionaryChildLoaderTest {

    private DictionaryChildTranslator translator;
    private DictionaryChildLoader query;

    @Before
    public void before() throws Exception {
        translator = mock(DictionaryChildTranslator.class);
        query = new DictionaryChildLoader(translator);
    }

    @Test
    public void loadChild() throws Exception {
        ASTNode child = mock(ASTNode.class);
        String name = "test";
        when(child.getIdentifier()).thenReturn(name);

        DictionarySymbolTable st = mock(DictionarySymbolTable.class);
        ObjectNode oNode = mock(ObjectNode.class);
        when(translator.translateChild(child, st)).thenReturn(oNode);

        DictionaryObjectNode dNode = mock(DictionaryObjectNode.class);
        query.loadChild(child, st, dNode);

        verify(dNode).loadMember(name, oNode);
    }
}