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

package layers.cell;

import cells.MockCell;
import control.identifiers.Coordinate;
import geometry.*;
import geometry.boundaries.HaltArena;
import org.junit.*;
import test.EslimeTestCase;

import static org.junit.Assert.*;
/**
 * Created by dbborens on 4/25/14.
 */
public class CellLayerTest extends EslimeTestCase {
    private MockGeometry geom;

    @Before
    public void setUp() throws Exception {
        geom = buildMockGeometry();
    }

    @Test
    public void testClone() {
        CellLayer query = new CellLayer(geom);
        CellLayer clone = query.clone();
        assertEquals(query, clone);
        assertFalse(query == clone);
    }

    @Test
    public void testHaltBoundaryCase() {
        Class[] componentClasses = new Class[]{
                Object.class,
                Object.class,
                HaltArena.class
        };

        geom.setComponentClasses(componentClasses);
        ExposedCellLayer query = new ExposedCellLayer(geom);

        assertEquals(HaltCellLayerContent.class, query.getContent().getClass());
    }

    @Test
    public void testFiniteBoundaryCase() {
        geom.setInfinite(false);
        ExposedCellLayer query = new ExposedCellLayer(geom);
        assertEquals(FiniteCellLayerContent.class, query.getContent().getClass());
    }

    @Test
    public void testInfiniteBoundaryCase() {
        geom.setInfinite(true);
        ExposedCellLayer query = new ExposedCellLayer(geom);
        assertEquals(InfiniteCellLayerContent.class, query.getContent().getClass());
    }

    @Test
    public void testReset() throws Exception {
        // Put some stuff on the lattice.
        ExposedCellLayer query = new ExposedCellLayer(geom);
        for (int i = 0; i < geom.getCanonicalSites().length; i++) {
            Coordinate c = geom.getCanonicalSites()[i];
            query.getUpdateManager().place(new MockCell(i), c);
        }

        // Verify that the lattice is filled up.
        assertEquals(geom.getCanonicalSites().length, query.getViewer().getOccupiedSites().size());

        // Reset the lattice.
        query.reset();

        // Make sure that the lattice is reset to square one.
        assertEquals(0, query.getViewer().getOccupiedSites().size());
        for (Coordinate c : geom.getCanonicalSites()) {
            assertFalse(query.getViewer().isOccupied(c));
        }
    }

    private class ExposedCellLayer extends CellLayer {

        public ExposedCellLayer(Geometry geom) {
            super(geom);
        }

        public CellLayerContent getContent() {
            return content;
        }
    }
}
