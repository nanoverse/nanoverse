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

package nanoverse.compiler.pipeline.interpret;

import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import org.junit.*;

import java.io.File;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class InterpreterTest {

    private AntlrBinding antlr;
    private Interpreter query;
    private File file;

    @Before
    public void init() throws Exception {
        antlr = mock(AntlrBinding.class);
        file = mock(File.class);
        when(file.exists()).thenReturn(true);
        query = new Interpreter(antlr);
    }

    @Test
    public void interpretCallsBinding() throws Exception {
        ASTNode expected = mock(ASTNode.class);
        when(antlr.interpret(file)).thenReturn(expected);

        ASTNode actual = query.interpret(file);
        assertSame(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullFileThrows() throws Exception {
        file = null;
        query.interpret(file);
    }

    @Test(expected = IllegalArgumentException.class)
    public void absentFileThrows() throws Exception {
        when(file.exists()).thenReturn(false);
        query.interpret(file);
    }
}