package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.HashSet;
import java.util.function.BiFunction;

/**
 * Created by dbborens on 10/20/2015.
 */
public class CardinalVacancyWeightCalculator {

    private final CardinalVacancyDistanceCalculator calculator;
    private final AgentLayer layer;

    public CardinalVacancyWeightCalculator(AgentLayer layer, ShoveHelper shoveHelper, BiFunction<Coordinate, Coordinate, Boolean> baseCaseFunction) {
        this.layer = layer;
        calculator = new CardinalVacancyDistanceCalculator(shoveHelper, baseCaseFunction);
    }

    public CardinalVacancyWeightCalculator(CardinalVacancyDistanceCalculator calculator, AgentLayer layer) {
        this.calculator = calculator;
        this.layer = layer;
    }

    /**
     * Calculate the weight to be given to a particular cardinal direction,
     * based on the distance from the origin to the nearest vacancy in that
     * cardinal direction
     */
    public double calcWeight(Coordinate origin, Coordinate target) throws HaltCondition {
        Coordinate displacement = layer.getGeometry().
            getDisplacement(origin,
                target, Geometry.APPLY_BOUNDARIES);
        HashSet<Coordinate> affectedSites = new HashSet<>();
        calculator.calculateDistToVacancy(origin, displacement, affectedSites);
        int dist = affectedSites.size();
        return 1.0 / dist;
    }
}
