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

import control.identifiers.Coordinate;
import geometry.set.*;
import layers.LayerManager;
import layers.cell.*;
import processes.BaseProcessArguments;
import processes.discrete.CellProcessArguments;

import java.util.stream.Stream;

import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 6/11/2015.
 */
public class CellProcessTestBase extends TestBase {
    protected Coordinate a, b, c, d;
    protected BaseProcessArguments arguments;
    protected CellProcessArguments cpArguments;
    protected CellLayer layer;
    protected CellLayerViewer viewer;
    protected CellLookupManager lookup;
    protected CellUpdateManager update;
    protected LayerManager lm;

    protected void setup() {
        arguments = mock(BaseProcessArguments.class);
        cpArguments = mock(CellProcessArguments.class);
        makeCoordinates();
        layer = mock(CellLayer.class);
        viewer = mock(CellLayerViewer.class);
        lookup = mock(CellLookupManager.class);
        update = mock(CellUpdateManager.class);
        lm = mock(LayerManager.class);
        when(arguments.getLayerManager()).thenReturn(lm);
        when(lm.getCellLayer()).thenReturn(layer);
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
