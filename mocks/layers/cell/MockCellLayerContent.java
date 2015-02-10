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

import cells.Cell;
import control.identifiers.Coordinate;
import geometry.Geometry;

import java.util.HashSet;
import java.util.Set;

public class MockCellLayerContent extends CellLayerContent {

    private Set<Coordinate> imaginarySites;
    private int[] stateVector;
    private double[] healthVector;

    public MockCellLayerContent(Geometry geom, CellLayerIndices indices) {
        super(geom, indices);
    }

    @Override
    public void sanityCheck(Coordinate coord) {

    }

    @Override
    public Set<Coordinate> getImaginarySites() {
        return imaginarySites;
    }

	/* stateVector */

    private void setImaginarySites(Set<Coordinate> imaginarySites) {
        this.imaginarySites = imaginarySites;
    }

    public Cell get(Coordinate coord) {
        // Mock getter doesn't do any validation
        return map.get(coord);
    }

    @Override
    public int[] getStateVector() {
        return stateVector;
    }

	/* healthVector */

    public void setStateVector(int[] stateVector) {
        this.stateVector = stateVector;
    }

    @Override
    public double[] getHealthVector() {
        return healthVector;
    }

    public void setHealthVector(double[] healthVector) {
        this.healthVector = healthVector;
    }

    @Override
    public CellLayerContent clone() {
        MockCellLayerContent clone = new MockCellLayerContent(geom, indices);
        clone.imaginarySites = new HashSet<>(imaginarySites);
        clone.stateVector = stateVector.clone();
        clone.healthVector = healthVector.clone();
        return clone;
    }
}
