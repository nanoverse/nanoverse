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

import nanoverse.compiler.pipeline.interpret.nanosyntax.NanosyntaxParser.IdContext;
import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.*;
import test.TestBase;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NanoStandaloneIdVisitorTest extends TestBase {

    private String id = "test";

    private NanoStandaloneIdVisitor query;
    private IdContext ctx;

    private int lineNumber = 1;
    private Token token;

    @Before
    public void before() throws Exception {
        query = new NanoStandaloneIdVisitor();
        ctx = mock(IdContext.class);

        token = mock(Token.class);
        when(ctx.getStart()).thenReturn(token);
        when(token.getLine()).thenReturn(lineNumber);
    }

    @Test
    public void visitId() throws Exception {
        ParseTree idTree = mock(ParseTree.class);
        when(ctx.getChild(0)).thenReturn(idTree);

        CommonToken payload = mock(CommonToken.class);
        when(idTree.getPayload()).thenReturn(payload);

        when(idTree.getText()).thenReturn(id);

        ASTNode result = query.visitId(ctx);
        verifyResult(result);
    }

    private void verifyResult(ASTNode result) {
        verifyId(result);
        verifyChildren(result);
        verifyLine(result);
    }

    private void verifyLine(ASTNode result) {
        int expected = lineNumber;
        int actual = result.getLineNumber();

        assertEquals(expected, actual);
    }

    private void verifyId(ASTNode result) {
        String expected = id;
        String actual = result.getIdentifier();

        assertEquals(expected, actual);
    }

    private void verifyChildren(ASTNode result) {
        Stream<ASTNode> expected = Stream.empty();
        Stream<ASTNode> actual = result.getChildren();

        assertStreamsEqual(expected, actual);
    }

}