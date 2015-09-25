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

import nanoverse.runtime.cells.Cell;
import nanoverse.runtime.control.arguments.CellDescriptor;
import nanoverse.runtime.control.halt.*;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.processes.*;
import nanoverse.runtime.processes.gillespie.GillespieState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.HashSet;

public class Scatter extends CellProcess {

    private HashSet<Coordinate> candidates;
    private CellDescriptor cellDescriptor;

    @FactoryTarget
    public Scatter(BaseProcessArguments arguments, CellProcessArguments cpArguments, CellDescriptor cellDescriptor) {
        super(arguments, cpArguments);
        this.cellDescriptor = cellDescriptor;
    }

    public void target(GillespieState gs) throws HaltCondition {
        // Construct initial set of candidates
        candidates = new HashSet<>();

        for (Coordinate c : getActiveSites()) {
            if (!getLayer().getViewer().isOccupied(c)) {
                candidates.add(c);
            }
        }
        if (gs != null) {
            gs.add(this.getID(), candidates.size(), candidates.size() * 1.0D);
        }
    }

    public void fire(StepState state) throws HaltCondition {
        System.out.println("Executing Scatter.");
        if (candidates == null) {
            throw new IllegalStateException("fire() invoked on scatter before target().");
        }

        int n = getMaxTargets().next();
        if (n < 0) {
            n = candidates.size();
        }

        for (int i = 0; i < n; i++) {
            if (candidates.isEmpty()) {
                throw new LatticeFullEvent();
            }

            // Choose target randomly
            Coordinate[] cVec = candidates.toArray(new Coordinate[0]);

            int o = getGeneralParameters().getRandom().nextInt(cVec.length);
            Coordinate target = cVec[o];

            Cell cell = cellDescriptor.next();

            //System.out.println("   Placing cell of type " + cell.getState() + " at location " + target);
            getLayer().getUpdateManager().place(cell, target);

            //state.highlight(target);
            candidates.remove(target);
        }

        // Make sure that a new target must be chosen prior to next invocation.
        candidates = null;

    }

    @Override
    public void init() {
        candidates = null;
    }

    @Override
    public int hashCode() {
        return cellDescriptor != null ? cellDescriptor.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Scatter scatter = (Scatter) o;

        if (cellDescriptor != null ? !cellDescriptor.equals(scatter.cellDescriptor) : scatter.cellDescriptor != null)
            return false;

        if (getActiveSites() != null ? !getActiveSites().equals(scatter.getActiveSites()) : scatter.getActiveSites() != null)
            return false;

        if (getMaxTargets() != null ? !getMaxTargets().equals(scatter.getMaxTargets()) : scatter.getMaxTargets() != null)
            return false;

        return true;
    }
}
