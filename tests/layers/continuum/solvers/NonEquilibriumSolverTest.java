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

package layers.continuum.solvers;

import layers.continuum.ContinuumLayer;
import layers.continuum.ContinuumLayerContent;
import layers.continuum.ScheduledOperations;
import no.uib.cipr.matrix.*;
import no.uib.cipr.matrix.Vector;
import org.junit.*;
import org.mockito.ArgumentCaptor;
import structural.utilities.MatrixUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import test.TestBase;

import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class NonEquilibriumSolverTest extends TestBase {

    private static final int RANGE = 10;

    private ContinuumLayerContent content;
    private Vector state, source;
    private Matrix operator;
    private ScheduledOperations so;
    private NonEquilibriumSolver query;

    @Before
    public void before() throws Exception {
        state = makeStateVector();
        content = mock(ContinuumLayerContent.class);
        when(content.getState()).thenReturn(state);

        source = makeSourceVector();
        operator = MatrixUtils.I(RANGE);
        so = mock(ScheduledOperations.class);
        when(so.getOperator()).thenReturn(operator);
        when(so.getSource()).thenReturn(source);
        query = new NonEquilibriumSolver(content, so);
    }

    private Vector makeSourceVector() {
        DenseVector vector = new DenseVector(RANGE);
        vector.set(0, 0.5);
        return vector;
    }

    private Vector makeStateVector() {
        DenseVector vector = new DenseVector(RANGE);
        IntStream.range(0, RANGE)
                .boxed()
                .forEach(i -> vector.set(i, i * 1.0));
        return vector;
    }

    @Test(expected = NotImplementedException.class)
    public void nontrivialMatrixThrows() throws Exception {
        operator = operator.scale(2.0);
        query.solve();
    }

    @Test
    public void doSolve() throws Exception {
        query.solve();
        ArgumentCaptor<Vector> ac = ArgumentCaptor.forClass(Vector.class);
        verify(content).setState(ac.capture());

        Vector expected = source.add(state);
        assertVectorsEqual(expected, ac.getValue(), epsilon);
    }
}