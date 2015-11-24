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

import nanoverse.compiler.pipeline.interpret.nanosyntax.NanosyntaxParser.StringPrimitiveContext;
import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.*;
import test.TestBase;

import java.util.List;
import java.util.stream.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NanoPrimitiveStringVisitorTest extends TestBase {

    private NanoPrimitiveStringVisitor query;
    private StringPrimitiveContext ctx;
    private ParseTree child;
    private String valueText = "test";
    private int lineNumber = 1;
    private Token token;

    @Before
    public void before() throws Exception {
        ctx = mock(StringPrimitiveContext.class);

        child = mock(ParseTree.class);
        when(ctx.getChild(0)).thenReturn(child);

        token = mock(Token.class);
        when(ctx.getStart()).thenReturn(token);
        when(token.getLine()).thenReturn(lineNumber);

        query = new NanoPrimitiveStringVisitor();
    }

    @Test
    public void visitStringPrimitive() throws Exception {
        when(ctx.getChildCount()).thenReturn(1);

        ParseTree child = mock(ParseTree.class);
        when(ctx.getChild(0)).thenReturn(child);

        CommonToken payload = mock(CommonToken.class);
        when(child.getPayload()).thenReturn(payload);

        when(child.getText()).thenReturn(valueText);

        ASTNode output = query.visitStringPrimitive(ctx);
        verifyOutput(output);
    }

    private void verifyOutput(ASTNode output) {
        verifyIdentifier(output);
        verifyChild(output);
        verifyLine(output);
    }

    private void verifyLine(ASTNode output) {
        int expected = lineNumber;
        int actual = output.lineNumber();

        assertEquals(expected, actual);
    }

    private void verifyIdentifier(ASTNode output) {
        String expected = NanoPrimitiveStringVisitor.IDENTIFIER;
        String actual = output.getIdentifier();

        assertEquals(expected, actual);
    }

    private void verifyChild(ASTNode output) {
        List<ASTNode> children = output.getChildren()
            .collect(Collectors.toList());

        assertEquals(1, children.size());

        ASTNode child = children.get(0);

        String expected = valueText;
        String actual = child.getIdentifier();
        assertEquals(expected, actual);

        Stream<ASTNode> granchildren = child.getChildren();
        assertStreamsEqual(Stream.empty(), granchildren);
    }

    @Test(expected = IllegalStateException.class)
    public void badPayloadThrows() throws Exception {
        when(ctx.getChildCount()).thenReturn(1);
        Object payload = mock(Object.class);
        when(child.getPayload()).thenReturn(payload);
        query.visitStringPrimitive(ctx);
    }

    @Test(expected = IllegalStateException.class)
    public void nullPayloadThrows() throws Exception {
        when(ctx.getChildCount()).thenReturn(1);
        query.visitStringPrimitive(ctx);
    }

    @Test(expected = IllegalStateException.class)
    public void excessChildrenThrows() throws Exception {
        when(ctx.getChildCount()).thenReturn(2);
        query.visitStringPrimitive(ctx);
    }

    @Test(expected = IllegalStateException.class)
    public void insufficientChildrenThrows() throws Exception {
        when(ctx.getChildCount()).thenReturn(0);
        query.visitStringPrimitive(ctx);
    }

}