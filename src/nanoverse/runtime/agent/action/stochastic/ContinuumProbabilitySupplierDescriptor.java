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

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.arguments.ProbabilitySupplierDescriptor;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.function.*;

/**
 * Created by dbborens on 8/25/2015.
 */
public class ContinuumProbabilitySupplierDescriptor extends ProbabilitySupplierDescriptor<ContinuumProbabilitySupplier> {

    @FactoryTarget(displayName = "ContinuumProbabilitySupplier")
    public ContinuumProbabilitySupplierDescriptor(String layer,
                                                  double coefficient,
                                                  double offset,
                                                  LayerManager layerManager) {

        Function<Agent, Double> valueLookup = c -> getFieldValueAt(c, layerManager, layer);
        Function<Agent, ContinuumProbabilitySupplier> constructor = cell -> new ContinuumProbabilitySupplier(valueLookup,
            cell, coefficient, offset);

        super.setConstructor(constructor);
    }

    private double getFieldValueAt(Agent cell, LayerManager layerManager, String fieldName) {

        Supplier<Coordinate> supplier = () -> layerManager
            .getAgentLayer()
            .getLookupManager()
            .getAgentLocation(cell);

        double value = layerManager.getContinuumLayer(fieldName).getLinker().get(supplier);
        return value;
    }
}
