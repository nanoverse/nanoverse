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

package nanoverse.runtime.geometry.lattice;

import nanoverse.runtime.control.identifiers.Coordinate;

public class MockLattice extends Lattice {

    private Coordinate zeroVector;
    private int connectivity;
    private int dimensionality;

    @Override
    protected void defineBasis() {
        // TODO Auto-generated method stub

    }

    @Override
    public int getConnectivity() {
        return connectivity;
    }

    public void setConnectivity(int c) {
        connectivity = c;
    }

    @Override
    public int getDimensionality() {
        return dimensionality;
    }

    public void setDimensionality(int d) {
        dimensionality = d;
    }

    @Override
    public Coordinate adjust(Coordinate toAdjust) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Coordinate invAdjust(Coordinate toAdjust) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Coordinate[] getAnnulus(Coordinate coord, int r) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Coordinate getDisplacement(Coordinate pCoord, Coordinate qCoord) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Coordinate getOrthoDisplacement(Coordinate pCoord, Coordinate qCoord) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Coordinate rel2abs(Coordinate coord, Coordinate displacement) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Lattice clone() {
        return new MockLattice();
    }

    @Override
    public Coordinate getZeroVector() {
        return zeroVector;
    }

    public void setZeroVector(Coordinate zeroVector) {
        this.zeroVector = zeroVector;
    }
}
