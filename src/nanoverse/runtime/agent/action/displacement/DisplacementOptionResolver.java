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

import nanoverse.runtime.agent.action.helper.CoordAgentMapper;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;

/**
 * Created by dbborens on 10/21/2015.
 */
public class DisplacementOptionResolver {

    private final DisplacementManager displacementManager;
    private final CoordAgentMapper mapper;

    public DisplacementOptionResolver(DisplacementManager displacementManager, CoordAgentMapper mapper) {
        this.displacementManager = displacementManager;
        this.mapper = mapper;
    }

    public DisplacementOption getOption(Coordinate start) throws HaltCondition {
        Geometry geom = mapper.getLayerManager().getAgentLayer().getGeometry();
        Coordinate end = displacementManager.chooseVacancy(start);
        int distance = geom.getL1Distance(start, end, Geometry.APPLY_BOUNDARIES);

        DisplacementOption ret = new DisplacementOption(start, end, distance);

        return ret;
    }
}
