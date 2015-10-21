package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.structural.RangeMap;

import java.util.*;
import java.util.function.BiFunction;

/**
 * Created by dbborens on 10/20/2015.
 */
public class WeightedShoveHelper {

    private final AgentLayer layer;
    private final Random random;
    private final ShoveOperationManager operationManager;
    private final ShoveHelper shoveHelper;
    private final WeightedShoveTargetChooser chooser;

    public WeightedShoveHelper(AgentLayer layer, Random random) {
        this.random = random;
        this.layer = layer;

        shoveHelper = new ShoveHelper(layer, random);

        operationManager = new ShoveOperationManager(shoveHelper,
            baseCaseFunction());

        chooser = new WeightedShoveTargetChooser(layer,
            random,
            shoveHelper,
            baseCaseFunction());
    }

    private BiFunction<Coordinate, Coordinate, Boolean> baseCaseFunction() {
        return (current, displacement) -> isBaseCase(current);
    }

    private boolean isBaseCase(Coordinate currentLocation) {
        return (!shoveHelper.isOccupied(currentLocation));
    }

    /**
     * shoves starting at the origin in a cardinal direction chosen by weight to nearest
     * vacancy along that direction. shoves until a vacancy is reached or failure.
     *
     * @param origin
     * @return affectedSites
     * @throws HaltCondition
     */
    public HashSet<Coordinate> shoveWeighted(Coordinate origin) throws HaltCondition {
        RangeMap<Coordinate> rangeMap = chooser.buildRangeMap(origin);
        Coordinate chosenTarget = chooser.chooseTarget(rangeMap);
        return doShove(origin, chosenTarget);

    }

    private HashSet<Coordinate> doShove(Coordinate origin, Coordinate chosenTarget) throws HaltCondition {
        Coordinate displacement = layer.getGeometry().
            getDisplacement(origin,
                chosenTarget, Geometry.APPLY_BOUNDARIES);

        HashSet<Coordinate> affectedSites = new HashSet<>();

        operationManager.doShove(origin, displacement, affectedSites);
        return affectedSites;
    }

}
