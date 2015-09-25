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

package nanoverse.runtime.factory.agent.action;

import nanoverse.runtime.agent.action.*;
import nanoverse.runtime.agent.action.stochastic.ProbabilitySupplier;
import nanoverse.runtime.cells.BehaviorCell;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.DynamicActionRangeMapDescriptor;
import nanoverse.runtime.control.arguments.ProbabilitySupplierDescriptor;
import nanoverse.runtime.factory.agent.BehaviorDescriptorFactory;
import nanoverse.runtime.factory.agent.action.stochastic.ProbabilitySupplierDescriptorFactory;
import nanoverse.runtime.layers.LayerManager;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Constructs an action chooser, which is used in construting a StochasticChoice
 * object. Each choice has a weighting and one or more actions to be executed in
 * sequence. If more than one action is specified, the actions are executed in
 * the order specified whenever the option is chosen.
 * <p>
 * For examples, see
 * Created by dbborens on 3/6/14.
 */
public abstract class DynamicActionRangeMapDescriptorFactory {

    public static DynamicActionRangeMapDescriptor instantiate(Element base, LayerManager layerManager, GeneralParameters p) {


        Map<ActionDescriptor, ProbabilitySupplierDescriptor> prototypes = new HashMap<>();

        for (Object o : base.elements("option")) {
            Element option = (Element) o;
            ActionDescriptor action = getAction(option, layerManager, p);
            ProbabilitySupplierDescriptor supplier = ProbabilitySupplierDescriptorFactory.instantiate(option, layerManager);
            prototypes.put(action, supplier);
//            chooser.add(action, supplier);
        }

        Function<BehaviorCell, DynamicActionRangeMap> fn = cell -> {
            DynamicActionRangeMap chooser = new DynamicActionRangeMap(layerManager);
            prototypes.keySet()
                    .stream()
                    .forEach(actionDescriptor -> {
                        Action action = actionDescriptor.instantiate(cell);
                        ProbabilitySupplierDescriptor supplierDescriptor = prototypes.get(actionDescriptor);
                        ProbabilitySupplier supplier = supplierDescriptor.instantiate(cell);
                        chooser.add(action, supplier);
                    });
            return chooser;
        };

        return new DynamicActionRangeMapDescriptor(fn);
    }

    private static ActionDescriptor getAction(Element option, LayerManager layerManager, GeneralParameters p) {
        Element actionElement = option.element("action");
        if (actionElement == null) {
            throw new IllegalArgumentException("Incomplete descriptor: " + option.asXML());
        }
        List elements = actionElement.elements();
        if (elements.size() == 0) {
            return new NullActionDescriptor();
        } else if (elements.size() == 1) {
            Element child = (Element) actionElement.elements().iterator().next();
            return ActionDescriptorFactory.instantiate(child, layerManager, p);
        } else {
            return BehaviorDescriptorFactory.instantiate(actionElement, layerManager, p);
        }
    }

}
