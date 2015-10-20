package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.*;

/**
 * Created by dbborens on 10/20/2015.
 */
public abstract class Shover {

    protected final ShoveHelper helper;

    public Shover(AgentLayer layer, Random random) {
        helper = new ShoveHelper(layer, random);
    }

    public Shover(ShoveHelper helper) {
        this.helper = helper;
    }

    /**
     * @param currentLocation: starting location. the child will be placed in this
     *                         position after the parent is shoved.
     * @param d:               displacement vector to target, in natural basis of lattice.
     *                         this will be the same for each shove.
     * @param sites:           list of affected sites (for highlighting)
     *                         <p>
     */
    public void doShove(Coordinate currentLocation,
                        Coordinate d, HashSet<Coordinate> sites)
        throws HaltCondition {

        if (isBaseCase(currentLocation, d)) {
            return;
        }

        Coordinate nextLocation = helper.getNextLocation(currentLocation, d);

        // use the same displacement vector d each time
        doShove(nextLocation, d, sites);

        helper.swap(currentLocation,
            nextLocation);

        sites.add(nextLocation);
    }

    protected abstract boolean isBaseCase(Coordinate currentLocation, Coordinate d);
}
