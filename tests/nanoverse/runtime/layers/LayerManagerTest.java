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

import nanoverse.runtime.layers.cell.CellLayer;
import nanoverse.runtime.layers.continuum.ContinuumAgentLinker;
import nanoverse.runtime.layers.continuum.ContinuumLayer;
import org.junit.Before;
import org.junit.Test;
import nanoverse.runtime.processes.StepState;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by David B Borenstein on 12/30/13.
 */
public class LayerManagerTest {

    private LayerManager query;
    private ContinuumLayer continuumLayer;
    private CellLayer cellLayer;
    private String id;

    @Before
    public void init() {
        ContinuumAgentLinker linker = mock(ContinuumAgentLinker.class);
        id = "test";

        continuumLayer = mock(ContinuumLayer.class);
        when(continuumLayer.getLinker()).thenReturn(linker);
        when(continuumLayer.getId()).thenReturn(id);

        cellLayer = mock(CellLayer.class);
        query = new LayerManager();
    }

    @Test
    public void addContinuumLayer() throws Exception {
        query.addContinuumLayer(continuumLayer);
        assertEquals(continuumLayer, query.getContinuumLayer(id));
    }

    @Test
    public void resetClearsCellLayer() throws Exception {
        query.setCellLayer(cellLayer);
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
        query.setCellLayer(cellLayer);
        assertEquals(cellLayer, query.getCellLayer());
    }
}
