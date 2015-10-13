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

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.processes.*;
import nanoverse.runtime.processes.gillespie.GillespieState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.HashSet;

public class Divide extends BulkDivisionProcess {

    private Coordinate[] candidates;

    @FactoryTarget
    public Divide(BaseProcessArguments arguments, AgentProcessArguments cpArguments) {
        super(arguments, cpArguments);
    }


    @Override
    public void init() {
        candidates = null;
    }

    public void target(GillespieState gs) throws HaltCondition {
        HashSet<Coordinate> candSet = getLayer().getViewer().getDivisibleSites();
        candidates = candSet.toArray(new Coordinate[0]);
        if (gs != null) {
            gs.add(getID(), candidates.length, candidates.length * 1.0D);
        }
    }

    public void fire(StepState state) throws HaltCondition {
        execute(candidates);
        candidates = null;
    }

}
