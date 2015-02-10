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

package factory.control.arguments;

import control.GeneralParameters;
import control.arguments.*;
import factory.agent.BehaviorDescriptorFactory;
import factory.cell.Reaction;
import factory.cell.ReactionFactory;
import layers.LayerManager;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Created by dbborens on 11/23/14.
 */
public abstract class CellDescriptorFactory {

    private static final int DEFAULT_STATE = 1;
    private static final double DEFAULT_INIT_HEALTH = 0.5;
    private static final double DEFAULT_THRESHOLD = 1.0;

    public static CellDescriptor instantiate(Element e, LayerManager layerManager, GeneralParameters p) {
        if (e == null) {
            return makeDefault(layerManager, p);
        }
        CellDescriptor cellDescriptor = new CellDescriptor(layerManager);
        setCellState(e, cellDescriptor, p.getRandom());
        setThreshold(e, cellDescriptor, p.getRandom());
        setInitialHealth(e, cellDescriptor, p.getRandom());
        loadReactions(e, cellDescriptor);
        loadBehaviors(e, cellDescriptor, layerManager, p);

        return cellDescriptor;
    }

    private static void loadBehaviors(Element e, CellDescriptor cellDescriptor, LayerManager layerManager, GeneralParameters p) {
        Element behaviorElem = e.element("behaviors");

        // No behaviors? No problem.
        if (behaviorElem == null) {
            cellDescriptor.setBehaviorDescriptors(new HashMap<>(0));
            return;
        }

        List<Object> elements = behaviorElem.elements();
        HashMap<String, BehaviorDescriptor> behaviorDescriptors = new HashMap<>(elements.size());
        elements.stream()
                .map(o -> (Element) o)
                .forEach(element -> {
                    String name = element.getName();

                    BehaviorDescriptor behaviorDescriptor = BehaviorDescriptorFactory.instantiate(element, layerManager, p);
                    behaviorDescriptors.put(name, behaviorDescriptor);
                });

        cellDescriptor.setBehaviorDescriptors(behaviorDescriptors);
    }


    private static void loadReactions(Element e, CellDescriptor cellDescriptor) {
        Element reactions = e.element("reactions");
        if (reactions == null) {
            return;
        }

        List<Object> elements = reactions.elements();

        Stream<Reaction> reactionStream = elements.stream()
                .map(x -> (Element) x)
                .map(ReactionFactory::instantiate);

        cellDescriptor.setReactions(reactionStream);
    }

    public static CellDescriptor makeDefault(LayerManager layerManager, GeneralParameters p) {
        CellDescriptor cellDescriptor = new CellDescriptor(layerManager);
        cellDescriptor.setCellState(new ConstantInteger(DEFAULT_STATE));
        cellDescriptor.setThreshold(new ConstantDouble(DEFAULT_THRESHOLD));
        cellDescriptor.setInitialHealth(new ConstantDouble(DEFAULT_INIT_HEALTH));
        return cellDescriptor;
    }

    private static void setInitialHealth(Element e, CellDescriptor cellDescriptor, Random random) {
        Argument<Double> initialHealth = DoubleArgumentFactory.instantiate(e, "initial-health", DEFAULT_INIT_HEALTH, random);
        cellDescriptor.setInitialHealth(initialHealth);
    }

    private static void setThreshold(Element e, CellDescriptor cellDescriptor, Random random) {
        Argument<Double> threshold = DoubleArgumentFactory.instantiate(e, "threshold", DEFAULT_THRESHOLD, random);
        cellDescriptor.setThreshold(threshold);
    }

    private static void setCellState(Element e, CellDescriptor cellDescriptor, Random random) {
        Argument<Integer> cellState = IntegerArgumentFactory.instantiate(e, "state", DEFAULT_STATE, random);
        cellDescriptor.setCellState(cellState);
    }


}
