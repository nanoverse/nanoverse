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

package factory.agent;

import agent.action.*;
import cells.BehaviorCell;
import control.GeneralParameters;
import factory.agent.action.ActionDescriptorFactory;
import layers.LayerManager;
import org.dom4j.Element;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by David B Borenstein on 1/23/14.
 */
public abstract class BehaviorDescriptorFactory {

    public static BehaviorDescriptor instantiate(Element e, LayerManager layerManager, GeneralParameters p) {

        ActionDescriptor[] actionSequence = getActionSequence(e, layerManager, p);

        Function<BehaviorCell, Action> fn = cell -> {
            Action[] actions = instantiateAll(actionSequence, cell);
            return new CompoundAction(cell, layerManager, actions);
        };
        return new BehaviorDescriptor(fn);
    }

    private static ActionDescriptor[] getActionSequence(Element e, LayerManager layerManager, GeneralParameters p) {
        List<Object> elements = e.elements();
        ActionDescriptor[] ret = new ActionDescriptor[elements.size()];
        elements.stream()
                .map(o -> (Element) o)
                .map(actionElem -> ActionDescriptorFactory.instantiate(actionElem, layerManager, p))
                .collect(Collectors.toList()).toArray(ret);

        return ret;
    }

    private static Action[] instantiateAll(ActionDescriptor[] descriptors, BehaviorCell cell) {
        Action[] ret = new Action[descriptors.length];

        Arrays.asList(descriptors)
                .stream()
                .map(descriptor -> descriptor.instantiate(cell))
                .collect(Collectors.toList()).toArray(ret);

        return ret;
    }
}
