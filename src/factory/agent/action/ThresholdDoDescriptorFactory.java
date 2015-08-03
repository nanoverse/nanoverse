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

package factory.agent.action;

import agent.action.*;
import cells.BehaviorCell;
import control.GeneralParameters;
import control.arguments.*;
import factory.agent.BehaviorDescriptorFactory;
import factory.control.arguments.DoubleArgumentFactory;
import layers.LayerManager;
import org.dom4j.*;

import java.util.*;
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
public abstract class ThresholdDoDescriptorFactory {

    public static ActionDescriptor<ThresholdDo> instantiate(Element base, LayerManager layerManager,
                                                       GeneralParameters p) {

        ActionDescriptor child = getAction(base, layerManager, p);
        DoubleArgument minimumArg = DoubleArgumentFactory.instantiate(base, "minimum", Double.NEGATIVE_INFINITY, p.getRandom());
        DoubleArgument maximumArg = DoubleArgumentFactory.instantiate(base, "maximum", Double.POSITIVE_INFINITY, p.getRandom());
        String layerId = base.element("layer").getTextTrim();
        return new ThresholdDoDescriptor(layerManager, layerId, minimumArg, maximumArg, child);
    }

    private static ActionDescriptor getAction(Element option, LayerManager layerManager, GeneralParameters p) {
        Element actionElement = option.element("action");
        List elements = actionElement.elements();
        if (elements.size() == 0) {
            throw new IllegalArgumentException("Expected an action or list of actions for stochastic choice option.");
        } else if (elements.size() == 1) {
            Element child = (Element) actionElement.elements().iterator().next();
            return ActionDescriptorFactory.instantiate(child, layerManager, p);
        } else {
            return BehaviorDescriptorFactory.instantiate(actionElement, layerManager, p);
        }
    }

}
