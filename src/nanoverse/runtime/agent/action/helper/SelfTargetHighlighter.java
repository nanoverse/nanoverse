/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

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
