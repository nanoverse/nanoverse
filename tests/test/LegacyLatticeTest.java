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
import geometry.MockGeometry;
import layers.MockLayerManager;
import layers.cell.CellLayer;
import org.junit.Before;

/**
 * Convenience class providing automatic mock infrastructure
 * for tests that require a cell layer. This includes all
 * cells and cell processes, for example.
 * <p>
 * Created by dbborens on 2/21/14.
 */
public abstract class LegacyLatticeTest extends LegacyTest {

    protected MockGeometry geom;
    protected MockLayerManager layerManager;
    protected CellLayer cellLayer;
    protected Coordinate origin, x, y, z, yz;
    protected Coordinate[] cc;

    @Before
    public void setUp() throws Exception {
        geom = buildMockGeometry();
        layerManager = new MockLayerManager();
        cellLayer = new CellLayer(geom);
        layerManager.setCellLayer(cellLayer);

        cc = geom.getCanonicalSites();

        // I should really sort these out
        origin = cc[0];
        x = cc[4];
        y = cc[2];
        z = cc[1];
        yz = cc[3];

    }
}
