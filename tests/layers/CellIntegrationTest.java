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

package layers;

import cells.BehaviorCell;
import cells.Cell;
import cells.MockCell;
import control.identifiers.Coordinate;
import geometry.Geometry;
import geometry.boundaries.Boundary;
import geometry.boundaries.PlaneRingHard;
import geometry.lattice.Lattice;
import geometry.lattice.TriangularLattice;
import geometry.shape.Rectangle;
import geometry.shape.Shape;
import layers.cell.CellLayer;
import layers.cell.StateMapViewer;
import test.EslimeTestCase;

import java.util.HashSet;

/**
 * Integration tests for the CellLayer object and
 * related helper objects. Helper objects are tested
 * separately.
 *
 * @author David Bruce Borenstein
 * @untested
 */
public class CellIntegrationTest extends EslimeTestCase {

    public void testConstructor() {
        //HexRing geom = new HexRing(6, 6);
        Lattice lattice = new TriangularLattice();
        Shape shape = new Rectangle(lattice, 6, 6);
        Boundary boundary = new PlaneRingHard(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);

        try {
            new CellLayer(geom);
        } catch (Exception ex) {
            fail();
        }
    }

    public void testInterrogate() throws Exception {
        //HexRing geom = new HexRing(6, 6);
        Lattice lattice = new TriangularLattice();
        Shape shape = new Rectangle(lattice, 6, 6);
        Boundary boundary = new PlaneRingHard(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);

        CellLayer layer = new CellLayer(geom);

        // Set up a cell
        Cell toPlace = new MockCell(1);

        Coordinate coord = new Coordinate(2, 3, 0);

        layer.getUpdateManager().place(toPlace, coord);

        // Get its properties through the lattice.
        // TODO This should be replaced with epsilon equality
        assertEquals(toPlace.getHealth(), layer.getViewer().getCell(coord).getHealth(), epsilon);
        assertEquals(toPlace.getState(), layer.getViewer().getCell(coord).getState());
    }

