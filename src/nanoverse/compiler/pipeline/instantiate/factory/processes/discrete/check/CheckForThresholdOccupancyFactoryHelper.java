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
package nanoverse.compiler.pipeline.instantiate.factory.processes.discrete.check;

import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.runtime.processes.discrete.AgentProcessArguments;
import nanoverse.runtime.processes.discrete.check.CheckForThresholdOccupancy;


public class CheckForThresholdOccupancyFactoryHelper {

    public CheckForThresholdOccupancy build(BaseProcessArguments arguments, AgentProcessArguments cpArguments, DoubleArgument thresholdOccupancy) {
        return new CheckForThresholdOccupancy(arguments, cpArguments, thresholdOccupancy);
    }
}