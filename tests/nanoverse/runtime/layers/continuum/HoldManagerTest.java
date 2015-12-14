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

package nanoverse.runtime.layers.continuum;

import nanoverse.runtime.layers.continuum.solvers.EquilibriumSolver;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HoldManagerTest {

    private ContinuumAgentManager manager;
    private EquilibriumSolver solver;
    private HoldManager query;

    @Before
    public void init() {
        manager = mock(ContinuumAgentManager.class);
        solver = mock(EquilibriumSolver.class);

        query = new HoldManager(manager, solver);
    }

    @Test(expected = IllegalStateException.class)
    public void releaseWhileNotHeldThrows() throws Exception {
        query.release();
    }

    @Test
    public void releaseFlipsHeldFlag() throws Exception {
        query.hold();
        query.release();
        assertFalse(query.isHeld());
    }

    @Test
    public void releaseCallsSolve() throws Exception {
        query.hold();
        query.release();
        verify(solver).solve();
    }

    @Test(expected = IllegalStateException.class)
    public void holdWhileHeldThrows() throws Exception {
        query.hold();
        query.hold();
    }

    @Test
    public void holdFlipsHeldFlag() throws Exception {
        query.hold();
        assertTrue(query.isHeld());
    }

    @Test(expected = IllegalStateException.class)
    public void solveWhileHeldThrows() throws Exception {
        query.hold();
        query.solve();
    }

    @Test
    public void solveCallsSolver() throws Exception {
        query.solve();
        verify(solver).solve();
    }

    /**
     * Verifies that solve DOES NOT apply reactions. (Prior to
     * Nanoverse v1.0.0-a10, it did.)
     *
     * @throws Exception
     */
    @Test
    public void solveDoesNotApplyReactions() throws Exception {
        query.solve();
        verify(manager, never()).apply();
    }

    @Test
    public void resetClearsHold() throws Exception {
        query.hold();
        query.reset();
        assertFalse(query.isHeld());
    }

    @Test
    public void resetCallsManager() throws Exception {
        query.reset();
        verify(manager).reset();
    }

    @Test
    public void idComesFromManager() throws Exception {
        when(manager.getId()).thenReturn("test");
        assertEquals("test", query.getId());
    }

    @Test
    public void linkerComesFromManager() throws Exception {
        ContinuumAgentLinker linker = mock(ContinuumAgentLinker.class);
        when(manager.getLinker(any())).thenReturn(linker);
    }
}