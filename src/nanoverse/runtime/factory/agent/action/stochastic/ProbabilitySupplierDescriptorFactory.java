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

package nanoverse.runtime.factory.agent.action.stochastic;

import nanoverse.runtime.agent.action.stochastic.*;
import nanoverse.runtime.cells.BehaviorCell;
import nanoverse.runtime.control.arguments.ProbabilitySupplierDescriptor;
import nanoverse.runtime.layers.LayerManager;
import org.dom4j.Element;

import java.util.function.Function;

/**
 * Created by dbborens on 1/9/15.
 */
public abstract class ProbabilitySupplierDescriptorFactory {

    public static ProbabilitySupplierDescriptor instantiate(Element option, LayerManager layerManager) {
        Element weight = option.element("weight");
        if (weight == null) {
            throw new IllegalArgumentException("Missing required argument 'weight'");
        }
        if (weight.elements().size() == 0) {
            double value = Double.valueOf(weight.getTextTrim());
            Function<BehaviorCell, ProbabilitySupplier> fn = cell -> new ConstantProbabilitySupplier(value);
            return new ProbabilitySupplierDescriptor(fn);
        } else {
            return DependentProbabilitySupplierFactory.instantiate(weight, layerManager);
        }
    }
}
