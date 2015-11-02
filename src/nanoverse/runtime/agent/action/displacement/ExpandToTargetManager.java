package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.agent.action.helper.*;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;

import java.util.Random;

/**
 * Created by dbborens on 10/21/2015.
 */
public class ExpandToTargetManager {
    private final ActionIdentityManager identity;
    private final Random random;
    private final DisplacementManager displacementManager;
    private final DisplacementOptionResolver resolver;

    public ExpandToTargetManager(ActionIdentityManager identity, Random random, CoordAgentMapper mapper, DisplacementManager displacementManager) {
        this.identity = identity;
        this.random = random;
        this.displacementManager = displacementManager;
        resolver = new DisplacementOptionResolver(displacementManager, mapper);
    }

    public ExpandToTargetManager(ActionIdentityManager identity, Random random, DisplacementManager displacementManager, DisplacementOptionResolver resolver) {
        this.identity = identity;
        this.random = random;
        this.displacementManager = displacementManager;
        this.resolver = resolver;
    }

    /**
     * Compare the distance between the origin and its nearest vacancy,
     * and the target and its nearest vacancy. Report the closer one as the
     * preferred direction of expansion.
     */
    public DisplacementOption getShortestOption(Coordinate target) throws HaltCondition {
        Coordinate origin = identity.getOwnLocation();

        // Get option that starts with origin.
        DisplacementOption originOption = resolver.getOption(origin);

        // Get option that starts with target.
        DisplacementOption targetOption = resolver.getOption(target);

        // Origin closer to vacancy?
        if (originOption.getDistance() < targetOption.getDistance()) {
            return originOption;
            // Target closer to vacancy?
        } else if (originOption.getDistance() > targetOption.getDistance()) {
            return targetOption;
            // Same?
        } else {
            // The coin toss arbitrarily favors shoving parent on true.
            return (random.nextBoolean() ? originOption : targetOption);
        }
    }

    public void doShove(DisplacementOption shortestOption) throws HaltCondition {
        Coordinate occupied = shortestOption.getOccupied();
        Coordinate vacant = shortestOption.getVacant();
        displacementManager.shove(occupied, vacant);
    }

}
