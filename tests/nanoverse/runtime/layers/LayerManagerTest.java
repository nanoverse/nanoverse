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

package nanoverse.runtime.layers;

import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.layers.continuum.*;
import nanoverse.runtime.processes.StepState;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by David B Borenstein on 12/30/13.
 */
public class LayerManagerTest {

    private LayerManager query;
    private ContinuumLayer continuumLayer;
    private AgentLayer cellLayer;
    private String id;

    @Before
    public void init() {
        ContinuumAgentLinker linker = mock(ContinuumAgentLinker.class);
        id = "test";

        continuumLayer = mock(ContinuumLayer.class);
        when(continuumLayer.getLinker()).thenReturn(linker);
        when(continuumLayer.getId()).thenReturn(id);

        cellLayer = mock(AgentLayer.class);
        query = new LayerManager();
    }

    @Test
    public void addContinuumLayer() throws Exception {
        query.addContinuumLayer(continuumLayer);
        assertEquals(continuumLayer, query.getContinuumLayer(id));
    }

    @Test
    public void resetClearsAgentLayer() throws Exception {
        query.setAgentLayer(cellLayer);
        query.reset();
        verify(cellLayer).reset();
    }

    @Test
    public void resetClearsContinuumLayer() throws Exception {
        query.addContinuumLayer(continuumLayer);
        query.reset();
        verify(continuumLayer).reset();
    }

    @Test
    public void stepState() throws Exception {
        StepState stepState = mock(StepState.class);
        query.setStepState(stepState);
        assertEquals(stepState, query.getStepState());
    }

    @Test
    public void cellLayer() throws Exception {
        query.setAgentLayer(cellLayer);
        assertEquals(cellLayer, query.getAgentLayer());
    }
}
