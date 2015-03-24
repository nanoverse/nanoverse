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

    /**
     * shoves starting at the origin in a randomly chosen cardinal direction until
     * a vacancy is reached or failure.
     * @param origin
     * @return affectedSites
     * @throws HaltCondition
     */
    public HashSet<Coordinate> shoveRandom(Coordinate origin) throws HaltCondition {
        HashSet<Coordinate> affectedSites = new HashSet<>();

        Coordinate target = chooseRandomNeighbor(origin);
        Coordinate displacement = layerManager.getCellLayer().getGeometry().
                getDisplacement(origin,
                        target, Geometry.APPLY_BOUNDARIES);
        doShoveCardinal(origin, displacement, affectedSites);
        return affectedSites;
    }

    /**
     *  choose a random direction to shove among the cardinal directions by selecting
     *  one of the neighbors at random. the displacement vector for that choice will
     *  be used for all subsequent shoving in the path in shoveCardinal()
     * @param parentLocation
     * @return target neighbor
     */
    private Coordinate chooseRandomNeighbor(Coordinate parentLocation) {
        Coordinate[] options = layerManager.getCellLayer().getGeometry().getNeighbors(parentLocation, Geometry.APPLY_BOUNDARIES);
        int i = random.nextInt(options.length);
        Coordinate target = options[i];
        return target;
    }

    /**
     * @param currentLocation: starting location. the child will be placed in this
     *                         position after the parent is shoved.
     * @param d:               displacement vector to target, in natural basis of lattice.
     *                         this will be the same for each shove.
     * @param sites:           list of affected sites (for highlighting)
     *                         <p>
     *                         TODO: This is so cloodgy and terrible.
     */
    private void doShoveCardinal(Coordinate currentLocation, Coordinate d, HashSet<Coordinate> sites) throws HaltCondition {
        // base case: if the currentLocation is vacant, can stop shoving
        if (!layerManager.getCellLayer().getViewer().isOccupied(currentLocation)) {
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

        // use the same displacement vector d each time
        doShoveCardinal(nextLocation, d, sites);

        layerManager.getCellLayer().getUpdateManager().swap(currentLocation,
                nextLocation);

        sites.add(nextLocation);
        return;
    }

    /**
     * shoves starting at the origin in a cardinal direction chosen by weight to nearest
     * vacancy along that direction. shoves until a vacancy is reached or failure.
     * @param origin
     * @return affectedSites
     * @throws HaltCondition
     */
    public HashSet<Coordinate> shoveWeighted(Coordinate origin) throws HaltCondition {
        Coordinate[] neighbors = layerManager.getCellLayer().getGeometry().getNeighbors(origin, Geometry.APPLY_BOUNDARIES);
        Coordinate target;
        Coordinate displacement;
        HashSet<Coordinate> affectedSites;
        int [] dist = new int[neighbors.length];
        double [] weight = new double[neighbors.length];
        double total = 0.0;
        for (int i=0; i<neighbors.length; i++) {
            target = neighbors[i];
            displacement = layerManager.getCellLayer().getGeometry().
                    getDisplacement(origin,
                            target, Geometry.APPLY_BOUNDARIES);
            affectedSites = new HashSet<>();
            calculateDistToVacancy(origin, displacement, affectedSites);
            dist[i] = affectedSites.size();
            weight[i] = 1.0/dist[i];
            total = total + weight[i];
        }

        double r = random.nextDouble() * total;

        Coordinate chosenTarget = neighbors[0];
        boolean found = false;
        if (r<weight[0]) {
            found = true;
        }
        double sum = weight[0];
        for (int j=1; j<neighbors.length; j++) {
            if (found == false) {
                if (r < sum + weight[j]) {
                    chosenTarget = neighbors[j];
                    found = true;
                } else {
                    sum = sum + weight[j];
                }
            }
        }

        displacement = layerManager.getCellLayer().getGeometry().
                getDisplacement(origin,
                        chosenTarget, Geometry.APPLY_BOUNDARIES);
        affectedSites = new HashSet<>();
        doShoveCardinal(origin, displacement, affectedSites);
        return affectedSites;
    }


    /**
     * @param currentLocation: starting location. the child will be placed in this
     *                         position after the parent is shoved.
     * @param d:               displacement vector to target, in natural basis of lattice.
     *                         this will be the same for each shove.
     * @param sites:           list of affected sites (for highlighting)
     *                         <p>
     *                         TODO: This is so cloodgy and terrible.
     */
    private void calculateDistToVacancy(Coordinate currentLocation, Coordinate d, HashSet<Coordinate> sites) throws HaltCondition {
        // base case: if the currentLocation is vacant, can stop shoving
        if (!layerManager.getCellLayer().getViewer().isOccupied(currentLocation)) {
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

        // use the same displacement vector d each time
        calculateDistToVacancy(nextLocation, d, sites);

        sites.add(nextLocation);
        return;
    }

//    /**
//     * @param currentLocation: starting location.
//     * @param d:               displacement vector to target, in natural basis of lattice.
//     *                         this will be the same for each shove.
//     * @param sites:           list of affected sites
//     *                         <p>
//     *                         TODO: This is so cloodgy and terrible.
//     */
//    private int calculateDistToVacancy(Coordinate currentLocation, Coordinate d) throws HaltCondition {
//        int count = 0;
//        CellLayer cellLayer = layerManager.getCellLayer().clone();
//        while (cellLayer.getViewer().isOccupied(currentLocation)) {
//            // Choose whether to go horizontally or vertically, weighted
//            // by the number of steps remaining in each direction
//            int nv = d.norm();
//
//            // Take a step in the chosen direction.
//            int[] nextDisplacement;                // Displacement vector, one step closer
//            Coordinate nextLocation;
//
//            int[] rel = new int[3];            // Will contain a unit vector specifying
//            // step direction
//
//            // Loop if the move is illegal.
//            do {
//                nextDisplacement = new int[]{d.x(), d.y(), d.z()};
//                nextLocation = getNextLocation(currentLocation, d, nv, nextDisplacement, rel);
//                if (nextLocation == null) {
//                    continue;
//                } else if (nextLocation.hasFlag(Flags.BEYOND_BOUNDS) && nv == 1) {
//                    throw new IllegalStateException("There's only one place to push cells and it's illegal!");
//                } else if (!nextLocation.hasFlag(Flags.BEYOND_BOUNDS)) {
//                    break;
//                }
//            } while (true);
//            cellLayer.getUpdateManager().swap(currentLocation, nextLocation);
//            count++;
//        }
//        return count;
//    }

//    private void calculateDistToVacancy(Coordinate currentLocation, Coordinate d, HashSet<Coordinate> sites) throws HaltCondition {
//        // base case: if the currentLocation is vacant, can stop shoving
//        if (!layerManager.getCellLayer().getViewer().isOccupied(currentLocation)) {
//            return;
//        }
//
//        // Choose whether to go horizontally or vertically, weighted
//        // by the number of steps remaining in each direction
//        int nv = d.norm();
//
//        // Take a step in the chosen direction.
//        int[] nextDisplacement;                // Displacement vector, one step closer
//        Coordinate nextLocation;
//
//        int[] rel = new int[3];            // Will contain a unit vector specifying
//        // step direction
//
//        // Loop if the move is illegal.
//        do {
//
//            nextDisplacement = new int[]{d.x(), d.y(), d.z()};
//
//            nextLocation = getNextLocation(currentLocation, d, nv, nextDisplacement, rel);
//
//            if (nextLocation == null) {
//                continue;
//            } else if (nextLocation.hasFlag(Flags.BEYOND_BOUNDS) && nv == 1) {
//                throw new IllegalStateException("There's only one place to push cells and it's illegal!");
//            } else if (!nextLocation.hasFlag(Flags.BEYOND_BOUNDS)) {
//                break;
//            }
//        } while (true);
//
//        // use the same displacement vector d each time
//        calculateDistToVacancy(nextLocation, d, sites);
//
////        layerManager.getCellLayer().getUpdateManager().swap(currentLocation,
////                nextLocation);
//
//        sites.add(nextLocation);
//        return;
//    }
}