    public void testFeed() throws Exception {
        //HexRing geom = new HexRing(6, 6);
        Lattice lattice = new TriangularLattice();
        Shape shape = new Rectangle(lattice, 6, 6);
        Boundary boundary = new PlaneRingHard(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        CellLayer layer = new CellLayer(geom);
        MockLayerManager lm = new MockLayerManager();
        lm.setCellLayer(layer);
        Cell toPlace = new BehaviorCell(lm, 1, 0.5, 1.0, null);
        Coordinate coord = new Coordinate(2, 3, 0);

        layer.getUpdateManager().place(toPlace, coord);

        assertEquals(layer.getViewer().getCell(coord).getHealth(), 0.5);
        assertTrue(!layer.getViewer().getDivisibleSites().contains(coord));

        layer.getViewer().getCell(coord).adjustHealth(1.0);

        assertEquals(layer.getViewer().getCell(coord).getHealth(), 1.5);
        assertTrue(layer.getViewer().getDivisibleSites().contains(coord));
    }

    public void testNeighborStates() throws Exception {
        //HexRing geom = new HexRing(6, 6);
        Lattice lattice = new TriangularLattice();
        Shape shape = new Rectangle(lattice, 6, 6);
        Boundary boundary = new PlaneRingHard(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        CellLayer layer = new CellLayer(geom);

        // Set up one cell
        Cell toPlace = new MockCell(1);
        Coordinate coord = new Coordinate(2, 3, 0);
        layer.getUpdateManager().place(toPlace, coord);

        // All neighbors should be vacant
        Coordinate[] neighbors = layer.getLookupManager().getNearestVacancies(coord, -1);
        assertEquals(neighbors.length, 6);

        // Add an occupied neighbor
        Coordinate coordAbove = new Coordinate(3, 3, 0);
        layer.getUpdateManager().place(new MockCell(2), coordAbove);

        // Check that the right cell is placed
        assertFalse(layer.getViewer().getCell(coord).getState() == layer.getViewer().getCell(coordAbove).getState());

        // Check neighborhood
        assertEquals(5, layer.getLookupManager().getNearestVacancies(coord, -1).length);
        assertEquals(5, layer.getLookupManager().getNearestVacancies(coordAbove, -1).length);

        // Add a cell at adjacent to southern boundary
        Coordinate south = new Coordinate(2, 1, 0);
        layer.getUpdateManager().place(new MockCell(1), south);

        // Should be short one vacant neighbor (hard BCs for cells)
        assertEquals(5, layer.getLookupManager().getNearestVacancies(south, -1).length);

        // Add a cell at origin (should be just like south)
        Coordinate origin = new Coordinate(0, 0, 0);
        layer.getUpdateManager().place(new MockCell(1), origin);

        // Should be short one vacant neighbor (hard BCs for cells)
        assertEquals(5, layer.getLookupManager().getNearestVacancies(origin, -1).length);
    }

    public void testVacancyModel() throws Exception {
        //HexRing geom = new HexRing(6, 6);
        Lattice lattice = new TriangularLattice();
        Shape shape = new Rectangle(lattice, 6, 6);
        Boundary boundary = new PlaneRingHard(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        CellLayer layer = new CellLayer(geom);

        // Set up one cell
        Cell toPlace = new MockCell(1);
        Coordinate coord = new Coordinate(2, 3, 0);
        layer.getUpdateManager().place(toPlace, coord);

        // List of vacancies should be canonical neighbors
        Coordinate[] cVec = geom.getNeighbors(coord, Geometry.APPLY_BOUNDARIES);
        Coordinate[] nVec = layer.getLookupManager().getNearestVacancies(coord, -1);
        assertEquals(cVec.length, nVec.length);

        HashSet<Coordinate> cSet = new HashSet<Coordinate>(cVec.length);

        for (int i = 0; i < cVec.length; i++)
            cSet.add(cVec[i]);

        for (int i = 0; i < nVec.length; i++) {
            assertTrue(cSet.contains(nVec[i]));
        }

        // Fill all but one canonical neighbor
        for (int i = 0; i < nVec.length - 1; i++) {
            layer.getUpdateManager().place(new MockCell(100), nVec[i]);
        }

        // List of vacancies should be only remaining canonical neighbor
        assertEquals(1, layer.getLookupManager().getNearestVacancies(coord, -1).length);

        // Fill that one -- should have 12 nearest vacancies now
        layer.getUpdateManager().place(new MockCell(100), nVec[nVec.length - 1]);
        assertEquals(12, layer.getLookupManager().getNearestVacancies(coord, -1).length);

        // Now try getNearestVacancies with a maximum radius of 1--shouldn't have any

        layer.getLookupManager().getNearestVacancies(coord, 1);

        Coordinate[] foo = layer.getLookupManager().getNearestVacancies(coord, 1);

        assertEquals(0, foo.length);
        assertEquals(12, layer.getLookupManager().getNearestVacancies(coord, 2).length);
    }

    public void testNoOverwriteOnPlace() throws Exception {
        //HexRing geom = new HexRing(6, 6);
        Lattice lattice = new TriangularLattice();
        Shape shape = new Rectangle(lattice, 6, 6);
        Boundary boundary = new PlaneRingHard(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        CellLayer layer = new CellLayer(geom);

        // Set up one cell
        Cell toPlace = new MockCell(1);
        Coordinate coord = new Coordinate(2, 3, 0);
        layer.getUpdateManager().place(toPlace, coord);

        Cell second = new MockCell(2);

        boolean thrown = false;
        try {
            layer.getUpdateManager().place(second, coord);
        } catch (Exception ex) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    public void testNoOverwriteOnMove() throws Exception {
        Lattice lattice = new TriangularLattice();
        Shape shape = new Rectangle(lattice, 6, 6);
        Boundary boundary = new PlaneRingHard(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        CellLayer layer = new CellLayer(geom);

        // Set up one cell
        Cell toPlace = new MockCell(1);
        Coordinate coord = new Coordinate(2, 3, 0);
        layer.getUpdateManager().place(toPlace, coord);

        Cell second = new MockCell(2);
        Coordinate sc = new Coordinate(3, 3, 0);
        layer.getUpdateManager().place(second, sc);

        boolean thrown = false;
        try {
            layer.getUpdateManager().move(coord, sc);
        } catch (Exception ex) {
            thrown = true;
        }
        assertTrue(thrown);

    }

    /********************/
    /* FUNCTIONAL TESTS */

    /**
     * ****************
     */

    public void testLatticeFunctionality() throws Exception {
        Lattice lattice = new TriangularLattice();
        Shape shape = new Rectangle(lattice, 6, 6);
        Boundary boundary = new PlaneRingHard(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        CellLayer layer = new CellLayer(geom);

        // There shouldn't be anything in the state map yet.
        assertEquals(0, layer.getViewer().getStateMapViewer().getStates().length);

        // Unoccupied lattice: occupied and divisible sites should be empty, vacant == canonical
        Coordinate[] canonical = geom.getCanonicalSites();

        HashSet<Coordinate> cSet = new HashSet<>(canonical.length);
        for (int i = 0; i < canonical.length; i++)
            cSet.add(canonical[i]);

        assertEquals(0, layer.getViewer().getDivisibleSites().size());
        assertEquals(0, layer.getViewer().getOccupiedSites().size());

        // Place one cell
        MockCell toPlace = new MockCell(1);
        MockCell childCell = new MockCell(1);
        childCell.setDivisible(true);
        toPlace.setDivisible(true);
        toPlace.setChild(childCell);
        Coordinate coord = new Coordinate(2, 3, 0);
        layer.getUpdateManager().place(toPlace, coord);

        // Verify state index integrity
        StateMapViewer v = layer.getViewer().getStateMapViewer();
        assertEquals(1, v.getStates().length);
        assertEquals(1, v.getStates()[0].intValue());
        assertEquals(1, v.getCount(1).intValue());

        // Indices should reflect the placement
        assertEquals(1, layer.getViewer().getOccupiedSites().size());
        assertEquals(1, layer.getViewer().getDivisibleSites().size());

        // Divide cell to a neighboring site
        Coordinate[] targets = layer.getLookupManager().getNearestVacancies(coord, -1);
        Coordinate child = targets[0];

        layer.getUpdateManager().divideTo(coord, child);

        // Verify state index integrity
        v = layer.getViewer().getStateMapViewer();
        assertEquals(1, v.getStates().length);
        assertEquals(1, v.getStates()[0].intValue());
        assertEquals(2, v.getCount(1).intValue());

        // Verify state
        assertEquals(layer.getViewer().getCell(coord).getState(), layer.getViewer().getCell(coord).getState());

        // Indices should reflect the division
        assertEquals(2, layer.getViewer().getOccupiedSites().size());
        assertEquals(2, layer.getViewer().getDivisibleSites().size());

        // Tell only one cell to consider...
        assertEquals(1, layer.getUpdateManager().consider(child));

        // Swap
        layer.getUpdateManager().swap(coord, child);

        // The consider count should be consistent with the swap
        assertEquals(2, layer.getUpdateManager().consider(coord));
        assertEquals(1, layer.getUpdateManager().consider(child));

        // Move one of them
        Coordinate destination = targets[1];
        layer.getUpdateManager().move(coord, destination);
        assertEquals(3, layer.getUpdateManager().consider(destination));

        // Indices should reflect the move
        assertEquals(2, layer.getViewer().getOccupiedSites().size());
        assertEquals(2, layer.getViewer().getDivisibleSites().size());

        // Banish the other one
        layer.getUpdateManager().banish(child);

        // Verify state index integrity
        v = layer.getViewer().getStateMapViewer();
        assertEquals(1, v.getStates().length);
        assertEquals(1, v.getStates()[0].intValue());
        assertEquals(1, v.getCount(1).intValue());

        // Indices should reflect the banishment
        assertEquals(1, layer.getViewer().getOccupiedSites().size());
        assertEquals(1, layer.getViewer().getDivisibleSites().size());

        // Check exact values of indices
        assertEquals(destination, (layer.getViewer().getOccupiedSites().iterator().next()));
        assertEquals(destination, (layer.getViewer().getDivisibleSites().iterator().next()));

        HashSet<Coordinate> oSet = layer.getViewer().getOccupiedSites();
        assertTrue(oSet.contains(destination));
    }
}
