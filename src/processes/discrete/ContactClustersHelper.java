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

import cells.*;
import control.halt.HaltCondition;
import control.identifiers.Coordinate;
import layers.cell.CellLayer;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by dbborens on 6/13/2015.
 */
public class ContactClustersHelper extends ScatterClustersHelper {

    public ContactClustersHelper(CellLayer layer) {
        super(layer);
    }

    /**
     * Place a cell if the candidate site is a valid site for placement.
     * Returns total number of cells placed.
     *
     * @param candidate
     * @param toPlace
     * @param m
     * @return
     */
    @Override
    public int attemptPlacement(Coordinate candidate, BehaviorCell toPlace, int m) {
        if (layer.getViewer().isOccupied(candidate)) {
            return 0;
        }
        int needed = needed(candidate, toPlace, m);
        if (needed > -1) {
            placeAndColonize(candidate, toPlace, needed);
            return needed + 1;
        }
        return 0;
    }

}
