package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.layers.cell.AgentLayer;

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
        layer.getViewer()
            .getImaginarySites()
            .forEach(c -> layer.getUpdateManager().banish(c));
    }
}
