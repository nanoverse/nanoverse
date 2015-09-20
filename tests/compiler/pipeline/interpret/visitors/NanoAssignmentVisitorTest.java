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

package compiler.pipeline.interpret.visitors;

import compiler.pipeline.interpret.nanosyntax.NanosyntaxParser.*;
import compiler.pipeline.interpret.nodes.*;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.*;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NanoAssignmentVisitorTest {

    private final String identifier = "id";

    private NanoSingletonVisitor singletonVisitor;
    private NanoBlockVisitor blockVisitor;
    private ParseTree child0, child2, grandchild;
    private BlockContext child1;
    private AssignmentContext ctx;
    private CommonToken idPayload;
    private NanoAssignmentVisitor query;

    @Before
    public void init() throws Exception {
        singletonVisitor = mock(NanoSingletonVisitor.class);
        blockVisitor = mock(NanoBlockVisitor.class);

        ctx = mock(AssignmentContext.class);
        grandchild = mock(ParseTree.class);
        child0 = mock(ParseTree.class);
        child1 = mock(BlockContext.class);
        child2 = mock(ParseTree.class);
        when(ctx.getChild(0)).thenReturn(child0);
        when(ctx.getChild(1)).thenReturn(child1);
        when(ctx.getChild(2)).thenReturn(child2);

        idPayload = mock(CommonToken.class);
        when(child0.getChild(0)).thenReturn(grandchild);
        when(grandchild.getPayload()).thenReturn(idPayload);
        when(grandchild.getText()).thenReturn(identifier);

        query = new NanoAssignmentVisitor(singletonVisitor, blockVisitor);
    }

    @Test
    public void singletonCase() throws Exception {
        when(ctx.getChildCount()).thenReturn(3);
        ASTNode value = mock(ASTNode.class);
        when(child2.accept(singletonVisitor)).thenReturn(value);

        ASTNode expected = new ASTNode(identifier,
                Stream.of(value));
        ASTNode actual = query.visitAssignment(ctx);
        assertEquals(expected, actual);
    }

    @Test
    public void blockCase() throws Exception {
        when(ctx.getChildCount()).thenReturn(2);
        ASTNode dummy = mock(ASTNode.class);
        when(blockVisitor.getChildrenAsNodes(child1))
                .thenReturn(Stream.of(dummy));
        ASTNode expected = new ASTNode(identifier,
                Stream.of(dummy));
        ASTNode actual = query.visitAssignment(ctx);
        assertEquals(expected, actual);
    }
}