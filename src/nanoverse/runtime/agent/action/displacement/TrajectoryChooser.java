package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.Random;

/**
 * Created by dbborens on 10/20/2015.
 */
public class TrajectoryChooser {

    private final TrajectoryCandidateChooser candidateChooser;
    private final TrajectoryLegalityHelper legalityHelper;

    public TrajectoryChooser(AgentLayer layer, Random random) {
        candidateChooser = new TrajectoryCandidateChooser(layer, random);
        legalityHelper = new TrajectoryLegalityHelper();
    }

    public Coordinate getNextLocation(Coordinate currentLocation, Coordinate d) {
        Coordinate nextLocation;
        do {
            nextLocation = candidateChooser.getNextCandidate(currentLocation, d);

            if (legalityHelper.isLegal(nextLocation)) {
                break;
            }

            legalityHelper.handleIllegal(nextLocation, d);
        } while (true);

        return nextLocation;
    }


}
