package nanoverse.runtime.agent.action.stochastic;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentLayer;

/**
 * Created by dbborens on 10/13/2015.
 */
public class NeighborhoodCountHelper {

    private final AgentLayer layer;
    private final Agent agent;

    public NeighborhoodCountHelper(AgentLayer layer, Agent agent) {
        this.layer = layer;
        this.agent = agent;
    }

    public double getNeighborCount() {
        Coordinate location = layer
                .getLookupManager()
                .getAgentLocation(agent);

        String[] neighborNames = layer.getLookupManager().getNeighborNames(location, true);
        double neighborCount = neighborNames.length;
        return neighborCount;
    }

    public NeighborhoodCountHelper clone(Agent child) {
        return new NeighborhoodCountHelper(layer, child);
    }
}
