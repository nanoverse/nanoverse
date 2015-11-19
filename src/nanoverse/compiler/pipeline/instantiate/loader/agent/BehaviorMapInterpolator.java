package nanoverse.compiler.pipeline.instantiate.loader.agent;

import nanoverse.compiler.pipeline.instantiate.loader.agent.action.FlexibleActionLoader;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.runtime.agent.action.ActionDescriptor;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 11/19/2015.
 */
public class BehaviorMapInterpolator {
    private final FlexibleActionLoader loader;

    public BehaviorMapInterpolator(FlexibleActionLoader loader) {
        this.loader = loader;
    }

    public BehaviorMapInterpolator() {
        loader = new FlexibleActionLoader();
    }

    public ActionDescriptor load(String id,
                                 DictionaryObjectNode node,
                                 LayerManager lm,
                                 GeneralParameters p) {

        ObjectNode child = node.getMember(id);
        return loader.load(child, lm, p);
    }
}
