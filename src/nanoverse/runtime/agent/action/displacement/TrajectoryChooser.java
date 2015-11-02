package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.structural.RangeMap;
import org.slf4j.*;

import java.util.Random;
import java.util.function.BiFunction;

/**
 * Created by dbborens on 10/20/2015.
 */
public class TrajectoryChooser {
    private final Random random;
    private final BiFunction<Coordinate, Coordinate, CoordinateTupleOptionMap> mapMaker;

    public TrajectoryChooser(Random random) {
        this.random = random;
        mapMaker = (a, b) -> new CoordinateTupleOptionMap(a, b);
    }

    public TrajectoryChooser(Random random,
                             BiFunction<Coordinate, Coordinate, CoordinateTupleOptionMap> mapMaker) {
        this.random = random;
        this.mapMaker = mapMaker;
    }

    public CoordinateTuple getNextTuple(Coordinate currentLocation, Coordinate currentDisplacement) {
        RangeMap<CoordinateTuple> chooser = mapMaker.apply(currentLocation, currentDisplacement);
        double weight = chooser.getTotalWeight();
        double x = random.nextDouble() * weight;
        return chooser.selectTarget(x);
    }

}
