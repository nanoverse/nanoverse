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

package nanoverse.runtime.layers.cell;

import nanoverse.runtime.cells.Cell;
import nanoverse.runtime.control.halt.BoundaryReachedEvent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.structural.CanonicalCellMap;

import java.util.*;

/**
 * @author David Bruce Borenstein
 */
public abstract class CellLayerContent {

    // Mapping of sites to cell pointers -- the lattice itself.
    protected CanonicalCellMap map;

    // All sites (basically the keyset for the lattice map).
    protected Set<Coordinate> canonicalSites;

    protected Geometry geom;
    protected CellLayerIndices indices;

    public CellLayerContent(Geometry geom, CellLayerIndices indices) {
        // Assign nanoverse.runtime.geometry.
        this.geom = geom;

        this.indices = indices;

        // Get canonical site list.
        canonicalSites = new HashSet<>(geom.getCanonicalSites().length);

        Coordinate[] cc = geom.getCanonicalSites();
        // Initialize map.
        map = new CanonicalCellMap();
        for (int i = 0; i < cc.length; i++) {
            Coordinate coord = cc[i];

            // Initialize each site to null (empty)
            map.put(coord, null);
            canonicalSites.add(coord);
        }
    }

    public Set<Coordinate> getOccupiedSites() {
        return indices.getOccupiedSites();
    }

    public Set<Coordinate> getDivisibleSites() {
        return indices.getDivisibleSites();
    }

    public boolean has(Coordinate coord) {
        return (get(coord) != null);
    }

    public Cell get(Coordinate coord) {

        // Get pointer to cell and return it
        Cell res = map.get(coord);

        return res;
    }

    public void put(Coordinate coord, Cell current) throws BoundaryReachedEvent {
        Cell previous = map.get(coord);
        indices.refresh(coord, previous, current);
        map.put(coord, current);
    }

    public void remove(Coordinate coord) {
        Cell previous = map.get(coord);
        indices.refresh(coord, previous, null);
        map.put(coord, null);
    }

    public int[] getStateVector() {
        Coordinate[] cArr = getCanonicalSites();

        int[] sArr = new int[cArr.length];

        for (int i = 0; i < cArr.length; i++) {
            Cell c = map.get(cArr[i]);
            if (c == null) {
                sArr[i] = 0;
            } else {
                sArr[i] = map.get(cArr[i]).getState();
            }
        }

        return sArr;
    }

    /**
     * Returns a vector containing the canonical coordinate of each
     * site on the lattice.
     *
     * @return
     */
    public Coordinate[] getCanonicalSites() {
        // Construct a copy of internal state
        Coordinate[] res = geom.getCanonicalSites();

        // Return it
        return res;
    }

    /**
     * Returns true iff the specified site has a canonical form
     * in this nanoverse.runtime.geometry.
     */
    public boolean hasCanonicalForm(Coordinate coord) {
        Coordinate canonical = coord.canonicalize();
        boolean has = canonicalSites.contains(canonical);
        return has;
    }

    /**
     * Returns the health vector, in canonical site order.
     */
    public double[] getHealthVector() {
        Coordinate[] cArr = getCanonicalSites();

        double[] fArr = new double[cArr.length];

        for (int i = 0; i < cArr.length; i++) {
            Cell c = map.get(cArr[i]);
            if (c == null) {
                fArr[i] = 0D;
            } else {
                fArr[i] = map.get(cArr[i]).getHealth();
            }
        }

        return fArr;
    }

    public abstract void sanityCheck(Coordinate coord);

    public abstract Set<Coordinate> getImaginarySites();

    public Coordinate locate(Cell cell) {
        return indices.locate(cell);
    }

    public Map<Integer, Integer> getStateMap() {
        return indices.getStateMap();
    }

    public boolean isIndexed(Cell cell) {
        return indices.isIndexed(cell);
    }

    @Override
    public int hashCode() {
        int result = map != null ? map.hashCode() : 0;
        result = 31 * result + (canonicalSites != null ? canonicalSites.hashCode() : 0);
        result = 31 * result + (geom != null ? geom.hashCode() : 0);
        result = 31 * result + (indices != null ? indices.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CellLayerContent that = (CellLayerContent) o;

        if (canonicalSites != null ? !canonicalSites.equals(that.canonicalSites) : that.canonicalSites != null)
            return false;
        if (geom != null ? !geom.equals(that.geom) : that.geom != null)
            return false;
        if (indices != null ? !indices.equals(that.indices) : that.indices != null)
            return false;
        if (map != null ? !map.equals(that.map) : that.map != null)
            return false;

        return true;
    }

    @Override
    public abstract CellLayerContent clone();
}
