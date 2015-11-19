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

package nanoverse.runtime.agent.targets;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.discrete.filter.Filter;
import org.slf4j.*;

import java.util.*;

/**
 * Targets specify which agents should receive the consequences
 * of an Action.
 * <p>
 * NOTE: Do not confuse 'callback' and 'caller.' The callback
 * is the cell associated with this Targeter object (i.e., whose
 * behavior is being described.) The caller is the cell that
 * triggered the behavior, if any.
 * <p>
 * Created by dbborens on 2/7/14.
 */
public abstract class TargetRule {

    protected final Random random;
    protected final Agent callback;
    protected final LayerManager layerManager;
    protected final Filter filter;
    private final Logger logger = LoggerFactory.getLogger(TargetRule.class);

    /**
     * @param callback     The cell whose behavior is being described
     * @param layerManager
     */
    public TargetRule(Agent callback, LayerManager layerManager, Filter filter, Random random) {
        this.callback = callback;
        this.layerManager = layerManager;
        this.random = random;
        this.filter = filter;
    }

    /**
     * Returns the list of target coordinates that satisfy
     * the action's target descriptor.
     *
     * @param caller The cell that triggered the action.
     */
    public List<Coordinate> report(Agent caller) {
        List<Coordinate> candidates = getCandidates(caller);
        List<Coordinate> targets = filter.apply(candidates);
        logger.debug("Reporting {} targets from an original set of {} candidates.", targets.size(), candidates.size());
        return targets;
    }
    protected abstract List<Coordinate> getCandidates(Agent caller);

    public abstract TargetRule copy(Agent child);

    public Agent getCallback() {
        return callback;
    }
}
