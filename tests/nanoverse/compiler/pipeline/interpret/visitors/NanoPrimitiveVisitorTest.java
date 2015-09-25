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

package nanoverse.compiler.pipeline.interpret.visitors;

import nanoverse.compiler.pipeline.interpret.nanosyntax.NanosyntaxParser.*;
import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.*;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class NanoPrimitiveVisitorTest {

    private NanoPrimitiveDoubleVisitor doubleVisitor;
    private NanoPrimitiveIntegerVisitor intVisitor;
    private NanoPrimitiveStringVisitor stringVisitor;
    private NanoPrimitiveBooleanVisitor booleanVisitor;

    private PrimitiveContext ctx;
    private ParseTree child;
    private ASTNode expected;

    private NanoPrimitiveVisitor query;

    @Before
    public void before() throws Exception {
        doubleVisitor = mock(NanoPrimitiveDoubleVisitor.class);
        intVisitor = mock(NanoPrimitiveIntegerVisitor.class);
        stringVisitor = mock(NanoPrimitiveStringVisitor.class);
        booleanVisitor = mock(NanoPrimitiveBooleanVisitor.class);
        query = new NanoPrimitiveVisitor(doubleVisitor,
            intVisitor, stringVisitor, booleanVisitor);

        expected = mock(ASTNode.class);


    }

    @Test
    public void integerCase() throws Exception {
        configureContext(IntPrimitiveContext.class);
        when(child.accept(intVisitor)).thenReturn(expected);
        verifyReturn();
    }

    private void configureContext(Class clazz) {
        child = (ParseTree) mock(clazz);
        ctx = mock(PrimitiveContext.class);
        when(ctx.getChild(0)).thenReturn(child);
    }

    private void verifyReturn() {
        ASTNode actual = query.visitPrimitive(ctx);
        assertSame(expected, actual);
    }

    @Test
    public void stringCase() throws Exception {
        configureContext(StringPrimitiveContext.class);
        when(child.accept(stringVisitor)).thenReturn(expected);
        verifyReturn();
    }

    @Test
    public void doubleCase() throws Exception {
        configureContext(FloatPrimitiveContext.class);
        when(child.accept(doubleVisitor)).thenReturn(expected);
        verifyReturn();
    }

    @Test
    public void booleanCase() throws Exception {
        configureContext(BoolPrimitiveContext.class);
        when(child.accept(booleanVisitor)).thenReturn(expected);
        verifyReturn();
    }

    @Test(expected = IllegalStateException.class)
    public void unexpectedClassThrows() throws Exception {
        configureContext(PrimitiveContext.class);
        query.visitPrimitive(ctx);
    }
}