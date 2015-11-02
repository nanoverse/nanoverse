package nanoverse.runtime.agent.action.helper;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.*;

/**
 * Keeps track of agent that owns this action, and resolves its location.
 *
 * Created by dbborens on 10/18/2015.
 */
public class ActionIdentityManager {
    private final Agent self;
    private final AgentLayer layer;

    public ActionIdentityManager(Agent self, AgentLayer layer) {
        this.self = self;
        this.layer = layer;
    }

    /**
     * Returns the location of the cell whose behavior this is.
     *
     * @return
     */
    public Coordinate getOwnLocation() {
        AgentLookupManager lookup = layer.getLookupManager();

        // If the acting cell has been removed from the lattice, return no coordinate.
        // It's up to the particular Action to decide what happens at that point.
        if (!layer.getViewer().exists(self)) {
            return null;
        }

        Coordinate location = lookup.getAgentLocation(self);
        return location;
    }

    public Agent getSelf() {
        return self;
    }

    public boolean selfExists() {
        return layer.getViewer().exists(self);
    }

}
