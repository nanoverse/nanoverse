package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;

import java.util.HashSet;
import java.util.function.BiFunction;

/**
 * Created by dbborens on 10/20/2015.
 */
public class CardinalVacancyDistanceCalculator {

    private final ShoveHelper shoveHelper;
    private final BiFunction<Coordinate, Coordinate, Boolean> baseCaseFunction;

    public CardinalVacancyDistanceCalculator(ShoveHelper shoveHelper, BiFunction<Coordinate, Coordinate, Boolean> baseCaseFunction) {
        this.shoveHelper = shoveHelper;
        this.baseCaseFunction = baseCaseFunction;
    }

    public void calculateDistToVacancy(Coordinate currentLocation, Coordinate d, HashSet<Coordinate> sites) throws HaltCondition {
        if (baseCaseFunction.apply(currentLocation, d) == true) {
            return;
        }

        Coordinate nextLocation = shoveHelper.getNextLocation(currentLocation, d);
        calculateDistToVacancy(nextLocation, d, sites);
        sites.add(nextLocation);
    }
}
