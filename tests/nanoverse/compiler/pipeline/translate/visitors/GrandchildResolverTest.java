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

package nanoverse.compiler.pipeline.translate.visitors;

import nanoverse.compiler.error.SyntaxError;
import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import org.junit.*;

import java.util.List;
import java.util.stream.*;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class GrandchildResolverTest {

    private GrandchildResolver query;

    @Before
    public void before() throws Exception {
        query = new GrandchildResolver();
    }

    @Test
    public void getChildValue() throws Exception {
        List<ASTNode> childList = childList(1);
        doTest(childList);
    }

    private void doTest(List<ASTNode> childList) throws Exception {
        ASTNode child = mock(ASTNode.class);
        when(child.getChildren()).thenReturn(childList.stream());

        ASTNode actual = query.getChildValue(child);
        ASTNode expected = childList.get(0);
        assertSame(expected, actual);
    }

    private List<ASTNode> childList(int numChildren) {
        return IntStream.range(0, numChildren)
            .mapToObj(i -> mock(ASTNode.class))
            .collect(Collectors.toList());
    }

    @Test(expected = SyntaxError.class)
    public void tooManyChildrenThrows() throws Exception {
        List<ASTNode> childList = childList(2);
        doTest(childList);
    }

    @Test(expected = SyntaxError.class)
    public void tooFewChildrenFail() throws Exception {
        List<ASTNode> childList = childList(0);
        doTest(childList);
    }
}