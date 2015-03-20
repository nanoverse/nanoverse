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

import cells.Cell;
import control.halt.HaltCondition;
import control.identifiers.Coordinate;
import layers.cell.CellUpdateManager;
import processes.BaseProcessArguments;
import processes.StepState;
import processes.gillespie.GillespieState;

import java.util.ArrayList;

/**
 * Kill all cells whose biomass is less than a defined
 * threshold.
 * <p>
 * Created by dbborens on 3/5/14.
 */
public class Cull extends CellProcess {
    private Coordinate[] targetsArr;
    private double threshold;

    public Cull(BaseProcessArguments arguments, CellProcessArguments cpArguments, double threshold) {
        super(arguments, cpArguments);
        this.threshold = threshold;
    }

    @Override
    public void init() {
        targetsArr = null;
    }

    public void target(GillespieState gs) throws HaltCondition {

        ArrayList<Coordinate> targets = new ArrayList<>();
        for (Coordinate candidate : activeSites) {
            if (!layer.getViewer().isOccupied(candidate)) {
                continue;
            }

            Cell cell = layer.getViewer().getCell(candidate);
            if (cell.getHealth() <= threshold) {
                targets.add(candidate);
            }
        }

        targetsArr = targets.toArray(new Coordinate[0]);

        if (gs != null) {
            gs.add(getID(), targets.size(), targets.size() * 1.0D);
        }
    }

    public void fire(StepState state) throws HaltCondition {
        execute(state, targetsArr);
        targetsArr = null;
    }

    private void execute(StepState state, Coordinate[] targetsArr) {
//        CellUpdateManager manager = layer.getUpdateManager();
        for (Coordinate target : targetsArr) {
            layer.getViewer().getCell(target).die();
//            manager.banish(target);
        }
    }
}
