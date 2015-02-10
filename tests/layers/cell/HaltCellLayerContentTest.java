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

package layers.cell;//import junit.framework.TestCase;

import cells.Cell;
import cells.MockCell;
import control.halt.BoundaryReachedEvent;
import control.identifiers.Coordinate;
import control.identifiers.Flags;
import geometry.Geometry;
import geometry.boundaries.Arena;
import geometry.boundaries.Boundary;
import geometry.lattice.Lattice;
import geometry.lattice.LinearLattice;
import geometry.shape.Line;
import geometry.shape.Shape;
import test.EslimeTestCase;

public class HaltCellLayerContentTest extends EslimeTestCase {

    private HaltCellLayerContent query;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, 5);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geometry = new Geometry(lattice, shape, boundary);
        CellLayerIndices indices = new CellLayerIndices();
        query = new HaltCellLayerContent(geometry, indices);
    }

    public void testPutInBounds() throws Exception {
        MockCell cell = new MockCell(1);
        Coordinate c = new Coordinate(0, 0, 0);
        query.put(c, cell);
        Cell actual = query.get(c);
        assertTrue(actual == cell);
    }

    public void testPutOutOfBounds() throws Exception {
        MockCell cell = new MockCell(1);
        Coordinate c = new Coordinate(-1, 0, Flags.END_OF_WORLD);

        boolean thrown = false;
        try {
            query.put(c, cell);
        } catch (BoundaryReachedEvent ex) {
            thrown = true;
        }

        assertTrue(thrown);
    }
}