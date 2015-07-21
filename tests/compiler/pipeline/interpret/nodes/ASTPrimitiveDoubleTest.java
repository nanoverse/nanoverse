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

package compiler.pipeline.interpret.nodes;

import org.junit.*;
import test.TestBase;

import java.util.stream.Stream;

import static org.junit.Assert.*;

public class ASTPrimitiveDoubleTest extends TestBase {

    private ASTPrimitiveDouble query;
    private Double value;

    @Before
    public void init() throws Exception {
        value = 7.3;
        query = new ASTPrimitiveDouble(value);
    }

    @Test
    public void getIdentifier() throws Exception {
        ASTPrimitiveDouble query = new ASTPrimitiveDouble(null);
        assertEquals(ASTPrimitiveDouble.IDENTIFIER, query.getIdentifier());
    }

    @Test
    public void getContent() throws Exception {
        assertEquals(value, query.getContent(), epsilon);
    }

    @Test
    public void equals() throws Exception {
        ASTPrimitiveDouble other = new ASTPrimitiveDouble(value);
        assertEquals(query, other);
    }

    @Test
    public void notEquals() throws Exception {
        ASTPrimitiveDouble other = new ASTPrimitiveDouble(value - 1.0);
        assertNotEquals(query, other);

    }

    @Test
    public void size() throws Exception {
        assertEquals(1, query.size());
    }

    @Test
    public void getChildren() throws Exception {
        Stream<ASTNode> expected = Stream.of(query);
        Stream<ASTNode> actual = query.getChildren();
        assertStreamsEqual(expected, actual);
    }
}