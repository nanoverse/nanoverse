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

package nanoverse.compiler.pipeline.interpret.nodes;

import nanoverse.compiler.pipeline.interpret.nanosyntax.NanosyntaxParser;
import org.junit.*;
import test.TestBase;

import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ASTNodeTest extends TestBase {

    private ASTNode child1, child2;
    private ASTNode query;
    private int lineNumber = 1;

    @Before
    public void init() throws Exception {
        child1 = mock(ASTNode.class);
        child2 = mock(ASTNode.class);
        Stream<ASTNode> stream = Stream.of(child1, child2);
        query = new ASTNode("id", stream, lineNumber);
    }

    @Test
    public void size() throws Exception {
        assertEquals(2, query.size());
    }

    @Test
    public void getChildren() throws Exception {
        Stream<ASTNode> expected = Stream.of(child1, child2);
        Stream<ASTNode> actual = query.getChildren();
        assertStreamsEqual(expected, actual);
    }

    @Test
    public void getIdentifier() throws Exception {
        assertEquals("id", query.getIdentifier());
    }

    @Test
    public void equals() throws Exception {
        Stream<ASTNode> stream = Stream.of(child1, child2);
        ASTNode other = new ASTNode("id", stream, lineNumber);
        assertEquals(query, other);
    }

    @Test
    public void notEqDiffId() throws Exception {
        Stream<ASTNode> stream = Stream.of(child1, child2);
        ASTNode other = new ASTNode("something else", stream, lineNumber);
        assertNotEquals(query, other);
    }

    @Test
    public void notEqDiffChildren() throws Exception {
        Stream<ASTNode> stream = Stream.empty();
        ASTNode other = new ASTNode("id", stream, lineNumber);
        assertNotEquals(query, other);
    }
}