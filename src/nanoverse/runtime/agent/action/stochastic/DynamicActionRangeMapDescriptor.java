package nanoverse.runtime.agent.action.stochastic;

import nanoverse.runtime.agent.Agent;

/**
 * Created by dbborens on 11/6/2015.
 */
public interface DynamicActionRangeMapDescriptor {
    DynamicActionRangeMap instantiate(Agent cell);
}
