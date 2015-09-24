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

import control.halt.HaltCondition;
import control.identifiers.Coordinate;
import geometry.Geometry;
import processes.BaseProcessArguments;
import processes.StepState;
import processes.gillespie.GillespieState;
import structural.annotations.FactoryTarget;

import java.util.Set;

/**
 * Neighbor swap -- switches a randomly chosen cell with one of
 * its neighbors.
 *
 * @author dbborens
 */
public class GeneralNeighborSwap extends CellProcess {

    private Coordinate[] getActiveSitesArr;

    @FactoryTarget
    public GeneralNeighborSwap(BaseProcessArguments arguments, CellProcessArguments cpArguments) {

        super(arguments, cpArguments);
    }

    @Override
    public void init() {
        getActiveSitesArr = new Coordinate[getActiveSites().size()];
        getActiveSites().toArray(getActiveSitesArr);
    }

    @Override
    public void fire(StepState state) throws HaltCondition {

        // Determine number of swaps to make.
        int n = getMaxTargets().next();


        // Perform swaps.
        for (int i = 0; i < n; i++) {
            // Choose first coordinate. Targets can be swapped multpiple times.
            int o = getGeneralParameters().getRandom().nextInt(getActiveSitesArr.length);

            Coordinate first = getActiveSitesArr[o];

            System.out.println(first);
            Coordinate[] neighbors = getLayer().getGeometry().
                    getNeighbors(first, Geometry.APPLY_BOUNDARIES);

            int m = getGeneralParameters().getRandom().nextInt(neighbors.length);

            Coordinate second = neighbors[m];

            System.out.println(second);

            getLayer().getUpdateManager().swap(first, second);
        }

        // Remove any accumulated imaginary sites.
        removeImaginary();
    }

    @Override
    public void target(GillespieState gs) throws HaltCondition {
        if (gs != null) {
            gs.add(getID(), 1, 1.0);
        }
    }

    /**
     * Remove all out-of-bounds cells from the system.
     */
    private void removeImaginary() {
        Set<Coordinate> imaginarySites = getLayerManager().getCellLayer().getViewer().getImaginarySites();

        for (Coordinate c : imaginarySites) {
            getLayerManager().getCellLayer().getUpdateManager().banish(c);
        }
    }
}
