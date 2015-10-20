package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.Random;

/**
 * Created by dbborens on 10/20/2015.
 */
public class TrajectoryCandidateChooser {
    private final AgentLayer layer;
    private final Random random;

    public TrajectoryCandidateChooser(AgentLayer layer, Random random) {
        this.layer = layer;
        this.random = random;
    }

    public Coordinate getNextCandidate(Coordinate curLoc, Coordinate d) {
        Coordinate nextLoc;
        int nv = d.norm();
        int o = random.nextInt(nv);
        Coordinate disp = calcDisp(d, o);
        nextLoc = layer.getGeometry().rel2abs(curLoc,
            disp, Geometry.APPLY_BOUNDARIES);
        return nextLoc;
    }

    private Coordinate calcDisp(Coordinate d, int o) {
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
