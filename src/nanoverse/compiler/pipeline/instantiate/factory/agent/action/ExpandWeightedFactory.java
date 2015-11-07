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
package nanoverse.compiler.pipeline.instantiate.factory.agent.action;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.agent.action.ExpandWeightedDescriptor;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.layers.LayerManager;

import java.util.Random;

public class ExpandWeightedFactory implements Factory<ExpandWeightedDescriptor> {

    private final ExpandWeightedFactoryHelper helper;

    private LayerManager layerManager;
    private IntegerArgument selfChannel;
    private IntegerArgument targetChannel;
    private Random random;

    public ExpandWeightedFactory() {
        helper = new ExpandWeightedFactoryHelper();
    }

    public ExpandWeightedFactory(ExpandWeightedFactoryHelper helper) {
        this.helper = helper;
    }

    public void setLayerManager(LayerManager layerManager) {
        this.layerManager = layerManager;
    }

    public void setSelfChannel(IntegerArgument selfChannel) {
        this.selfChannel = selfChannel;
    }

    public void setTargetChannel(IntegerArgument targetChannel) {
        this.targetChannel = targetChannel;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    @Override
    public ExpandWeightedDescriptor build() {
        return helper.build(layerManager, selfChannel, targetChannel, random);
    }
}