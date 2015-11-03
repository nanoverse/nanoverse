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

import nanoverse.runtime.control.halt.*;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.Random;

/**
 * Created by dbborens on 10/20/2015.
 */
public class VacancyChooser {

    private final AgentLayer layer;
    private final Random random;

    public VacancyChooser(AgentLayer layer, Random random) {
        this.layer = layer;
        this.random = random;
    }

    /**
     * Gets the set of all nearest vacancies to the agent, and chooses randomly
     * between them.
     *
     * @param origin
     * @return
     * @throws HaltCondition
     */
    public Coordinate chooseVacancy(Coordinate origin) throws HaltCondition {
        Coordinate target;
        // Get nearest vacancies to the agent
        Coordinate[] targets = layer.getLookupManager().getNearestVacancies(origin, -1);
        if (targets.length == 0) {
            throw new LatticeFullEvent();
        } else {
            int i = random.nextInt(targets.length);
            target = targets[i];
        }

        return target;
    }
}
