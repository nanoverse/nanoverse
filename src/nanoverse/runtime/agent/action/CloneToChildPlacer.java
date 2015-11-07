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

package nanoverse.runtime.agent.action;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.action.helper.*;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.*;

/**
 * Created by dbborens on 10/20/2015.
 */
public class CloneToChildPlacer {

    private final SelfTargetHighlighter highlighter;
    private final CoordAgentMapper mapper;

    private final boolean noReplace;

    public CloneToChildPlacer(SelfTargetHighlighter highlighter,
                              CoordAgentMapper mapper,
                              boolean noReplace) {

        this.highlighter = highlighter;
        this.mapper = mapper;
        this.noReplace = noReplace;
    }

    public void place(Coordinate self, Coordinate target, Agent child) throws HaltCondition {

        AgentUpdateManager updater = getUpdater();
        AgentLayerViewer viewer = getViewer();

        // Place replicate at target site
        if (!viewer.isOccupied(target)) {
            updater.place(child, target);
        } else {
            replaceIfAppropriate(target, child);
        }

        // Highlight sites
        highlighter.highlight(target, self);
    }

    private void replaceIfAppropriate(Coordinate target, Agent child) throws HaltCondition {
        if (noReplace) {
            doThrow();
        }

        replace(target, child);
    }

    private void replace(Coordinate target, Agent child) throws HaltCondition {
        AgentUpdateManager updater = getUpdater();
        updater.banish(target);
        updater.place(child, target);

    }

    private void doThrow() {
        throw new IllegalStateException("In CloneTo: Attempted to " +
            "place child at occupied position (leading to " +
            "replacement), but <no-replacment /> flag is set.");
    }

    private AgentUpdateManager getUpdater() {
        return mapper
            .getLayerManager()
            .getAgentLayer()
            .getUpdateManager();
    }

    private AgentLayerViewer getViewer() {
        return mapper
            .getLayerManager()
            .getAgentLayer()
            .getViewer();
    }

    public boolean isNoReplace() {
        return noReplace;
    }

    public IntegerArgument getSelfChannel() {
        return highlighter.getSelfChannel();
    }

    public IntegerArgument getTargetChannel() {
        return highlighter.getTargetChannel();
    }
}
