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

package compiler.pipeline.instantiate.loader.layers.continuum;

import compiler.pipeline.instantiate.loader.InterpolatorTest;
import compiler.pipeline.instantiate.loader.layers.continuum.solvers.ContinuumSolverLoader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.arguments.GeometryDescriptor;
import layers.continuum.*;
import layers.continuum.solvers.ContinuumSolver;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HoldManagerInterpolatorTest extends InterpolatorTest {

    private HoldManagerInterpolator query;
    private HoldManagerDefaults defaults;
    private ContinuumLayerContent content;
    private ScheduledOperations so;

    private GeometryDescriptor geom;

    @Before
    public void before() throws Exception {
        super.before();
        geom = mock(GeometryDescriptor.class);
        content = mock(ContinuumLayerContent.class);
        so = mock(ScheduledOperations.class);
        defaults = mock(HoldManagerDefaults.class);
        query = new HoldManagerInterpolator(load, defaults);

    }

    @Test
    public void id() throws Exception {
        String expected = "my layer";
        when(load.aString(eq(node), eq("id"))).thenReturn(expected);
        String actual = query.id(node);
        assertEquals(expected, actual);
    }

    @Test
    public void solver() throws Exception {
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("solver")).thenReturn(cNode);

        ContinuumSolverLoader loader = mock(ContinuumSolverLoader.class);
        when(load.getLoader(eq(node), eq("solver"), anyBoolean())).thenReturn(loader);

        ContinuumSolver expected = mock(ContinuumSolver.class);
        when(loader.instantiate(cNode, content, so)).thenReturn(expected);

        ContinuumSolver actual = query.solver(node, content, so);
        assertSame(expected, actual);
    }

    @Test
    public void solverDefault() throws Exception {
        ContinuumSolver expected = mock(ContinuumSolver.class);
        when(defaults.solver(content, so)).thenReturn(expected);
        ContinuumSolver actual = query.solver(node, content, so);

        assertSame(expected, actual);
    }


}