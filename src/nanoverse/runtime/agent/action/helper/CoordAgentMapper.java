package nanoverse.runtime.agent.action.helper;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 10/18/2015.
 */
public class CoordAgentMapper {
    private final AgentResolver resolver;

    public CoordAgentMapper(LayerManager layerManager) {
        resolver = new AgentResolver(layerManager);
    }

    public CoordAgentMapper(AgentResolver resolver) {
        this.resolver = resolver;
    }

    public Agent resolveCaller(Coordinate caller) {
        // The caller is null, indicating that the call came from
        // a top-down process. Return null.
        if (caller == null) {
            return null;
        }

        Agent callerAgent = resolveAgent(caller);
        return callerAgent;
    }

    public Agent resolveAgent(Coordinate coord) {
        return resolver.resolveAgent(coord);
    }

    public LayerManager getLayerManager() {
        return resolver.getLayerManager();
    }

}
