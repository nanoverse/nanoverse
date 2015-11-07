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
import nanoverse.runtime.agent.targets.*;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.Random;
import java.util.function.Function;

/**
 * Created by dbborens on 8/3/2015.
 */
public class ExpandToDescriptor extends ActionDescriptor<ExpandTo> {

    private final Function<Agent, ExpandTo> constructor;

    @FactoryTarget(displayName = "ExpandTo")
    public ExpandToDescriptor(LayerManager layerManager,
                              TargetDescriptor ruleDescriptor,
                              IntegerArgument selfChannel,
                              IntegerArgument targetChannel, Random
                                      random) {

        constructor = cell -> {
            TargetRule targetRule = ruleDescriptor.instantiate(cell);
            return new ExpandTo(cell, layerManager, targetRule,
                selfChannel, targetChannel, random);
        };
    }

    @Override
    protected Function<Agent, ExpandTo> resolveConstructor() {
        return constructor;
    }
}
