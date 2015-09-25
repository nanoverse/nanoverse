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

package nanoverse.compiler.pipeline.translate.visitors;

import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.ConstantPrimitiveSymbolTable;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MasterTranslationVisitorTest {

    private DictionaryContainerVisitor dictVisitor;
    private MapContainerVisitor mapVisitor;
    private ListContainerVisitor listVisitor;
    private PrimitiveVisitor primitiveVisitor;

    private MasterTranslationVisitor query;

    private ASTNode toTranslate;
    private ObjectNode expected;

    @Before
    public void before() throws Exception {
        dictVisitor = mock(DictionaryContainerVisitor.class);
        mapVisitor = mock(MapContainerVisitor.class);
        listVisitor = mock(ListContainerVisitor.class);
        primitiveVisitor = mock(PrimitiveVisitor.class);

        query = new MasterTranslationVisitor(mapVisitor,
            listVisitor, dictVisitor, primitiveVisitor);

        toTranslate = mock(ASTNode.class);
        expected = mock(ObjectNode.class);
    }

    private void doTest(SymbolTable st) throws Exception {
        ObjectNode actual = query.translate(toTranslate, st);
        assertSame(expected, actual);
    }

    @Test
    public void listCase() throws Exception {
        ListSymbolTable st = mock(ListSymbolTable.class);
        expected = mock(ListObjectNode.class);
        when(listVisitor.translate(toTranslate, st))
            .thenReturn((ListObjectNode) expected);
        doTest(st);
    }

    @Test
    public void mapCase() throws Exception {
        MapSymbolTable st = mock(MapSymbolTable.class);
        when(mapVisitor.translate(toTranslate, st)).thenReturn(expected);
        doTest(st);
    }

    @Test
    public void dictionaryCase() throws Exception {
        DictionarySymbolTable st = mock(DictionarySymbolTable.class);
        when(dictVisitor.translate(toTranslate, st)).thenReturn(expected);
        doTest(st);
    }

    @Test
    public void primitiveCase() throws Exception {
        ConstantPrimitiveSymbolTable st = mock(ConstantPrimitiveSymbolTable.class);
        when(primitiveVisitor.translate(toTranslate, st)).thenReturn(expected);
        doTest(st);
    }

    @Test(expected = IllegalStateException.class)
    public void unexpectedClassThrows() throws Exception {
        SymbolTable st = mock(SymbolTable.class);
        query.translate(toTranslate, st);
    }

}