/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein and the
 * Trustees of Princeton University.
 *
 * This file is part of the Nanoverse simulation framework
 * (patent pending).
 *
 * This program is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Affero General
 * Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package nanoverse.runtime.agent.action;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.action.helper.*;
import nanoverse.runtime.agent.targets.TargetRule;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;

import java.util.*;

/**
 * Places a copy or copies of the current cell at the target site(s).
 * This uses the "replicate" method, meaning that the state of the cell is
 * exactly preserved.
 * <p>
 * Created by dbborens on 5/2/14.
 */
public class CloneTo extends Action {

    private final TargetRule targetRule;
    private final CloneToChildPlacer childPlacer;
    private final Random random;

    /**
     * Main constructor
     */
    public CloneTo(Agent agent, LayerManager layerManager,
                   TargetRule targetRule, boolean noReplace,
                   IntegerArgument selfChannel,
                   IntegerArgument targetChannel, Random random) {

        super(agent, layerManager);
        this.targetRule = targetRule;
        this.random = random;

        SelfTargetHighlighter stHighlighter =
            new SelfTargetHighlighter(highlighter, selfChannel, targetChannel);

        childPlacer = new CloneToChildPlacer(stHighlighter,
            mapper, noReplace);
    }

    public CloneTo(ActionIdentityManager identity, CoordAgentMapper mapper, ActionHighlighter highlighter, TargetRule targetRule, CloneToChildPlacer childPlacer, Random random) {
        super(identity, mapper, highlighter);
        this.targetRule = targetRule;
        this.childPlacer = childPlacer;
        this.random = random;
    }

    @Override
    public void run(Coordinate caller) throws HaltCondition {
        Agent callerAgent = mapper.resolveCaller(caller);

        Coordinate self = identity.getOwnLocation();

        List<Coordinate> targets = targetRule.report(callerAgent);


        for (Coordinate target : targets) {
            Agent child = identity.getSelf().copy();
            childPlacer.place(self, target, child);
        }

    }

    @Override
    public Action copy(Agent child) {
        TargetRule clonedTargeter = targetRule.copy(child);

        IntegerArgument selfChannel = childPlacer.getSelfChannel();
        IntegerArgument targetChannel = childPlacer.getTargetChannel();
        boolean noReplace = childPlacer.isNoReplace();

        return new CloneTo(child, mapper.getLayerManager(), clonedTargeter, noReplace,
            selfChannel, targetChannel, random);
    }
}
