/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein and the
 * Trustees of Princeton University.
 *
 * This file is part of the Nanoverse simulation framework
 * (patent pending).
 *
 * This program is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Affero General
 * Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */
package nanoverse.compiler.pipeline.instantiate.factory.agent.action;

import nanoverse.runtime.agent.action.ExpandDescriptor;
import nanoverse.runtime.control.arguments.IntegerArgument;
import java.util.Random;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.compiler.pipeline.instantiate.factory.Factory;

public class ExpandFactory implements Factory<ExpandDescriptor> {

    private final ExpandFactoryHelper helper;

    private LayerManager layerManager;
    private IntegerArgument selfChannel;
    private IntegerArgument targetChannel;
    private Random random;

    public ExpandFactory() {
        helper = new ExpandFactoryHelper();
    }

    public ExpandFactory(ExpandFactoryHelper helper) {
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
    public ExpandDescriptor build() {
        return helper.build(layerManager, selfChannel, targetChannel, random);
    }
}