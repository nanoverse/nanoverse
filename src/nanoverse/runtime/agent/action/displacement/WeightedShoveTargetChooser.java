package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.structural.RangeMap;

import java.util.Random;
import java.util.function.BiFunction;

/**
 * Created by dbborens on 10/20/2015.
 */
public class WeightedShoveTargetChooser {

    private final WeightedShoveTargetBuilder builder;

    public WeightedShoveTargetChooser(AgentLayer layer,
                                      Random random,
                                      ShoveHelper shoveHelper,
                                      BiFunction<Coordinate, Coordinate, Boolean> baseCaseFunction) {

        builder = new WeightedShoveTargetBuilder(layer, random, shoveHelper, baseCaseFunction);
    }

    public WeightedShoveTargetChooser(WeightedShoveTargetBuilder builder) {
        this.builder = builder;
    }

    public Coordinate choose(Coordinate origin) throws HaltCondition {
        RangeMap<Coordinate> rangeMap = builder.buildRangeMap(origin);
        Coordinate chosenTarget = builder.chooseTarget(rangeMap);
        return chosenTarget;
    }

}
