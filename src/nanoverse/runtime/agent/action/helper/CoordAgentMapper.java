package nanoverse.runtime.agent.action.helper;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.AgentLayerViewer;

/**
 * Created by dbborens on 10/18/2015.
 */
public class CoordAgentMapper {
    private final LayerManager layerManager;

    public CoordAgentMapper(LayerManager layerManager) {
        this.layerManager = layerManager;
    }

    public Agent resolveCaller(Coordinate caller) {
        // The caller is null, indicating that the call came from
        // a top-down process. Return null.
        if (caller == null) {
            return null;
        }

        // Blow up unless target coordinate contains a behavior cell.
        // In that case, return that cell.
        Agent callerAgent = resolveAgent(caller);

        if (callerAgent == null) {
            throw new IllegalStateException("Attempted to trigger non-existent agent");
        }

        return callerAgent;
    }

    public Agent resolveAgent(Coordinate coord) {
        AgentLayerViewer viewer = layerManager.getAgentLayer().getViewer();

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
