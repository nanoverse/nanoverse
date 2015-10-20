package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.*;

/**
 * Created by dbborens on 10/20/2015.
 */
public class WeightedShoveHelper extends CardinalShover {

    private final AgentLayer layer;
    private final Random random;

    public WeightedShoveHelper(AgentLayer layer, Random random) {
        super(layer);
        this.random = random;
        this.layer = layer;
    }

    /**
     * shoves starting at the origin in a cardinal direction chosen by weight to nearest
     * vacancy along that direction. shoves until a vacancy is reached or failure.
     *
     * @param origin
     * @return affectedSites
     * @throws HaltCondition
     */
    public HashSet<Coordinate> shoveWeighted(Coordinate origin) throws HaltCondition {
        Coordinate[] neighbors = layer.getGeometry().getNeighbors(origin, Geometry.APPLY_BOUNDARIES);
        Coordinate target;
        Coordinate displacement;
        HashSet<Coordinate> affectedSites;
        int[] dist = new int[neighbors.length];
        double[] weight = new double[neighbors.length];
        double total = 0.0;
        for (int i = 0; i < neighbors.length; i++) {
            target = neighbors[i];
            displacement = layer.getGeometry().
                getDisplacement(origin,
                    target, Geometry.APPLY_BOUNDARIES);
            affectedSites = new HashSet<>();
            calculateDistToVacancy(origin, displacement, affectedSites);
            dist[i] = affectedSites.size();
            weight[i] = 1.0 / dist[i];
            total = total + weight[i];
        }

        double r = random.nextDouble() * total;

        Coordinate chosenTarget = neighbors[0];
        boolean found = false;
        if (r < weight[0]) {
            found = true;
        }
        double sum = weight[0];
        for (int j = 1; j < neighbors.length; j++) {
            if (found == false) {
                if (r < sum + weight[j]) {
                    chosenTarget = neighbors[j];
                    found = true;
                } else {
                    sum = sum + weight[j];
                }
            }
        }

        displacement = layer.getGeometry().
            getDisplacement(origin,
                chosenTarget, Geometry.APPLY_BOUNDARIES);
        affectedSites = new HashSet<>();
        doShove(origin, displacement, affectedSites);
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
        if (!layer.getViewer().isOccupied(currentLocation)) {
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
            nextLocation = helper.getNextLocation(currentLocation, d, nv, nextDisplacement, rel);

            if (nextLocation == null) {
                continue;
            } else if (nextLocation.hasFlag(Flags.BEYOND_BOUNDS) && nv == 1) {
                throw new IllegalStateException("There's only one place to push nanoverse.runtime.cells and it's illegal!");
            } else if (!nextLocation.hasFlag(Flags.BEYOND_BOUNDS)) {
                break;
            }
        } while (true);

        // use the same displacement vector d each time
        calculateDistToVacancy(nextLocation, d, sites);

        sites.add(nextLocation);
        return;
    }
}
