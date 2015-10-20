package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.Random;

/**
 * Created by dbborens on 10/20/2015.
 */
public class ShoveHelper {

    private final AgentLayer layer;
    private TrajectoryChooser chooser;

    public ShoveHelper(AgentLayer layer, Random random) {
        this.layer = layer;
        chooser = new TrajectoryChooser(layer, random);
    }

    public boolean isOccupied(Coordinate c) {
        return layer.getViewer().isOccupied(c);
    }

    public void swap(Coordinate p, Coordinate q) throws HaltCondition {
        layer.getUpdateManager().swap(p, q);
    }

    public Coordinate getNextLocation(Coordinate currentLocation, Coordinate d) {
        return chooser.getNextLocation(currentLocation, d);
    }

}
