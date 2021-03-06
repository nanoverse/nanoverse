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

import nanoverse.compiler.pipeline.interpret.nanosyntax.NanosyntaxParser.RootContext;
import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import nanoverse.compiler.pipeline.interpret.visitors.helpers.NanoBlockHelper;
import org.antlr.v4.runtime.Token;
import org.junit.*;
import test.TestBase;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NanoRootVisitorTest extends TestBase {

    private NanoBlockHelper helper;
    private NanoRootVisitor query;

    private ASTNode child;

    private int lineNumber = 1;
    private Token token;


    @Before
    public void before() throws Exception {
        helper = mock(NanoBlockHelper.class);
        query = new NanoRootVisitor(helper);

        token = mock(Token.class);
        when(token.getLine()).thenReturn(lineNumber);

        child = mock(ASTNode.class);
    }

    @Test
    public void visitRoot() throws Exception {
        RootContext ctx = mock(RootContext.class);
        when(ctx.getChildCount()).thenReturn(1);
        when(ctx.getStart()).thenReturn(token);

        Stream<ASTNode> children = Stream.of(child);
        when(helper.doVisit(ctx, 0, 1)).thenReturn(children);

        ASTNode result = query.visitRoot(ctx);
        verifyContent(result);
    }

    private void verifyContent(ASTNode result) {
        verifyName(result);
        verifyValue(result);
        verifyLine(result);
    }

    private void verifyLine(ASTNode result) {
        int expected = lineNumber;
        int actual = result.getLineNumber();

        assertEquals(expected, actual);
    }

    private void verifyName(ASTNode result) {
        String expected = NanoRootVisitor.IDENTIFIER;
        String actual = result.getIdentifier();
        assertEquals(expected, actual);
    }

    private void verifyValue(ASTNode result) {
        Stream<ASTNode> expected = Stream.of(child);
        Stream<ASTNode> actual = result.getChildren();
        assertStreamsEqual(expected, actual);
    }

}