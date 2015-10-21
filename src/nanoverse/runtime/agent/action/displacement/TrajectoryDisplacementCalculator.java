package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.*;

/**
 * Created by dbborens on 10/20/2015.
 */
public class TrajectoryDisplacementCalculator {
    public Coordinate calcDisp(Coordinate d, int o) {
        int[] dNext = asArray(d);

        int[] rel = new int[3];
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

        return new Coordinate2D(rel, d.flags());
    }

    private int[] asArray(Coordinate d) {
        int[] arr = new int[3];
        arr[0] = d.x();
        arr[1] = d.y();
        arr[2] = d.z();
        return arr;
    }
}
