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

package processes.continuum;

import no.uib.cipr.matrix.DenseMatrix;
import org.junit.Before;
import org.junit.Test;
import processes.BaseProcessArguments;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;

public class OperatorProcessTest {

    private BaseProcessArguments arguments;
    private DenseMatrix operator;
    private Consumer<DenseMatrix> target;
    private OperatorProcess query;

    @Before
    public void init() {
        arguments = mock(BaseProcessArguments.class);
        operator = mock(DenseMatrix.class);
        target = (Consumer<DenseMatrix>) mock(Consumer.class);
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