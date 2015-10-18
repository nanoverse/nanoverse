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
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.processes.*;
import nanoverse.runtime.processes.gillespie.GillespieState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

/**
 * Fills in all sites in the active site set
 * with nanoverse.runtime.cells of the type specified by the
 * process' cell descriptor. Does not throw
 * LatticeFullExceptions.
 *
 * @author dbborens
 */
public class Fill extends AgentProcess {

    private AgentDescriptor cellDescriptor;

    // If true, the process will skip over any already-filled sites. If
    // false, it will blow up if it encounters an already-filled site
    // that it expected to fill.
    private boolean skipFilled;

    @FactoryTarget
    public Fill(BaseProcessArguments arguments, AgentProcessArguments cpArguments, boolean skipFilled, AgentDescriptor cellDescriptor) {
        super(arguments, cpArguments);
        this.skipFilled = skipFilled;
        this.cellDescriptor = cellDescriptor;

        try {
            if (cpArguments.getMaxTargets().next() >= 0) {
                throw new IllegalArgumentException("Cannot specify maximum targets on fill operation. (Did you mean to limit active sites?)");
            }
        } catch (HaltCondition ex) {
            throw new IllegalStateException(ex);
        }
    }

    public void target(GillespieState gs) throws HaltCondition {
        // This process only has one event: it affects all relevant nanoverse.runtime.cells.
        if (gs != null) {
            gs.add(getID(), 1, 1D);
        }
    }

    public void fire(StepState state) throws HaltCondition {

        for (Coordinate c : getActiveSites()) {
            boolean filled = getLayer().getViewer().isOccupied(c);

            if (filled && !skipFilled) {
                String msg = "Attempted to fill coordinate " + c.toString() +
                    " but it was already filled. This is illegal unless" +
                    " the <skip-filled-sites> flag is set to true. Did you" +
                    " mean to set it? (id=" + getID() + ")";

                throw new IllegalStateException(msg);
            } else if (!filled) {
                Agent agent = cellDescriptor.next();
                getLayer().getUpdateManager().place(agent, c);
            } else {
                // Do nothing if site is filled and skipFilled is true.
            }
        }
    }

    @Override
    public void init() {
    }

    @Override
    public int hashCode() {
        int result = cellDescriptor != null ? cellDescriptor.hashCode() : 0;
        result = 31 * result + (skipFilled ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fill fill = (Fill) o;

        if (skipFilled != fill.skipFilled) return false;
        if (cellDescriptor != null ? !cellDescriptor.equals(fill.cellDescriptor) : fill.cellDescriptor != null)
            return false;

        if (getActiveSites() != null ? !getActiveSites().equals(fill.getActiveSites()) : fill.getActiveSites() != null)
            return false;

        if (getMaxTargets() != null ? !getMaxTargets().equals(fill.getMaxTargets()) : fill.getMaxTargets() != null)
            return false;

        return true;
    }
}
