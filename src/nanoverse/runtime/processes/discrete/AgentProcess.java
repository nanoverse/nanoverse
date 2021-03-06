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

package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.geometry.set.CoordinateSet;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.processes.*;

public abstract class AgentProcess extends NanoverseProcess {
    // These are easily accessed from the layer manager, but there
    // are very many calls to them thanks to some legacy code.
    private final AgentProcessArguments cpArguments;
    private final BaseProcessArguments arguments;

    public AgentProcess(BaseProcessArguments arguments, AgentProcessArguments cpArguments) {
        super(arguments);
        this.cpArguments = cpArguments;
        this.arguments = arguments;
    }

    protected AgentLayer getLayer() {
        return arguments.getLayerManager().getAgentLayer();
    }

    protected CoordinateSet getActiveSites() {
        return cpArguments.getActiveSites();
    }

    protected IntegerArgument getMaxTargets() {
        return cpArguments.getMaxTargets();
    }
}
