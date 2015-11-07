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
import nanoverse.runtime.agent.action.displacement.DisplacementManager;
import nanoverse.runtime.agent.action.displacement.PreferentialExpansionManager;
import nanoverse.runtime.agent.action.helper.ActionHighlighter;
import nanoverse.runtime.agent.action.helper.ActionIdentityManager;
import nanoverse.runtime.agent.action.helper.CoordAgentMapper;
import nanoverse.runtime.agent.action.helper.SelfTargetHighlighter;
import nanoverse.runtime.agent.targets.TargetRule;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

/**
 * Places a copy or copies of the current cell at the target site(s).
 * This uses the "replicate" method, rather than the "divide" method, meaning
 * that the state of the cell is exactly preserved.
 * <p>
 * Created by dbborens on 5/2/14.
 */
public class ExpandTo extends Action {

    private final DisplacementManager displacementManager;
    private final SelfTargetHighlighter stHighlighter;
    private final PreferentialExpansionManager expansionManager;
    private final Random random;
    private final TargetRule targetRule;
    private final Logger logger = LoggerFactory.getLogger(ExpandTo.class);

    public ExpandTo(Agent callback, LayerManager layerManager, TargetRule targetRule,
                    IntegerArgument selfChannel, IntegerArgument targetChannel, Random random) {
        super(callback, layerManager);
        this.random = random;
        this.targetRule = targetRule;
        stHighlighter = new SelfTargetHighlighter(highlighter, selfChannel, targetChannel);
        displacementManager = new DisplacementManager(layerManager.getAgentLayer(), random);
        expansionManager = new PreferentialExpansionManager(identity, stHighlighter, mapper, random, displacementManager);
    }

    public ExpandTo(ActionIdentityManager identity, CoordAgentMapper mapper, ActionHighlighter highlighter, DisplacementManager displacementManager, SelfTargetHighlighter stHighlighter, Random random, TargetRule targetRule, PreferentialExpansionManager expansionManager) {
        super(identity, mapper, highlighter);
        this.displacementManager = displacementManager;
        this.stHighlighter = stHighlighter;
        this.random = random;
        this.targetRule = targetRule;
        this.expansionManager = expansionManager;
    }

    @Override
    public void run(Coordinate caller) throws HaltCondition {
        Agent callerAgent = mapper.resolveCaller(caller);
        List<Coordinate> targets = targetRule.report(callerAgent);
        for (Coordinate target : targets) {
            expansionManager.preferentialExpand(target);
        }
    }

    @Override
    public Action copy(Agent child) {
        IntegerArgument selfChannel = stHighlighter.getSelfChannel();
        IntegerArgument targetChannel = stHighlighter.getTargetChannel();

        TargetRule clonedTargetRule = targetRule.copy(child);
        return new ExpandTo(child, mapper.getLayerManager(), clonedTargetRule, selfChannel,
            targetChannel, random);
    }
}
