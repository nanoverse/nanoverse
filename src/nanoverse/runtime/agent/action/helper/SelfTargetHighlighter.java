package nanoverse.runtime.agent.action.helper;

import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;

/**
 * Created by dbborens on 10/20/2015.
 */
public class SelfTargetHighlighter {

    private final ActionHighlighter highlighter;
    private final IntegerArgument selfChannel;
    private final IntegerArgument targetChannel;

    public SelfTargetHighlighter(ActionHighlighter highlighter,
                                 IntegerArgument selfChannel,
                                 IntegerArgument targetChannel) {

        this.highlighter = highlighter;
        this.selfChannel = selfChannel;
        this.targetChannel = targetChannel;
    }

    public void highlight(Coordinate target, Coordinate ownLocation) throws HaltCondition {
        highlighter.doHighlight(targetChannel, target);
        highlighter.doHighlight(selfChannel, ownLocation);
    }

    public IntegerArgument getTargetChannel() {
        return targetChannel;
    }

    public IntegerArgument getSelfChannel() {
        return selfChannel;
    }
}
