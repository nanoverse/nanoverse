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
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.function.Function;

/**
 * Created by dbborens on 8/3/2015.
 */
public class DieDescriptor extends ActionDescriptor<Die> {

    private final Function<Agent, Die> constructor;

    @FactoryTarget(displayName = "Die")
    public DieDescriptor(LayerManager layerManager, IntegerArgument channel) {
        constructor = cell -> new Die(cell, layerManager, channel);
    }

    @Override
    protected Function<Agent, Die> resolveConstructor() {
        return constructor;
    }

}
