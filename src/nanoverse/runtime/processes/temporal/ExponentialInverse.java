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

package nanoverse.runtime.processes.temporal;

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.processes.*;

/**
 * Simple stochastic process for time scaling. Assumes all agents
 * are growing at the same rate: 1 cell per arbitrary unit. So
 * choose an exponentially distributed random number whose lambda
 * is inversely proportional to the number of agents. This is
 * equivalent to a Gillespie process in the case where cell division
 * is the only process and where all agents have an equal
 * probability of dividing.
 *
 * @author dbborens
 */
public class ExponentialInverse extends TimeProcess {

    public ExponentialInverse(BaseProcessArguments arguments) {
        super(arguments);
    }

    @Override
    public void fire(StepState state) throws HaltCondition {
        double lambda = 1.0D / getLayerManager().getAgentLayer().getViewer().getOccupiedSites().count();
        double dt = expRandom(lambda);
        state.advanceClock(dt);
    }

    @Override
    public void init() {
    }

}
