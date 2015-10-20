package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.Set;

/**
 * Removes out-of-bound coordinates.
 * Created by dbborens on 10/20/2015.
 */
public class ImaginarySiteCleaner {

    private final AgentLayer layer;

    public ImaginarySiteCleaner(AgentLayer layer) {
        this.layer = layer;
    }

    /**
     * Remove all out-of-bounds nanoverse.runtime.cells from the system. Useful after a shoving
     * operation.
     */
    public void removeImaginary() {
        Set<Coordinate> imaginarySites = layer.getViewer().getImaginarySites();

        for (Coordinate c : imaginarySites) {
            layer.getUpdateManager().banish(c);
        }
    }
}
