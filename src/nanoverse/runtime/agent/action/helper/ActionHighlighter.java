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
