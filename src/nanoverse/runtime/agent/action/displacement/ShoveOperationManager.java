package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import org.slf4j.*;

import java.util.HashSet;
import java.util.function.BiFunction;

/**
 * Created by dbborens on 10/20/2015.
 */
public class ShoveOperationManager {
    private final Logger logger;
    private final ShoveHelper helper;
    private final BiFunction<Coordinate, Coordinate, Boolean> isBaseCase;

    public ShoveOperationManager(ShoveHelper helper, BiFunction<Coordinate, Coordinate, Boolean> isBaseCase) {
        logger = LoggerFactory.getLogger(ShoveOperationManager.class);
        this.helper = helper;
        this.isBaseCase = isBaseCase;
    }

    /**
     * @param currentLocation: starting location. the child will be placed in this
     *                         position after the parent is shoved.
     * @param displacement:    displacement vector to target, in natural basis of lattice.
     *                         this will be the same for each shove.
     * @param sites:           list of affected sites (for highlighting)
     *                         <p>
     */
    public void doShove(Coordinate currentLocation, Coordinate displacement, HashSet<Coordinate> sites) throws HaltCondition {

        logger.debug("Shoving. Origin: " + currentLocation + ". Displacement: " + displacement + ".");

        if (isBaseCase.apply(currentLocation, displacement)) {
            return;
        }

        CoordinateTuple tuple = helper.getNextTuple(currentLocation, displacement);
        Coordinate nextDisplacement = tuple.getDisplacement();
        Coordinate nextLocation = tuple.getOrigin();

        // use the same displacement vector d each time
        doShove(nextLocation, nextDisplacement, sites);

        helper.swap(currentLocation, nextLocation);

        sites.add(nextLocation);
    }
}
