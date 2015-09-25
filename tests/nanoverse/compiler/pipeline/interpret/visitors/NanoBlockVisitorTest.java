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

import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import nanoverse.compiler.pipeline.interpret.visitors.helpers.NanoBlockHelper;
import org.junit.*;

import java.util.stream.Stream;

import static nanoverse.compiler.pipeline.interpret.nanosyntax.NanosyntaxParser.BlockContext;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class NanoBlockVisitorTest {

    private NanoBlockHelper helper;
    private NanoBlockVisitor query;
    private BlockContext ctx;

    @Before
    public void init() throws Exception {
        helper = mock(NanoBlockHelper.class);
        ctx = mock(BlockContext.class);
        query = new NanoBlockVisitor(helper);
    }

    @Test
    public void getChildrenAsNodes() throws Exception {
        when(ctx.getChildCount()).thenReturn(3);
        Stream<ASTNode> expected = mock(Stream.class);
        when(helper.doVisit(ctx, 1, 2)).thenReturn(expected);
        Stream<ASTNode> actual = query.getChildrenAsNodes(ctx);
        assertSame(expected, actual);
    }
}