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
import nanoverse.runtime.agent.action.TriggerDescriptor;
import nanoverse.runtime.agent.targets.TargetDescriptor;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.layers.LayerManager;

public class TriggerActionFactory implements Factory<TriggerDescriptor> {

    private final TriggerFactoryHelper helper;

    private LayerManager layerManager;
    private String behaviorName;
    private TargetDescriptor ruleDescriptor;
    private IntegerArgument selfChannel;
    private IntegerArgument targetChannel;

    public TriggerActionFactory() {
        helper = new TriggerFactoryHelper();
    }

    public TriggerActionFactory(TriggerFactoryHelper helper) {
        this.helper = helper;
    }

    public void setLayerManager(LayerManager layerManager) {
        this.layerManager = layerManager;
    }

    public void setBehaviorName(String behaviorName) {
        this.behaviorName = behaviorName;
    }

    public void setRuleDescriptor(TargetDescriptor ruleDescriptor) {
        this.ruleDescriptor = ruleDescriptor;
    }

    public void setSelfChannel(IntegerArgument selfChannel) {
        this.selfChannel = selfChannel;
    }

    public void setTargetChannel(IntegerArgument targetChannel) {
        this.targetChannel = targetChannel;
    }

    @Override
    public TriggerDescriptor build() {
        return helper.build(layerManager, behaviorName, ruleDescriptor, selfChannel, targetChannel);
    }
}