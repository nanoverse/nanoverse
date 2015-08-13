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
package compiler.pipeline.instantiate.factory.control.arguments;

import java.util.List;

import control.arguments.Argument;
import control.arguments.IntegerArgument;
import control.arguments.DoubleArgument;
import java.util.Map;
import layers.LayerManager;
import control.arguments.CellDescriptor;
import compiler.pipeline.instantiate.factory.Factory;

public class AgentDescriptorFactory implements Factory<CellDescriptor> {

    private final AgentDescriptorFactoryHelper helper;

    private LayerManager layerManager;
    private Argument<Integer> cellState;
    private Argument<Double> threshold;
    private Argument<Double> initialHealth;
    private List reactions;
    private Map behaviorDescriptors;

    public AgentDescriptorFactory() {
        helper = new AgentDescriptorFactoryHelper();
    }

    public AgentDescriptorFactory(AgentDescriptorFactoryHelper helper) {
        this.helper = helper;
    }

    public void setLayerManager(LayerManager layerManager) {
        this.layerManager = layerManager;
    }

    public void setCellState(Argument<Integer> cellState) {
        this.cellState = cellState;
    }

    public void setThreshold(Argument<Double> threshold) {
        this.threshold = threshold;
    }

    public void setInitialHealth(Argument<Double> initialHealth) {
        this.initialHealth = initialHealth;
    }

    public void setReactions(List reactions) {
        this.reactions = reactions;
    }

    public void setBehaviorDescriptors(Map behaviorDescriptors) {
        this.behaviorDescriptors = behaviorDescriptors;
    }

    @Override
    public CellDescriptor build() {
        return helper.build(layerManager, cellState, threshold, initialHealth, reactions, behaviorDescriptors);
    }
}