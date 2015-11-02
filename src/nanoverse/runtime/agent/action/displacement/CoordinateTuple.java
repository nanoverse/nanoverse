package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;

/**
 * Created by dbborens on 11/2/2015.
 */
public class CoordinateTuple {

    private final Coordinate displacement;
    private final Coordinate origin;

    public CoordinateTuple(Coordinate displacement, Coordinate origin) {
        this.displacement = displacement;
        this.origin = origin;
    }

    public Coordinate getDisplacement() {
        return displacement;
    }

    public Coordinate getOrigin() {
        return origin;
    }
}
