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

package nanoverse.runtime.geometry;

import nanoverse.runtime.control.identifiers.*;

import java.util.*;

public class MockGeometry extends Geometry {

	/* infinite */

    protected Coordinate[] canonicalSites;
    private boolean infinite;
    private HashMap<Coordinate, Coordinate[]> cellNeighbors = new HashMap<Coordinate, Coordinate[]>();
    private int connectivity;

    /* canonicalSites */
    private int dimensionality;
    private Coordinate center;
    private boolean reportEquals;
    private double lastRequestedScale = 0;


    private Class[] componentClasses;

    public MockGeometry() {
        super(null, null, null);

        componentClasses = new Class[]{
            Object.class,
            Object.class,
            Object.class
        };
    }

    @Override
    public Coordinate[] getCanonicalSites() {
        return canonicalSites;
    }

    public void setCanonicalSites(Coordinate[] canonicalSites) {
        // Note that the MockGeometry override of setCanonicalSites
        // does not canonicalize coordinates -- in particular, 2D
        // coordinates will not receive the PLANAR flag.
        this.canonicalSites = canonicalSites;
        rebuildIndex();
    }

    @Override
    public Coordinate[] getNeighbors(Coordinate coord, int mode) {
        return cellNeighbors.get(coord);
    }

    /* rel2abs -- overriden as needed in subclass mocks */
    public Coordinate rel2abs(Coordinate origin, Coordinate displacement, int mode) {
        return origin.addFlags(Flags.UNDEFINED);
    }

    public int getDimensionality() {
        return dimensionality;
    }

    public int getConnectivity() {
        return connectivity;
    }

    @Override
    public boolean isInfinite() {
        return infinite;
    }

    public void setInfinite(boolean infinite) {
        this.infinite = infinite;
    }

    public Coordinate getCenter() {
        return center;
    }

    public void setCenter(Coordinate center) {
        this.center = center;
    }

    @Override
    public boolean equals(Object obj) {
        return reportEquals;
    }

    @Override
    public Geometry cloneAtScale(double rangeScale) {
        lastRequestedScale = rangeScale;
        return null;
    }

    @Override
    public Class[] getComponentClasses() {
        return componentClasses;
    }

    public void setComponentClasses(Class[] componentClasses) {
        this.componentClasses = componentClasses;
    }

    public void setConnectivity(int connectivity) {
        this.connectivity = connectivity;
    }

    public void setDimensionality(int dimensionality) {
        this.dimensionality = dimensionality;
    }

    public void setAgentNeighbors(Coordinate coord, Coordinate[] neighbors) {
        cellNeighbors.put(coord, neighbors);
    }

    protected void consider(ArrayList<Coordinate> neighbors, int x, int y) {
        Coordinate candidate = new Coordinate2D(x, y, 0);

        for (int i = 0; i < canonicalSites.length; i++) {
            if (canonicalSites[i].equals(candidate)) {
                neighbors.add(candidate);
                return;
            }
        }

    }

    protected void consider(ArrayList<Coordinate> neighbors, int x, int y, int z) {
        Coordinate candidate = new Coordinate3D(x, y, z, 0);

        for (int i = 0; i < canonicalSites.length; i++) {
            if (canonicalSites[i].equals(candidate)) {
                neighbors.add(candidate);
                return;
            }
        }
    }

    /**
     * Causes the equality operator of this mock object to return
     * the specified value in all cases.
     */
    public void setEquals(boolean reportEquals) {
        this.reportEquals = reportEquals;
    }

    public double getLastRequestedScale() {
        return lastRequestedScale;
    }

}
