package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.List;
import java.util.stream.Collectors;

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
     * Remove all out-of-bounds agents from the system. Useful after a shoving
     * operation.
     */
    public void removeImaginary() {
        // Collect to a new list to avoid concurrent modification
        List<Coordinate> sites = layer.getViewer().getImaginarySites().collect(Collectors.toList());
        sites.stream().forEach(c -> layer.getUpdateManager().banish(c));
    }
}
