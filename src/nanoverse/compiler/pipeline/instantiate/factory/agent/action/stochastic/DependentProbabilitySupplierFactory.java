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
import nanoverse.runtime.agent.action.stochastic.DependentProbabilitySupplierDescriptor;
import nanoverse.runtime.layers.LayerManager;

public class DependentProbabilitySupplierFactory implements Factory<DependentProbabilitySupplierDescriptor> {

    private final DependentProbabilitySupplierFactoryHelper helper;

    private String layer;
    private double coefficient;
    private double offset;
    private LayerManager layerManager;

    public DependentProbabilitySupplierFactory() {
        helper = new DependentProbabilitySupplierFactoryHelper();
    }

    public DependentProbabilitySupplierFactory(DependentProbabilitySupplierFactoryHelper helper) {
        this.helper = helper;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public void setLayerManager(LayerManager layerManager) {
        this.layerManager = layerManager;
    }

    @Override
    public DependentProbabilitySupplierDescriptor build() {
        return helper.build(layer, coefficient, offset, layerManager);
    }
}