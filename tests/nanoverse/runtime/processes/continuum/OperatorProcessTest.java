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

package nanoverse.runtime.processes.continuum;

import nanoverse.runtime.processes.BaseProcessArguments;
import no.uib.cipr.matrix.sparse.CompDiagMatrix;
import org.junit.*;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;

public class OperatorProcessTest {

    private BaseProcessArguments arguments;
    private CompDiagMatrix operator;
    private Consumer<CompDiagMatrix> target;
    private OperatorProcess query;

    @Before
    public void init() {
        arguments = mock(BaseProcessArguments.class);
        operator = mock(CompDiagMatrix.class);
        target = (Consumer<CompDiagMatrix>) mock(Consumer.class);
        query = new OperatorProcess(arguments, operator, target);
    }


    @Test
    public void fireReportsMatrixToTarget() throws Exception {
        query.fire(null);
        verify(target).accept(operator);
    }

    @Test
    public void initDoesNothing() throws Exception {
        query.init();
        verifyNoMoreInteractions(arguments, operator, target);
    }

}