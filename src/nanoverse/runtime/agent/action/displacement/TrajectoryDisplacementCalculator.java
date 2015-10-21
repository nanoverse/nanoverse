package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.*;

/**
 * Created by dbborens on 10/20/2015.
 */
public class TrajectoryDisplacementCalculator {

    // Effing legacy code
    public Coordinate calcDisp(Coordinate d, int o) {
        int[] rel = new int[3];
        for (int i = 0; i < 3; i++) {
            rel[i] = 0;
        }

        // Decrement the displacement vector by one unit in a randomly chosen
        // direction, weighted so that the path is, on average, straight.
        if (o < Math.abs(d.x())) {
            rel[0] += (int) Math.signum(d.x());
        } else if (o < (Math.abs(d.x()) + Math.abs(d.y()))) {
            rel[1] += (int) Math.signum(d.y());
        } else {
            rel[2] += (int) Math.signum(d.z());
        }

        // TODO this is awful
        if (d instanceof Coordinate1D) {
            return new Coordinate1D(rel, d.flags());
        } else if (d instanceof Coordinate2D) {
            return new Coordinate2D(rel, d.flags());
        } else if (d instanceof Coordinate3D) {
            return new Coordinate3D(rel, d.flags());
        }

        throw new IllegalStateException();
    }

}
