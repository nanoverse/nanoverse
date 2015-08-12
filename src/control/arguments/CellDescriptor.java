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

package control.arguments;

import agent.Behavior;
import agent.action.BehaviorDescriptor;
import agent.control.BehaviorDispatcher;
import cells.BehaviorCell;
import cells.Cell;
import control.halt.HaltCondition;
import layers.continuum.Reaction;
import layers.LayerManager;
import structural.annotations.FactoryTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by dbborens on 11/23/14.
 */
public class CellDescriptor extends Argument<Cell> {

    private LayerManager layerManager;

    private IntegerArgument cellState;

    private DoubleArgument threshold;
    private DoubleArgument initialHealth;

    private List<Reaction> reactions;
    private Map<String, BehaviorDescriptor> behaviorDescriptors;

    @FactoryTarget(displayName = "AgentDescriptor")
    public CellDescriptor(LayerManager layerManager,
                          IntegerArgument cellState,
                          DoubleArgument threshold,
                          DoubleArgument initialHealth,
                          List<Reaction> reactions,
                          Map<String, BehaviorDescriptor> behaviorDescriptors) {

        this.layerManager = layerManager;
        this.cellState = cellState;
        this.threshold = threshold;
        this.initialHealth = initialHealth;
        this.reactions = reactions;
        this.behaviorDescriptors = behaviorDescriptors;
    }

    public CellDescriptor(LayerManager layerManager) {
        this.layerManager = layerManager;
        reactions = new ArrayList<>(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CellDescriptor that = (CellDescriptor) o;

        //if (!behaviorRoot.equals(that.behaviorRoot)) return false;
        if (!cellState.equals(that.cellState)) return false;
        if (!initialHealth.equals(that.initialHealth)) return false;
        if (!threshold.equals(that.threshold)) return false;

        return true;
    }

    @Override
    public BehaviorCell next() throws HaltCondition {
        // Load cell properties
        double initialHealthValue = initialHealth.next();
        double thresholdValue = threshold.next();
        int stateValue = cellState.next();

        Supplier<BehaviorCell> supplier = () -> {
            try {
                return next();
            } catch (HaltCondition ex) {
                throw new RuntimeException();
            }
        };

        // Construct cell
        BehaviorCell cell = new BehaviorCell(layerManager, stateValue, initialHealthValue, thresholdValue, supplier);

        loadReactions(cell);
        loadBehaviors(cell);
        // Return completed object
        return cell;
    }

    public void setCellState(IntegerArgument cellState) {
        this.cellState = cellState;
    }

    public void setThreshold(DoubleArgument threshold) {
        this.threshold = threshold;
    }

    public void setInitialHealth(DoubleArgument initialHealth) {
        this.initialHealth = initialHealth;
    }

    private void loadReactions(BehaviorCell cell) {
        reactions.forEach(cell::load);
    }

    private void loadBehaviors(BehaviorCell cell) {
        BehaviorDispatcher dispatcher = new BehaviorDispatcher();

        behaviorDescriptors.keySet()
                .stream()
                .forEach(name -> {
                    BehaviorDescriptor descriptor = behaviorDescriptors.get(name);
                    Behavior behavior = descriptor.instantiate(cell);
                    dispatcher.map(name, behavior);
                });

        cell.setDispatcher(dispatcher);
    }

    public void setReactions(Stream<Reaction> reactions) {
        this.reactions = reactions.collect(Collectors.toList());
    }

    public void setBehaviorDescriptors(Map<String, BehaviorDescriptor> behaviorDescriptors) {
        this.behaviorDescriptors = behaviorDescriptors;
    }
}
