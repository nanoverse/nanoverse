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

package nanoverse.runtime.geometry.shape;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.lattice.Lattice;

public class MockShape extends Shape {

    // We want to be able to declare mock objects "equal"
    private boolean equal;

    public MockShape() {
        super(null);
    }

    @Override
    protected void verify(Lattice lattice) {

    }

    @Override
    public Coordinate[] getCanonicalSites() {
        return null;
    }

    @Override
    public Coordinate getCenter() {
        return null;
    }

    @Override
    public Coordinate[] getBoundaries() {
        return null;
    }

    @Override
    public Coordinate getOverbounds(Coordinate coord) {
        return null;
    }

    @Override
    protected Coordinate[] calcSites() {
        return null;
    }

    @Override
    public int[] getDimensions() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return equal;
    }

    @Override
    public Shape cloneAtScale(Lattice clonedLattice, double rangeScale) {
        return new MockShape();
    }

    public void setEqual(boolean equal) {
        this.equal = equal;
    }
}
