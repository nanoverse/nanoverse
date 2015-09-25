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

package nanoverse.runtime.layers.continuum;

import no.uib.cipr.matrix.DenseVector;
import no.uib.cipr.matrix.sparse.CompDiagMatrix;
import org.junit.*;
import org.mockito.ArgumentCaptor;
import test.LinearMocks;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ContinuumLayerSchedulerTest extends LinearMocks {

    private ScheduledOperations so;
    private HoldManager holdManager;
    private ContinuumLayerScheduler query;
    private ArgumentCaptor<Runnable> runArgument;

    @Before
    public void init() throws Exception {
        so = new ScheduledOperations(geom, true);
        holdManager = mock(HoldManager.class);
        runArgument = ArgumentCaptor.forClass(Runnable.class);

        query = new ContinuumLayerScheduler(so, holdManager);
    }

    @Test
    public void apply() throws Exception {
        CompDiagMatrix matrix = matrix(1.0, 2.0, 3.0);
        query.apply(matrix);
        runRunnable();
        checkMatrix(so.getOperator(), 2.0, 3.0, 4.0);
    }

    private void runRunnable() {
        verify(holdManager).resolve(runArgument.capture());
        Runnable runnable = runArgument.getValue();
        runnable.run();
    }

    @Test
    public void exp() throws Exception {
        query.exp(a, 1.0);
        runRunnable();
        checkMatrix(so.getOperator(), 2.0, 1.0, 1.0);
    }

    @Test
    public void injectScalar() throws Exception {
        query.inject(a, 1.0);
        runRunnable();
        checkVector(so.getSource(), 1.0, 0.0, 0.0);
    }

    @Test
    public void injectVector() throws Exception {
        DenseVector vector = vector(1.0, 2.0, 3.0);
        query.inject(vector);
        runRunnable();
        assertVectorsEqual(vector, so.getSource(), epsilon);
    }

    @Test
    public void resetCallsHoldManager() throws Exception {
        query.reset();
        verify(holdManager).reset();
    }

    @Test
    public void holdCallsHoldManager() throws Exception {
        query.hold();
        verify(holdManager).hold();
    }

    @Test
    public void releaseCallsHoldManager() throws Exception {
        query.release();
        verify(holdManager).release();
    }

    @Test
    public void resetCallsScheduledOperations() throws Exception {
        // SO is not a mock, so we have to do this the hard way
        so.inject(vector(1.0, 1.0, 1.0));
        query.reset();
        checkVector(so.getSource(), 0.0, 0.0, 0.0);
    }

    @Test
    public void getLinkerAsksHoldManager() throws Exception {
        ContinuumAgentLinker linker = mock(ContinuumAgentLinker.class);
        when(holdManager.getLinker(any())).thenReturn(linker);
        assertEquals(linker, query.getLinker(null));
    }

    @Test
    public void getIdAsksHoldManager() throws Exception {
        when(holdManager.getId()).thenReturn("test");
        assertEquals("test", query.getId());
    }

    @Test
    public void solveCallsHoldManager() throws Exception {
        query.solve();
        verify(holdManager).solve();
    }

}