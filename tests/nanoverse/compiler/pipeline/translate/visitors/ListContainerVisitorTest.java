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

package nanoverse.compiler.pipeline.translate.visitors;

import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import nanoverse.compiler.pipeline.translate.nodes.ListObjectNode;
import nanoverse.compiler.pipeline.translate.symbol.ListSymbolTable;
import org.junit.*;
import org.mockito.*;

import java.util.List;
import java.util.stream.*;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class ListContainerVisitorTest {

    private ListChildLoader loader;
    private ListContainerVisitor query;

    private List<ASTNode> children;

    @Before
    public void before() throws Exception {
        loader = mock(ListChildLoader.class);
        query = new ListContainerVisitor(loader);

        children = makeChildren();
    }

    private List<ASTNode> makeChildren() {
        return IntStream.range(0, 4)
            .mapToObj(i -> mock(ASTNode.class))
            .collect(Collectors.toList());
    }

    @Test
    public void translate() throws Exception {
        ASTNode toTranslate = mock(ASTNode.class);
        when(toTranslate.getChildren()).thenReturn(children.stream());

        ListSymbolTable lst = mock(ListSymbolTable.class);
        when(lst.getBroadClass()).thenReturn(Object.class);
        ArgumentCaptor<ListObjectNode> captor = ArgumentCaptor.forClass(ListObjectNode.class);
        InOrder inOrder = inOrder(loader);

        ListObjectNode node = query.translate(toTranslate, lst);

        for (int i = 0; i < 4; i++) {
            ASTNode child = children.get(i);
            inOrder.verify(loader).loadChild(eq(child), eq(lst), captor.capture());
            assertSame(node, captor.getValue());
        }
    }
}