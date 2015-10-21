package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.*;
import java.util.function.BiFunction;

/**
 * CardinalShover pushes on agents in a random cardinal direction until a
 * vacancy is reached.
 * <p>
 * Created by dbborens on 10/20/2015.
 */
public class CardinalShover {

    private final CardinalShoverTargetHelper targetHelper;
    private final ShoveHelper shoveHelper;
    private final ShoveOperationManager operationManager;

    public CardinalShover(AgentLayer layer, Random random) {
        shoveHelper = new ShoveHelper(layer, random);
        targetHelper = new CardinalShoverTargetHelper(layer, random);
        operationManager = new ShoveOperationManager(shoveHelper, baseCaseFunction());
    }

    private BiFunction<Coordinate, Coordinate, Boolean> baseCaseFunction() {
        return (current, displacement) -> isBaseCase(current);
    }

    private boolean isBaseCase(Coordinate currentLocation) {
        return (!shoveHelper.isOccupied(currentLocation));
    }

    /**
     * shoves starting at the origin in a randomly chosen cardinal direction until
     * a vacancy is reached or failure.
     *
     * @param origin
     * @return affectedSites
     * @throws HaltCondition
     */
    public HashSet<Coordinate> shoveRandom(Coordinate origin) throws HaltCondition {
        Coordinate displacement = targetHelper.getDisplacementToRandomTarget(origin);
        return doShove(origin, displacement);
    }

    public HashSet<Coordinate> doShove(Coordinate origin, Coordinate displacement) throws HaltCondition {
        HashSet<Coordinate> affectedSites = new HashSet<>();
        operationManager.doShove(origin, displacement, affectedSites);
        return affectedSites;
    }
}
