package nanoverse.runtime.agent.action.helper;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.AgentLayerViewer;

/**
 * Created by dbborens on 10/18/2015.
 */
public class AgentResolver {

    private final LayerManager layerManager;

    public AgentResolver(LayerManager layerManager) {
        this.layerManager = layerManager;
    }

    public Agent resolveAgent(Coordinate coord) {
        AgentLayerViewer viewer = layerManager.getAgentLayer().getViewer();

        if (coord == null) {
            throw new IllegalStateException("Attempting to resolve null coordinate to agent.");
        }
        if (!viewer.isOccupied(coord)) {
            return null;
        }

        Agent result = viewer.getAgent(coord);

        return result;
    }

    public LayerManager getLayerManager() {
        return layerManager;
    }
}
