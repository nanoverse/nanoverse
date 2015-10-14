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
package nanoverse.compiler.pipeline.instantiate.factory.agent.action.stochastic;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.agent.action.stochastic.NeighborhoodProbabilitySupplierDescriptor;
import nanoverse.runtime.layers.cell.AgentLayer;

public class NeighborhoodProbabilitySupplierFactory implements Factory<NeighborhoodProbabilitySupplierDescriptor> {

    private final NeighborhoodProbabilitySupplierFactoryHelper helper;

    private double coefficient;
    private double offset;
    private AgentLayer layer;

    public NeighborhoodProbabilitySupplierFactory() {
        helper = new NeighborhoodProbabilitySupplierFactoryHelper();
    }

    public NeighborhoodProbabilitySupplierFactory(NeighborhoodProbabilitySupplierFactoryHelper helper) {
        this.helper = helper;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public void setLayer(AgentLayer layer) {
        this.layer = layer;
    }

    @Override
    public NeighborhoodProbabilitySupplierDescriptor build() {
        return helper.build(coefficient, offset, layer);
    }
}