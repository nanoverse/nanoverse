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

import nanoverse.runtime.agent.AbstractAgent;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.*;
import nanoverse.runtime.processes.*;

import java.util.Random;

public abstract class BulkDivisionProcess extends CellProcess {


    protected Random random;
    private ShoveHelper shoveHelper;

    public BulkDivisionProcess(BaseProcessArguments arguments, CellProcessArguments cpArguments) {
        super(arguments, cpArguments);
    }

    @Override
    public void init() {
        random = getGeneralParameters().getRandom();
        shoveHelper = new ShoveHelper(getLayerManager(), random);
    }

    protected void execute(Coordinate[] candidates) throws HaltCondition {
        Object[] chosen = MaxTargetHelper.respectMaxTargets(candidates, getMaxTargets().next(), getGeneralParameters().getRandom());
        AbstractAgent[] chosenAgents = toCellArray(chosen);
        for (int i = 0; i < chosenAgents.length; i++) {
            AbstractAgent agent = chosenAgents[i];
            CellLookupManager lm = getLayer().getLookupManager();
            Coordinate currentLocation = lm.getCellLocation(agent);
            doDivision(currentLocation);
        }

        // The shoving process complete, look for nanoverse.runtime.cells that have gotten pushed
        // out of bounds and remove them. If such a situation is not permitted
        // in this nanoverse.runtime.geometry, this loop merely iterates over an empty set.
        shoveHelper.removeImaginary();
    }

    private AbstractAgent[] toCellArray(Object[] chosen) {
        int n = chosen.length;
        AbstractAgent[] abstractAgents = new AbstractAgent[n];
        for (int i = 0; i < n; i++) {
            Coordinate coord = (Coordinate) chosen[i];
            AbstractAgent agent = getLayer().getViewer().getCell(coord);
            abstractAgents[i] = agent;
        }

        return abstractAgents;
    }


    protected void doDivision(Coordinate origin) throws HaltCondition {
        // Get child cell
        CellUpdateManager um = getLayer().getUpdateManager();
        AbstractAgent child = um.divide(origin);

        Coordinate target = shoveHelper.chooseVacancy(origin);

        shoveHelper.shove(origin, target);

        // Divide the child cell into the vacancy left by the parent
        getLayer().getUpdateManager().place(child, origin);
    }


}
