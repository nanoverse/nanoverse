package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;

import java.util.Random;

/**
 * Created by dbborens on 10/20/2015.
 */
public class RandomNeighborChooser {

    private final Geometry geometry;
    private final Random random;

    public RandomNeighborChooser(Geometry geometry, Random random) {
        this.geometry = geometry;
        this.random = random;
    }

    /**
     * choose a random direction to shove among the cardinal directions by selecting
     * one of the neighbors at random. the displacement vector for that choice will
     * be used for all subsequent shoving in the path in shoveCardinal()
     *
     * @param parentLocation
     * @return target neighbor
     */
    public Coordinate chooseRandomNeighbor(Coordinate parentLocation) {
        Coordinate[] options = geometry.getNeighbors(parentLocation, Geometry.APPLY_BOUNDARIES);
        int i = random.nextInt(options.length);
        Coordinate target = options[i];
        return target;
    }
}
