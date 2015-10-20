package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.*;

/**
 * CardinalShover pushes on agents in a random cardinal direction until a
 * vacancy is reached.
 * <p>
 * Created by dbborens on 10/20/2015.
 */
public class CardinalShover extends Shover {

    private final AgentLayer layer;
    private final RandomNeighborChooser neighborChooser;

    public CardinalShover(AgentLayer layer, Random random) {
        super(layer, random);
        this.layer = layer;
        neighborChooser = new RandomNeighborChooser(layer.getGeometry(), random);
    }

    @Override
    public boolean isBaseCase(Coordinate currentLocation, Coordinate d) {
        return (!helper.isOccupied(currentLocation));
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
        HashSet<Coordinate> affectedSites = new HashSet<>();

        Coordinate target = neighborChooser.chooseRandomNeighbor(origin);
        Coordinate displacement = layer.getGeometry().
            getDisplacement(origin,
                target, Geometry.APPLY_BOUNDARIES);

        doShove(origin, displacement, affectedSites);
        return affectedSites;
    }
}
