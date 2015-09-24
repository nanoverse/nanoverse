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
import compiler.pipeline.translate.helpers.TranslationCallback;
import compiler.pipeline.translate.nodes.ObjectNode;
import compiler.pipeline.translate.symbol.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MapGrandchildVisitorTest {

    private TranslationCallback walker;
    private GrandchildResolver gcResolver;
    private MapGrandchildVisitor query;

    @Before
    public void before() throws Exception {
        walker = mock(TranslationCallback.class);
        gcResolver = mock(GrandchildResolver.class);
        query = new MapGrandchildVisitor(walker, gcResolver);
    }

    @Test
    public void resolve() throws Exception {
        ASTNode child = mock(ASTNode.class);
        ASTNode grandchild = mock(ASTNode.class);
        when(gcResolver.getChildValue(child)).thenReturn(grandchild);

        String id = "test";
        when(grandchild.getIdentifier()).thenReturn(id);

        ResolvingSymbolTable rst = mock(ResolvingSymbolTable.class);
        InstantiableSymbolTable ist = mock(InstantiableSymbolTable.class);
        when(rst.getSymbolTable(id)).thenReturn(ist);

        ObjectNode expected = mock(ObjectNode.class);
        when(walker.walk(grandchild, ist)).thenReturn(expected);

        ObjectNode actual = query.walk(child, rst);
        assertSame(expected, actual);
    }
}