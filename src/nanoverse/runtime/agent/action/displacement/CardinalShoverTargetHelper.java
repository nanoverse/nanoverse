package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.Random;

/**
 * Created by dbborens on 10/20/2015.
 */
public class CardinalShoverTargetHelper {

    private final AgentLayer layer;
    private final RandomNeighborChooser neighborChooser;

    public CardinalShoverTargetHelper(AgentLayer layer, Random random) {
        this.layer = layer;
        neighborChooser = new RandomNeighborChooser(layer.getGeometry(), random);
    }

    public Coordinate getDisplacementToRandomTarget(Coordinate origin) {
        Coordinate target = neighborChooser.chooseRandomNeighbor(origin);
        Coordinate displacement = layer.getGeometry().
            getDisplacement(origin,
                target, Geometry.APPLY_BOUNDARIES);

        return displacement;
    }
}
