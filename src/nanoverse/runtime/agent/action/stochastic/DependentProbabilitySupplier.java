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

package nanoverse.runtime.agent.action.stochastic;

import nanoverse.runtime.agent.BehaviorAgent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;

import java.util.function.*;

/**
 * This is part of a cloodge to make it so that StochasticChoice can read the state
 * of solute fields. As such, it is not very carefully written. This will be replaced
 * when more flexible state objects are made available.
 * <p>
 * Created by dbborens on 1/9/15.
 */
public class DependentProbabilitySupplier extends ProbabilitySupplier {

    private double coefficient;
    private double offset;
    private BehaviorAgent cell;
    private Function<BehaviorAgent, Double> valueLookup;

    public DependentProbabilitySupplier(Function<BehaviorAgent, Double> valueLookup, BehaviorAgent cell, double coefficient, double offset) {
        this.coefficient = coefficient;
        this.offset = offset;
        this.cell = cell;
        this.valueLookup = valueLookup;
    }

    private static double getFieldValueAt(BehaviorAgent cell, LayerManager layerManager, String fieldName) {

        Supplier<Coordinate> supplier = () -> layerManager
            .getAgentLayer()
            .getLookupManager()
            .getAgentLocation(cell);

        double value = layerManager.getContinuumLayer(fieldName).getLinker().get(supplier);
        return value;
    }

    @Override
    public DependentProbabilitySupplier clone(BehaviorAgent child) {
        return new DependentProbabilitySupplier(valueLookup, child, coefficient, offset);
    }

    @Override
    public Double get() {
        double value = valueLookup.apply(cell);
        return (coefficient * value) + offset;
    }
}
