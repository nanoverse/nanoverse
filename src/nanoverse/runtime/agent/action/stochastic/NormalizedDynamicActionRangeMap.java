package nanoverse.runtime.agent.action.stochastic;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.action.*;
import nanoverse.runtime.layers.LayerManager;

import java.util.Map;

/**
 * Created by dbborens on 11/6/2015.
 */
public class NormalizedDynamicActionRangeMap extends DynamicActionRangeMap {

    public NormalizedDynamicActionRangeMap(Map<Action, ProbabilitySupplier> functionMap, LayerManager layerManager) {
        super(functionMap, layerManager);
    }

    public NormalizedDynamicActionRangeMap(LayerManager layerManager) {
        super(layerManager);
    }

    @Override
    public void refresh() {
        super.refresh();
        normalize();
    }

    private void normalize() {
        double weight = getTotalWeight();

        if (getTotalWeight() > 1.0) {
            throw new IllegalStateException("Total probability exceeds 1.0 " +
                "in normalized stochastic choice. Did you mean to use a " +
                "weighted stochastic choice?");
        }

        double complement = 1.0 - weight;
        Action nullAction = new NullAction();
        valueMap.add(nullAction, complement);
    }

    @Override
    public Action selectTarget(double x) {
        if (x > 1.0) {
            throw new IllegalStateException("System error: attempted to " +
                "select a random event outside of defined domain.");
        }
        return super.selectTarget(x);
    }
}
