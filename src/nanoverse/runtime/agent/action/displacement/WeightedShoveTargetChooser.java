package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.structural.RangeMap;

import java.util.Random;
import java.util.function.BiFunction;

/**
 * Created by dbborens on 10/20/2015.
 */
public class WeightedShoveTargetChooser {

    private final AgentLayer layer;
    private final Random random;
    private final ShoveHelper shoveHelper;
    private final CardinalVacancyWeightCalculator calculator;

    public WeightedShoveTargetChooser(AgentLayer layer,
                                      Random random,
                                      ShoveHelper shoveHelper,
                                      BiFunction<Coordinate, Coordinate, Boolean> baseCaseFunction) {

        this.layer = layer;
        this.random = random;
        this.shoveHelper = shoveHelper;
        calculator = new CardinalVacancyWeightCalculator(layer, shoveHelper, baseCaseFunction);
    }

    public RangeMap<Coordinate> buildRangeMap(Coordinate origin) throws HaltCondition {
        Coordinate[] neighbors = layer.getGeometry().getNeighbors(origin, Geometry.APPLY_BOUNDARIES);
        RangeMap<Coordinate> rangeMap = new RangeMap<>(neighbors.length);

        for (Coordinate neighbor : neighbors) {
            double weight = calculator.calcWeight(origin, neighbor);
            rangeMap.add(neighbor, weight);
        }

        return rangeMap;
    }

    public Coordinate chooseTarget(RangeMap<Coordinate> rangeMap) {
        double range = rangeMap.getTotalWeight();
        double x = random.nextDouble() * range;
        Coordinate chosen = rangeMap.selectTarget(x);
        return chosen;
    }

}
