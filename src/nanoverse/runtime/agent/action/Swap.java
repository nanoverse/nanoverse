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
import nanoverse.runtime.agent.targets.TargetRule;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;

import java.util.List;

/**
 * Causes the callback cell to swap locations with a neighbor.
 * <p>
 * Created by dbborens on 5/26/14.
 */
public class Swap extends Action {
    private final TargetRule targetRule;
    private final IntegerArgument selfChannel;
    private final IntegerArgument targetChannel;

    public Swap(Agent callback, LayerManager layerManager,
                TargetRule targetRule, IntegerArgument selfChannel,
                IntegerArgument targetChannel) {
        super(callback, layerManager);
        this.targetRule = targetRule;
        this.selfChannel = selfChannel;
        this.targetChannel = targetChannel;
    }

    /**
     * Testing constructor
     */
    public Swap(ActionIdentityManager identity,
                CoordAgentMapper mapper,
                ActionHighlighter highlighter,
                TargetRule targetRule,
                IntegerArgument selfChannel,
                IntegerArgument targetChannel) {

        super(identity, mapper, highlighter);
        this.targetRule = targetRule;
        this.targetChannel = targetChannel;
        this.selfChannel = selfChannel;
    }

    @Override
    public void run(Coordinate caller) throws HaltCondition {
        Agent callerAgent = mapper.resolveCaller(caller);
        Coordinate self = identity.getOwnLocation();
        List<Coordinate> targets = targetRule.report(callerAgent);

        if (targets.size() != 1) {
            throw new IllegalStateException("Swap action requires exactly one " +
                "target per event.");
        }

        Coordinate target = targets.get(0);

        mapper.getLayerManager().getAgentLayer().getUpdateManager().swap(self, target);

        highlighter.doHighlight(selfChannel, self);
        highlighter.doHighlight(targetChannel, target);
    }

    @Override
    public Action copy(Agent child) {
        TargetRule clonedTargetRule = targetRule.copy(child);
        return new Swap(child, mapper.getLayerManager(), clonedTargetRule, selfChannel, targetChannel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Swap)) return false;

        Swap swap = (Swap) o;

        if (!targetRule.equals(swap.targetRule)) return false;

        return true;
    }
}
