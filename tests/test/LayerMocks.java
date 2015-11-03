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

package test;

import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.*;
import org.junit.Before;

import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/18/2015.
 */
public abstract class LayerMocks extends TestBase {

    protected LayerManager layerManager;
    protected AgentLayer agentLayer;
    protected AgentLookupManager lookup;
    protected AgentUpdateManager update;
    protected AgentLayerViewer viewer;
    protected Geometry geometry;

    public void verifyNothingHappened() throws Exception {
        verifyNoMoreInteractions(layerManager, agentLayer, lookup,
                update, viewer, geometry);
    }

    @Before
    public void before() throws Exception {
        layerManager = mock(LayerManager.class);

        agentLayer = mock(AgentLayer.class);
        when(layerManager.getAgentLayer()).thenReturn(agentLayer);

        lookup = mock(AgentLookupManager.class);
        when(agentLayer.getLookupManager()).thenReturn(lookup);

        update = mock(AgentUpdateManager.class);
        when(agentLayer.getUpdateManager()).thenReturn(update);

        viewer = mock(AgentLayerViewer.class);
        when(agentLayer.getViewer()).thenReturn(viewer);

        geometry = mock(Geometry.class);
        when(agentLayer.getGeometry()).thenReturn(geometry);
    }
}
