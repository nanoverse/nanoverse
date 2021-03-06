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

package nanoverse.compiler;

import nanoverse.Nanoverse;
import nanoverse.compiler.error.ConsoleError;
import nanoverse.runtime.control.run.Runner;
import org.junit.*;

import static org.mockito.Mockito.*;

public class NanoverseTest {

    @Before
    public void before() throws Exception {

    }

    @Test(expected = ConsoleError.class)
    public void noArgumentsThrows() throws Exception {
        String[] args = new String[0];
        new Nanoverse(args);
    }

    @Test(expected = ConsoleError.class)
    public void tooManyArgsThrows() throws Exception {
        String[] args = new String[2];
        new Nanoverse(args);
    }

    @Test
    public void goCompilesAndRuns() throws Exception {
        Compiler compiler = mock(Compiler.class);
        Runner runner = mock(Runner.class);
        when(compiler.compile()).thenReturn(runner);

        Nanoverse query = new Nanoverse(compiler);
        query.go();

        verify(runner).run();
    }
}