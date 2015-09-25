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

package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.cells.BehaviorCell;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.control.halt.*;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.processes.*;
import nanoverse.runtime.processes.discrete.cluster.ScatterClustersHelper;
import nanoverse.runtime.processes.gillespie.GillespieState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.*;
import java.util.stream.Collectors;

public class ScatterClusters extends CellProcess {

    private final IntegerArgument neighborCount;
    private final CellDescriptor cellDescriptor;
    private final ScatterClustersHelper clustersHelper;
    private List<Coordinate> candidates;

    @FactoryTarget
    public ScatterClusters(BaseProcessArguments arguments,
                           CellProcessArguments cpArguments,
                           IntegerArgument neighborCount,
                           CellDescriptor cellDescriptor,
                           ScatterClustersHelper clustersHelper) {

        super(arguments, cpArguments);
        this.cellDescriptor = cellDescriptor;
        this.neighborCount = neighborCount;
        this.clustersHelper = clustersHelper;
    }

    public void target(GillespieState gs) throws HaltCondition {
        // Construct initial set of candidates

        candidates = getActiveSites().stream()
            .filter(c -> !getLayer()
                .getViewer()
                .isOccupied(c))
            .collect(Collectors.toList());

        if (gs != null) {
            gs.add(this.getID(), candidates.size(), candidates.size() * 1.0D);
        }
    }

    public void fire(StepState state) throws HaltCondition {
        Collections.shuffle(candidates);
        int placed = 0;
        int n = getCeiling();
        int m = getNeighborCount();

        Iterator<Coordinate> cIter = candidates.iterator();
        BehaviorCell toPlace = cellDescriptor.next();

        while (placed < n) {
            if (!cIter.hasNext()) {
                throw new LatticeFullEvent();
            }

            // Get next candidate coordinate.
            Coordinate current = cIter.next();

            // Place cell at this site, if it is acceptable
            int curPlaced = clustersHelper.attemptPlacement(current, toPlace, m);
            if (curPlaced > 0) {
                placed += curPlaced;
                toPlace = cellDescriptor.next();
            }
        }

//        System.out.println("Total nanoverse.runtime.cells placed: " + placed);
//        System.out.println("Total system population: " + getLayer().getViewer().getOccupiedSites().size());

    }

    @Override
    public void init() {
        candidates = null;
    }

    private int getCeiling() {
        int n;

        try {
            n = getMaxTargets().next();
            if (n < 0) {
                throw new IllegalArgumentException("Scatter cluster process requires >= 0 max targets.");
            }

            return n;
        } catch (HaltCondition ex) {
            throw new RuntimeException("Unexpected halt condition", ex);
        }
    }

    public int getNeighborCount() {
        int m;

        try {
            m = neighborCount.next();
        } catch (HaltCondition ex) {
            throw new RuntimeException("Unexpected halt condition", ex);
        }

        return m;
    }

    @Override
    public int hashCode() {
        return cellDescriptor != null ? cellDescriptor.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScatterClusters scatter = (ScatterClusters) o;

        if (cellDescriptor != null ? !cellDescriptor.equals(scatter.cellDescriptor) : scatter.cellDescriptor != null)
            return false;

        if (getActiveSites() != null ? !getActiveSites().equals(scatter.getActiveSites()) : scatter.getActiveSites() != null)
            return false;

        if (getMaxTargets() != null ? !getMaxTargets().equals(scatter.getMaxTargets()) : scatter.getMaxTargets() != null)
            return false;

        return true;
    }
}
