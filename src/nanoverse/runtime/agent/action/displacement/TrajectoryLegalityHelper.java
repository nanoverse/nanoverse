package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.*;

/**
 * Created by dbborens on 10/20/2015.
 */
public class TrajectoryLegalityHelper {
    public boolean isLegal(Coordinate c) {
        return (c != null && !c.hasFlag(Flags.BEYOND_BOUNDS));
    }

    public void handleIllegal(Coordinate nextLocation, Coordinate d) {
        int nv = d.norm();
        if (nextLocation.hasFlag(Flags.BEYOND_BOUNDS) && nv == 1) {
            throw new IllegalStateException("There's only one place to push nanoverse.runtime.cells and it's illegal!");
        }
    }
}
