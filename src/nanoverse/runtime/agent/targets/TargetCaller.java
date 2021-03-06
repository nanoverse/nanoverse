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

import java.util.*;

/**
 * Targets specify which agents should receive the consequences
 * of an Action.
 * Created by dbborens on 2/7/14.
 */
public class TargetCaller extends TargetRule {
    public TargetCaller(Agent callback, LayerManager layerManager, Filter filter, Random random) {
        super(callback, layerManager, filter, random);
    }

    @Override
    protected List<Coordinate> getCandidates(Agent caller) {
        Coordinate coord = layerManager.getAgentLayer().getLookupManager().getAgentLocation(caller);

        ArrayList<Coordinate> ret = new ArrayList<>(1);
        ret.add(coord);
        return ret;
    }

    @Override
    public TargetRule copy(Agent child) {
        return new TargetCaller(child, layerManager, filter, random);
    }
}
