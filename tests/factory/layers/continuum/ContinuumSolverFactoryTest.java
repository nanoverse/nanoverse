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

package factory.layers.continuum;

import layers.continuum.ContinuumLayerContent;
import layers.continuum.ScheduledOperations;
import layers.continuum.solvers.ContinuumSolver;
import layers.continuum.solvers.EquilibriumSolver;
import layers.continuum.solvers.NonEquilibriumSolver;
import org.dom4j.Element;
import org.dom4j.tree.BaseElement;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ContinuumSolverFactoryTest {

    private ContinuumLayerContent content;
    private ScheduledOperations so;

    @Before
    public void before() throws Exception {
        content = mock(ContinuumLayerContent.class);
        so = mock(ScheduledOperations.class);
    }

    @Test
    public void defaultCase() throws Exception {
        Element e = null;
        ContinuumSolver result = ContinuumSolverFactory.instantiate(e, content, so);
        assertEquals(EquilibriumSolver.class, result.getClass());
    }

    private void doTest(String text, Class expected) {
        Element e = new BaseElement("solver");
        e.setText(text);
        ContinuumSolver result = ContinuumSolverFactory.instantiate(e, content, so);
        assertEquals(expected, result.getClass());

    }
    @Test
    public void equilibriumCase() throws Exception {
        doTest("equilibrium", EquilibriumSolver.class);
    }

    @Test
    public void nonEquilibriumCase() throws Exception {
        doTest("non-equilibrium", NonEquilibriumSolver.class);
    }
}