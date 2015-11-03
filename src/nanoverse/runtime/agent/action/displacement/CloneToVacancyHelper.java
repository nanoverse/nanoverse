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

package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.action.helper.*;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentUpdateManager;

/**
 * Created by dbborens on 10/21/2015.
 */
public class CloneToVacancyHelper {
    private final ActionIdentityManager identity;
    private final CoordAgentMapper mapper;

    public CloneToVacancyHelper(ActionIdentityManager identity, CoordAgentMapper mapper) {
        this.identity = identity;
        this.mapper = mapper;
    }

    public void cloneToVacancy(Coordinate vacancy) throws HaltCondition {
        AgentUpdateManager u = mapper.getLayerManager().getAgentLayer().getUpdateManager();

        // Clone parent.
        Agent child = identity.getSelf().copy();

        // Place child in parent location.
        u.place(child, vacancy);
    }
}
