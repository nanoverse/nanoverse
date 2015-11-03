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

package nanoverse.compiler.pipeline.interpret.visitors;

import nanoverse.runtime.structural.NotYetImplementedException;
import org.junit.*;

import static nanoverse.compiler.pipeline.interpret.nanosyntax.NanosyntaxParser.*;
import static org.mockito.Mockito.mock;

public class RejectingVisitorTest {

    private RejectingVisitor query;

    @Before
    public void init() throws Exception {
        query = new RejectingVisitor();
    }

    @Test(expected = NotYetImplementedException.class)
    public void visitRoot() throws Exception {
        RootContext ctx = mock(RootContext.class);
        query.visitRoot(ctx);
    }

    @Test(expected = NotYetImplementedException.class)
    public void visitStatement() throws Exception {
        StatementContext ctx = mock(StatementContext.class);
        query.visitStatement(ctx);
    }

    @Test(expected = NotYetImplementedException.class)
    public void visitAssignment() throws Exception {
        AssignmentContext ctx = mock(AssignmentContext.class);
        query.visitAssignment(ctx);
    }

    @Test(expected = NotYetImplementedException.class)
    public void visitBlock() throws Exception {
        BlockContext ctx = mock(BlockContext.class);
        query.visitBlock(ctx);
    }

    @Test(expected = NotYetImplementedException.class)
    public void visitSingleton() throws Exception {
        SingletonContext ctx = mock(SingletonContext.class);
        query.visitSingleton(ctx);
    }

    @Test(expected = NotYetImplementedException.class)
    public void visitId() throws Exception {
        IdContext ctx = mock(IdContext.class);
        query.visitId(ctx);
    }

    @Test(expected = NotYetImplementedException.class)
    public void visitPrimitive() throws Exception {
        PrimitiveContext ctx = mock(PrimitiveContext.class);
        query.visitPrimitive(ctx);
    }

    @Test(expected = NotYetImplementedException.class)
    public void visitStringPrimitive() throws Exception {
        StringPrimitiveContext ctx = mock(StringPrimitiveContext.class);
        query.visitStringPrimitive(ctx);
    }

    @Test(expected = NotYetImplementedException.class)
    public void visitFloatPrimitive() throws Exception {
        FloatPrimitiveContext ctx = mock(FloatPrimitiveContext.class);
        query.visitFloatPrimitive(ctx);
    }

    @Test(expected = NotYetImplementedException.class)
    public void visitIntPrimitive() throws Exception {
        IntPrimitiveContext ctx = mock(IntPrimitiveContext.class);
        query.visitIntPrimitive(ctx);
    }
}