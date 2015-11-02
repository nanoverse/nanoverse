package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.action.helper.*;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentUpdateManager;

/**
 * Created by dbborens on 10/21/2015.
 */
public class CloneToVacancyHelper {
    private final ActionIdentityManager identity;
    private final CoordAgentMapper mapper;

    public CloneToVacancyHelper(ActionIdentityManager identity, CoordAgentMapper mapper) {
        this.identity = identity;
        this.mapper = mapper;
    }

    public void cloneToVacancy(Coordinate vacancy) throws HaltCondition {
        AgentUpdateManager u = mapper.getLayerManager().getAgentLayer().getUpdateManager();

        // Clone parent.
        Agent child = identity.getSelf().copy();

        // Place child in parent location.
        u.place(child, vacancy);
    }
}
