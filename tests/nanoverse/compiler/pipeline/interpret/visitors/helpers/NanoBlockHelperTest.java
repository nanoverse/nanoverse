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

package nanoverse.compiler.pipeline.interpret.visitors.helpers;

import nanoverse.compiler.pipeline.interpret.nanosyntax.NanosyntaxParser.StatementContext;
import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import nanoverse.compiler.pipeline.interpret.visitors.NanoStatementVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.*;
import test.TestBase;

import java.util.stream.*;

import static org.mockito.Mockito.*;

public class NanoBlockHelperTest extends TestBase {

    private NanoStatementVisitor statementVisitor;
    private NanoBlockHelper query;

    private ParseTree[] children;
    private ASTNode[] childNodes;
    private ParserRuleContext ctx;

    @Before
    public void before() throws Exception {
        statementVisitor = mock(NanoStatementVisitor.class);
        query = new NanoBlockHelper(statementVisitor);

        configureContext();
    }

    private void configureContext() {
        ctx = mock(ParserRuleContext.class);
        children = new ParseTree[5];
        childNodes = new ASTNode[5];

        for (int i = 0; i < 5; i++) {
            ParseTree child = mock(ParseTree.class);

            StatementContext payload = mock(StatementContext.class);
            when(child.getPayload()).thenReturn(payload);

            children[i] = child;
            when(ctx.getChild(i)).thenReturn(child);

            ASTNode childNode = mock(ASTNode.class);
            when(child.accept(statementVisitor)).thenReturn(childNode);
            childNodes[i] = childNode;
        }

        when(ctx.getChildCount()).thenReturn(5);
    }

    @Test
    public void doVisit() throws Exception {
        Stream<ASTNode> expected = IntStream.range(1, 4)
            .mapToObj(i -> childNodes[i]);

        Stream<ASTNode> actual = query.doVisit(ctx, 1, 4);

        assertStreamsEqual(expected, actual);
    }

}