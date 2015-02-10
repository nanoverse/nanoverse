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
import control.halt.LatticeFullEvent;
import control.identifiers.Coordinate;
import control.identifiers.Flags;
import geometry.Geometry;
import layers.LayerManager;
import processes.StepState;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by dbborens on 5/14/14.
 */
public class ShoveHelper {

    private LayerManager layerManager;
    private Random random;

    public ShoveHelper(LayerManager layerManager, Random random) {
        this.layerManager = layerManager;
        this.random = random;
    }

    /**
     * Push the row of cells at origin toward target, such that origin
     * winds up vacant. Return a list of affected cells.
     *
     * @param origin The site to become vacant.
     * @param target A currently unoccupied site that will become occupied at
     *               the end of the shove process. The entire line of cells,
     *               including the cell at the origin, will have been pushed
     *               in the direction of the target.
     * @return A set of coordinates that were affected by the shove operation.
     */
    public HashSet<Coordinate> shove(Coordinate origin, Coordinate target) throws HaltCondition {
        HashSet<Coordinate> affectedSites = new HashSet<>();

        Coordinate displacement = layerManager.getCellLayer().getGeometry().
                getDisplacement(origin,
                        target, Geometry.APPLY_BOUNDARIES);

        doShove(origin, displacement, affectedSites);
        return affectedSites;
    }

    /**
     * Remove all out-of-bounds cells from the system. Useful after a shoving
     * operation.
     */
    public void removeImaginary() {
        Set<Coordinate> imaginarySites = layerManager.getCellLayer().getViewer().getImaginarySites();

        for (Coordinate c : imaginarySites) {
            layerManager.getCellLayer().getUpdateManager().banish(c);
        }
    }

    /**
     * Gets the set of all nearest vacancies to the cell, and chooses randomly
     * between them.
     *
     * @param origin
     * @return
     * @throws HaltCondition
     */
    public Coordinate chooseVacancy(Coordinate origin) throws HaltCondition {
        StepState state = layerManager.getStepState();
        Coordinate target;
        // Get nearest vacancies to the cell
        Coordinate[] targets = layerManager.getCellLayer().getLookupManager().getNearestVacancies(origin, -1);
        if (targets.length == 0) {
            throw new LatticeFullEvent();
        } else {
            int i = random.nextInt(targets.length);
            target = targets[i];
        }

        return target;
    }

    /**
     * @param currentLocation: starting location. the child will be placed in this
     *                         position after the parent is shoved.
     * @param d:               displacement vector to target, in natural basis of lattice.
     * @param sites:           list of affected sites (for highlighting)
     *                         <p>
     *                         TODO: This is so cloodgy and terrible.
     */
    private void doShove(Coordinate currentLocation, Coordinate d, HashSet<Coordinate> sites) throws HaltCondition {

        // Base case 0: we've reached the target
        if (d.norm() == 0) {
            return;
        }

        // Choose whether to go horizontally or vertically, weighted
        // by the number of steps remaining in each direction
        int nv = d.norm();

        // Take a step in the chosen direction.
        int[] nextDisplacement;                // Displacement vector, one step closer
        Coordinate nextLocation;

        int[] rel = new int[3];            // Will contain a unit vector specifying
        // step direction

        // Loop if the move is illegal.
        do {

            nextDisplacement = new int[]{d.x(), d.y(), d.z()};

            nextLocation = getNextLocation(currentLocation, d, nv, nextDisplacement, rel);

            if (nextLocation == null) {
                continue;
            } else if (nextLocation.hasFlag(Flags.BEYOND_BOUNDS) && nv == 1) {
                throw new IllegalStateException("There's only one place to push cells and it's illegal!");
            } else if (!nextLocation.hasFlag(Flags.BEYOND_BOUNDS)) {
                break;
            }
        } while (true);

        Coordinate du = new Coordinate(nextDisplacement, d.flags());
        doShove(nextLocation, du, sites);

        layerManager.getCellLayer().getUpdateManager().swap(currentLocation,
                nextLocation);

        sites.add(nextLocation);
    }

    private Coordinate getNextLocation(Coordinate curLoc, Coordinate d, int nv, int[] dNext, int[] rel) {
        Coordinate nextLoc;
        int o = random.nextInt(nv);
        Coordinate disp = calcDisp(d, dNext, rel, o);
        nextLoc = layerManager.getCellLayer().getGeometry().rel2abs(curLoc,
                disp, Geometry.APPLY_BOUNDARIES);
        return nextLoc;
    }

    private Coordinate calcDisp(Coordinate d, int[] dNext, int[] rel, int o) {
        // Initialize rel vector
        for (int i = 0; i < 3; i++) {
            rel[i] = 0;
        }

        // Decrement the displacement vector by one unit in a randomly chosen
        // direction, weighted so that the path is, on average, straight.
        if (o < Math.abs(d.x())) {
            dNext[0] -= (int) Math.signum(d.x());
            rel[0] += (int) Math.signum(d.x());
        } else if (o < (Math.abs(d.x()) + Math.abs(d.y()))) {
            dNext[1] -= (int) Math.signum(d.y());
            rel[1] += (int) Math.signum(d.y());
        } else {
            dNext[2] -= (int) Math.signum(d.z());
            rel[2] += (int) Math.signum(d.z());
        }

        return new Coordinate(rel, d.flags());
    }

}
