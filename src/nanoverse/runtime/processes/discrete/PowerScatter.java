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

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.arguments.AgentDescriptor;
import nanoverse.runtime.control.halt.*;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.processes.*;
import nanoverse.runtime.processes.discrete.cluster.SeparationStrategyManager;
import nanoverse.runtime.processes.gillespie.GillespieState;
import nanoverse.runtime.structural.RangeMap;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.*;
import java.util.stream.*;

public class PowerScatter extends AgentProcess {

    private final AgentDescriptor cellDescriptor;
    private final SeparationStrategyManager clustersHelper;
    private final RangeMap<Integer> neighborChooser;
    private List<Coordinate> candidates;

    @FactoryTarget
    public PowerScatter(BaseProcessArguments arguments,
                        AgentProcessArguments cpArguments,
                        AgentDescriptor cellDescriptor,
                        SeparationStrategyManager clustersHelper) {

        super(arguments, cpArguments);
        this.cellDescriptor = cellDescriptor;
        this.clustersHelper = clustersHelper;
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
        int ttlPlaced = 0;
        int n = getFloor();

        Iterator<Coordinate> cIter = candidates.iterator();
        Agent toPlace = cellDescriptor.next();

        while (ttlPlaced < n) {
            if (!cIter.hasNext()) {
                throw new LatticeFullEvent();
            }

            int m = chooseMinNeighbors();
            // Get next candidate coordinate.
            Coordinate current = cIter.next();

            // Place cell at this site, if it is acceptable
            int placed = clustersHelper.attemptPlacement(current, toPlace, m);

            if (placed > 0) {
                ttlPlaced += placed;
                toPlace = cellDescriptor.next();
            }
        }

    }

    @Override
    public void init() {
        candidates = null;
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

    private int chooseMinNeighbors() {
        double x = getGeneralParameters()
            .getRandom()
            .nextDouble() * neighborChooser.getTotalWeight();

        return neighborChooser.selectTarget(x);
    }

    @Override
    public int hashCode() {
        return cellDescriptor != null ? cellDescriptor.hashCode() : 0;
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
}
