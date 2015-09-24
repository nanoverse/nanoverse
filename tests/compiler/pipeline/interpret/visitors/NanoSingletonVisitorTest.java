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
import compiler.pipeline.interpret.nodes.ASTNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class NanoSingletonVisitorTest {

    private NanoStandaloneIdVisitor idVisitor;
    private NanoPrimitiveVisitor primitiveVisitor;
    private NanoAssignmentVisitor assignmentVisitor;

    private SingletonContext ctx;
    private ParseTree child;
    private ASTNode expected;

    private NanoSingletonVisitor query;

    @Before
    public void before() throws Exception {
        idVisitor = mock(NanoStandaloneIdVisitor.class);
        primitiveVisitor = mock(NanoPrimitiveVisitor.class);
        assignmentVisitor = mock(NanoAssignmentVisitor.class);

        query = new NanoSingletonVisitor(idVisitor, primitiveVisitor, assignmentVisitor);

        ctx = mock(SingletonContext.class);
        when(ctx.getChildCount()).thenReturn(1);

        expected = mock(ASTNode.class);
    }

    @Test(expected = IllegalStateException.class)
    public void illegalContextThrows() throws Exception {
        ParseTree child = mock(ParseTree.class);
        when(ctx.getChild(0)).thenReturn(child);
        query.visitSingleton(ctx);
    }

    @Test
    public void assignmentCase() throws Exception {
        configureContext(AssignmentContext.class);
        when(child.accept(assignmentVisitor)).thenReturn(expected);
        verifyReturn();
    }

    @Test
    public void primitiveCase() throws Exception {
        configureContext(PrimitiveContext.class);
        when(child.accept(primitiveVisitor)).thenReturn(expected);
        verifyReturn();
    }

    @Test
    public void standaloneIdCase() throws Exception {
        configureContext(IdContext.class);
        when(child.accept(idVisitor)).thenReturn(expected);
        verifyReturn();
    }

    private void configureContext(Class clazz) {
        child = (ParseTree) mock(clazz);
        ctx = mock(SingletonContext.class);
        when(ctx.getChild(0)).thenReturn(child);
    }

    private void verifyReturn() {
        ASTNode actual = query.visitSingleton(ctx);
        assertSame(expected, actual);
    }

}