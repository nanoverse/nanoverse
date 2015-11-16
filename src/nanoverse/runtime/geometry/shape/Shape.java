/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package nanoverse.runtime.geometry.shape;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.lattice.Lattice;

import java.util.*;

public abstract class Shape {

    // The set of sites in this nanoverse.runtime.geometry, given the lattice type
    protected Coordinate[] canonicalSites;

    // A reverse go of coordinate to index into canonicalSites
    protected Map<Coordinate, Integer> coordMap;

    protected Lattice lattice;

    public Shape(Lattice lattice) {
        verify(lattice);
        this.lattice = lattice;

    }

    protected abstract void verify(Lattice lattice);

    /**
     * Initialize general data structures. Should be invoked by a Shape
     * subclass after local variables have been assigned.
     */
    protected void init() {
        canonicalSites = calcSites();
        buildCoordMap();
    }

	
	/* PUBLIC METHODS */

    /* PROTECTED METHODS */
    protected abstract Coordinate[] calcSites();

    private void buildCoordMap() {

        coordMap = new HashMap<>();
        for (Integer i = 0; i < canonicalSites.length; i++) {
            coordMap.put(canonicalSites[i], i);
        }
    }

    // Get a complete list of sites for this geometry.
    public Coordinate[] getCanonicalSites() {
        return canonicalSites.clone();
    }

    /**
     * Returns two coordinates representing the limits of the nanoverse.runtime.geometry.
     * <p>
     * The coordinates are given as minimum and maximum displacements,
     * in terms of the basis vectors, from the center coordinate.
     */
    //public abstract Coordinate[] getLimits();
    public abstract Coordinate getCenter();

    public abstract Coordinate[] getBoundaries();

    /**
     * Returns a coordinate vector, in the basis of the nanoverse.runtime.geometry, of how far
     * over the boundary a particular point is.
     *
     * @param coord
     * @return
     */
    public abstract Coordinate getOverbounds(Coordinate coord);

    public abstract int[] getDimensions();

    /**
     * In order for shapes to be equal, they must be of the
     * same class and have the same dimensions. They do not
     * need to be associated with the same lattice nanoverse.runtime.geometry
     * or have the same boundary conditions in order to be
     * considered equal.
     *
     * @param obj
     * @return
     */
    @Override
    public abstract boolean equals(Object obj);

    public abstract Shape cloneAtScale(Lattice clonedLattice, double rangeScale);
}
