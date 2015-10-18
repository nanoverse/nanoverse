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

package test;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.set.*;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.*;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.runtime.processes.discrete.AgentProcessArguments;

import java.util.stream.Stream;

import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 6/11/2015.
 */
public class AgentProcessTestBase extends TestBase {
    protected Coordinate a, b, c, d;
    protected BaseProcessArguments arguments;
    protected AgentProcessArguments cpArguments;
    protected AgentLayer layer;
    protected AgentLayerViewer viewer;
    protected AgentLookupManager lookup;
    protected AgentUpdateManager update;
    protected LayerManager lm;

    protected void setup() {
        arguments = mock(BaseProcessArguments.class);
        cpArguments = mock(AgentProcessArguments.class);
        makeCoordinates();
        layer = mock(AgentLayer.class);
        viewer = mock(AgentLayerViewer.class);
        lookup = mock(AgentLookupManager.class);
        update = mock(AgentUpdateManager.class);
        lm = mock(LayerManager.class);
        when(arguments.getLayerManager()).thenReturn(lm);
        when(lm.getAgentLayer()).thenReturn(layer);
        when(layer.getViewer()).thenReturn(viewer);
        when(layer.getUpdateManager()).thenReturn(update);
        when(layer.getLookupManager()).thenReturn(lookup);

    }

    private void makeCoordinates() {
        a = mock(Coordinate.class);
        b = mock(Coordinate.class);
        c = mock(Coordinate.class);
        d = mock(Coordinate.class);
    }

    protected void makeActiveSites(Coordinate... sites) {
        CoordinateSet set = new CustomSet();
        Stream.of(sites).forEach(set::add);
        when(cpArguments.getActiveSites()).thenReturn(set);

    }
}