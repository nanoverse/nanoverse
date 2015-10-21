package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.Random;

/**
 * Created by dbborens on 10/20/2015.
 */
public class TrajectoryCandidateChooser {
    private final AgentLayer layer;
    private final Random random;
    private final TrajectoryDisplacementCalculator calculator;

    public TrajectoryCandidateChooser(AgentLayer layer, Random random) {
        this.layer = layer;
        this.random = random;
        this.calculator = new TrajectoryDisplacementCalculator();
    }

    public TrajectoryCandidateChooser(AgentLayer layer, Random random, TrajectoryDisplacementCalculator calculator) {
        this.layer = layer;
        this.random = random;
        this.calculator = calculator;
    }

    public Coordinate getNextCandidate(Coordinate curLoc, Coordinate d) {
        int nv = d.norm();
        int o = random.nextInt(nv);
        Coordinate disp = calculator.calcDisp(d, o);
        Coordinate nextLoc = layer.getGeometry().rel2abs(curLoc,
            disp, Geometry.APPLY_BOUNDARIES);
        return nextLoc;
    }

}
