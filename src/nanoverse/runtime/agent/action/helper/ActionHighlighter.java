package nanoverse.runtime.agent.action.helper;

import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.StepState;

/**
 * Created by dbborens on 10/18/2015.
 */
public class ActionHighlighter {
    private final LayerManager layerManager;

    public ActionHighlighter(LayerManager layerManager) {
        this.layerManager = layerManager;
    }

    public void doHighlight(IntegerArgument channelArg, Coordinate toHighlight) throws HaltCondition {
        // If not using highlights, do nothing
        if (channelArg == null) {
            return;
        }

        if (!layerManager.getAgentLayer().getGeometry().isInBounds(toHighlight)) {
            return;
        }

        Integer channel = channelArg.next();
        StepState stepState = layerManager.getStepState();
        stepState.highlight(toHighlight, channel);
    }
}
