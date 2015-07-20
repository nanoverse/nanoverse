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

package processes.discrete;

import cells.BehaviorCell;
import control.arguments.*;
import control.halt.*;
import control.identifiers.Coordinate;
import processes.*;
import processes.discrete.cluster.ScatterClustersHelper;
import processes.gillespie.GillespieState;
import structural.RangeMap;

import java.util.*;
import java.util.stream.*;

public class PowerScatter extends CellProcess {

    private List<Coordinate> candidates;
    private final CellDescriptor cellDescriptor;
    private final ScatterClustersHelper helper;
    private final RangeMap<Integer> neighborChooser;

    public PowerScatter(BaseProcessArguments arguments,
                        CellProcessArguments cpArguments,
                        CellDescriptor cellDescriptor,
                        ScatterClustersHelper helper) {

        super(arguments, cpArguments);
        this.cellDescriptor = cellDescriptor;
        this.helper = helper;
        neighborChooser = makeNeighborChooser();
    }

    private RangeMap<Integer> makeNeighborChooser() {
        int maxNeighbors = getLayer().getGeometry().getConnectivity() * 2;
        RangeMap<Integer> ret = new RangeMap<>(maxNeighbors);
        IntStream.range(0, maxNeighbors).forEach(i -> {
            double weight = 1.0 / (i + 1.0);
            ret.add(i, weight);
        });
        return ret;
    }

    @Override
    public void init() {
        candidates = null;
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

    private int getFloor() {
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

    public void fire(StepState state) throws HaltCondition {

        Collections.shuffle(candidates);
        int ttlPlaced = 0;
        int n = getFloor();

        Iterator<Coordinate> cIter = candidates.iterator();
        BehaviorCell toPlace = cellDescriptor.next();

        while (ttlPlaced < n) {
            if (!cIter.hasNext()) {
                throw new LatticeFullEvent();
            }

            int m = chooseMinNeighbors();
            // Get next candidate coordinate.
            Coordinate current = cIter.next();

            // Place cell at this site, if it is acceptable
            int placed = helper.attemptPlacement(current, toPlace, m);

            if (placed > 0) {
                ttlPlaced += placed;
                toPlace = cellDescriptor.next();
            }
        }

    }

    private int chooseMinNeighbors() {
        double x = getGeneralParameters()
                .getRandom()
                .nextDouble() * neighborChooser.getTotalWeight();

        return neighborChooser.selectTarget(x);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PowerScatter scatter = (PowerScatter) o;

        if (cellDescriptor != null ? !cellDescriptor.equals(scatter.cellDescriptor) : scatter.cellDescriptor != null)
            return false;

        if (getActiveSites() != null ? !getActiveSites().equals(scatter.getActiveSites()) : scatter.getActiveSites() != null)
            return false;

        if (getMaxTargets() != null ? !getMaxTargets().equals(scatter.getMaxTargets()) : scatter.getMaxTargets() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return cellDescriptor != null ? cellDescriptor.hashCode() : 0;
    }
}
