package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;

/**
 * Created by dbborens on 10/21/2015.
 */
public class DisplacementOption {
    // This is the site that would start out occupied and end up vacant
    // (unless occupied == vacant).
    private final Coordinate occupied;

    // This is the site that would start out vacant and end up occupied
    // (unless occupied == vacant).
    private final Coordinate vacant;

    // L1 distance between origin and vacancy.
    private final int distance;

    public DisplacementOption(Coordinate occupied, Coordinate vacant, int distance) {
        this.occupied = occupied;
        this.vacant = vacant;
        this.distance = distance;
    }

    public Coordinate getOccupied() {
        return occupied;
    }

    public Coordinate getVacant() {
        return vacant;
    }

    public int getDistance() {
        return distance;
    }
}
