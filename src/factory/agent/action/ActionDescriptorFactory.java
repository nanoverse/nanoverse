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
import agent.targets.TargetRule;
import cells.BehaviorCell;
import control.GeneralParameters;
import control.arguments.ActionDescriptor;
import control.arguments.Argument;
import control.arguments.DynamicActionRangeMapDescriptor;
import control.arguments.TargetDescriptor;
import factory.agent.targets.TargetDesciptorFactory;
import factory.control.arguments.DoubleArgumentFactory;
import factory.control.arguments.IntegerArgumentFactory;
import layers.LayerManager;
import org.dom4j.Element;
import structural.utilities.XmlUtil;

import java.util.Random;
import java.util.function.Function;

/**
 * Created by David B Borenstein on 1/22/14.
 */
public abstract class ActionDescriptorFactory {

    public static ActionDescriptor instantiate(Element e, LayerManager layerManager, GeneralParameters p) {
        String actionName = e.getName();

        switch (actionName) {
            case "mock":
                return mockAction();
            case "die":
                return die(e, layerManager, p);
            case "adjust-health":
                return adjustHealth(e, layerManager);
            case "trigger":
                return trigger(e, layerManager, p);
            case "clone":
                return cloneTo(e, layerManager, p);
            case "expand":
                return expand(e, layerManager, p);
            case "expand-to":
                return expandTo(e, layerManager, p);
            case "expand-random":
                return expandRandom(e, layerManager, p);
            case "expand-weighted":
                return expandWeighted(e, layerManager, p);
            case "stochastic-choice":
                return stochasticChoice(e, layerManager, p);
            case "swap":
                return swap(e, layerManager, p);
            case "inject":
                return inject(e, layerManager, p);
            case "threshold-do":
                return ThresholdDoDescriptorFactory.instantiate(e, layerManager, p);
            case "null":
                return nullAction();
            default:
                String msg = "Unrecognized action '" + actionName + "'. In parent: " + e.getParent().asXML();
                throw new IllegalArgumentException(msg);
        }
    }

    private static ActionDescriptor inject(Element e, LayerManager layerManager, GeneralParameters p) {
        Argument<Double> deltaArg = DoubleArgumentFactory.instantiate(e, "delta", p.getRandom());
        String layerId = e.element("layer").getTextTrim();

        Function<BehaviorCell, Inject> fn = cell -> new Inject(cell, layerManager, layerId, deltaArg);
        return new ActionDescriptor<>(fn);
    }

    private static ActionDescriptor<NullAction> nullAction() {
        Function<BehaviorCell, NullAction> fn = cell -> new NullAction();
        return new ActionDescriptor<>(fn);
    }


    private static ActionDescriptor<StochasticChoice> stochasticChoice(Element e, LayerManager layerManager,
                                                                       GeneralParameters p) {

        Random random = p.getRandom();
        DynamicActionRangeMapDescriptor chooserPrototype = DynamicActionRangeMapDescriptorFactory.instantiate(e, layerManager, p);
        Function<BehaviorCell, StochasticChoice> fn = cell -> {
            DynamicActionRangeMap chooser = chooserPrototype.instantiate(cell);
            return new StochasticChoice(cell, layerManager, chooser, random);
        };
        return new ActionDescriptor<>(fn);
    }

    private static ActionDescriptor<MockAction> mockAction() {
        Function<BehaviorCell, MockAction> fn = cell -> new MockAction();
        return new ActionDescriptor<>(fn);
    }

    private static ActionDescriptor<Die> die(Element e, LayerManager layerManager, GeneralParameters p) {
        Argument<Integer> channel = IntegerArgumentFactory.instantiate(e, "highlight", -1, p.getRandom());
        Function<BehaviorCell, Die> fn = cell -> new Die(cell, layerManager, channel);
        return new ActionDescriptor<>(fn);
    }

    private static ActionDescriptor<Trigger> trigger(Element e, LayerManager layerManager, GeneralParameters p) {
        Element descriptor = e.element("target");
        TargetDescriptor targetDescriptor = TargetDesciptorFactory.instantiate(layerManager, descriptor, p);
        String behaviorName = e.element("behavior").getTextTrim();
        Argument<Integer> selfChannel = IntegerArgumentFactory.instantiate(e, "actor-highlight", -1, p.getRandom());
        Argument<Integer> targetChannel = IntegerArgumentFactory.instantiate(e, "target-highlight", -1, p.getRandom());
        Function<BehaviorCell, Trigger> fn = cell -> {
            TargetRule targetRule = targetDescriptor.instantiate(cell);
            return new Trigger(cell, layerManager, behaviorName, targetRule, selfChannel, targetChannel);
        };
        return new ActionDescriptor<>(fn);
    }

    private static ActionDescriptor<CloneTo> cloneTo(Element e, LayerManager layerManager, GeneralParameters p) {
        Element descriptor = e.element("target");
        TargetDescriptor targetDescriptor = TargetDesciptorFactory.instantiate(layerManager, descriptor, p);
        Argument<Integer> selfChannel = IntegerArgumentFactory.instantiate(e, "actor-highlight", -1, p.getRandom());
        Argument<Integer> targetChannel = IntegerArgumentFactory.instantiate(e, "target-highlight", -1, p.getRandom());
        boolean noReplace = XmlUtil.getBoolean(e, "no-replacement");
        Function<BehaviorCell, CloneTo> fn = cell -> {
            TargetRule targetRule = targetDescriptor.instantiate(cell);
            return new CloneTo(cell, layerManager, targetRule, noReplace, selfChannel, targetChannel, p.getRandom());
        };
        return new ActionDescriptor<>(fn);
    }

    private static ActionDescriptor<Swap> swap(Element e, LayerManager layerManager, GeneralParameters p) {
        Element descriptor = e.element("target");
        TargetDescriptor targetDescriptor = TargetDesciptorFactory.instantiate(layerManager, descriptor, p);
        Argument<Integer> selfChannel = IntegerArgumentFactory.instantiate(e, "actor-highlight", -1, p.getRandom());
        Argument<Integer> targetChannel = IntegerArgumentFactory.instantiate(e, "target-highlight", -1, p.getRandom());
        Function<BehaviorCell, Swap> fn = cell -> {
            TargetRule targetRule = targetDescriptor.instantiate(cell);
            return new Swap(cell, layerManager, targetRule, selfChannel, targetChannel);
        };
        return new ActionDescriptor<>(fn);
    }

    private static ActionDescriptor<ExpandTo> expandTo(Element e, LayerManager layerManager, GeneralParameters p) {
        Element descriptor = e.element("target");
        TargetDescriptor targetDescriptor = TargetDesciptorFactory.instantiate(layerManager, descriptor, p);
        Argument<Integer> selfChannel = IntegerArgumentFactory.instantiate(e, "actor-highlight", -1, p.getRandom());
        Argument<Integer> targetChannel = IntegerArgumentFactory.instantiate(e, "target-highlight", -1, p.getRandom());
        Function<BehaviorCell, ExpandTo> fn = cell -> {
            TargetRule targetRule = targetDescriptor.instantiate(cell);
            return new ExpandTo(cell, layerManager, targetRule, selfChannel, targetChannel, p.getRandom());
        };
        return new ActionDescriptor<>(fn);
    }

    private static ActionDescriptor<Expand> expand(Element e, LayerManager layerManager, GeneralParameters p) {
        Argument<Integer> selfChannel = IntegerArgumentFactory.instantiate(e, "actor-highlight", -1, p.getRandom());
        Argument<Integer> targetChannel = IntegerArgumentFactory.instantiate(e, "target-highlight", -1, p.getRandom());
        Function<BehaviorCell, Expand> fn = cell -> new Expand(cell, layerManager, selfChannel, targetChannel, p.getRandom());
        return new ActionDescriptor<>(fn);
    }

    private static ActionDescriptor<ExpandRandom> expandRandom(Element e, LayerManager layerManager, GeneralParameters p) {
        Argument<Integer> selfChannel = IntegerArgumentFactory.instantiate(e, "actor-highlight", -1, p.getRandom());
        Argument<Integer> targetChannel = IntegerArgumentFactory.instantiate(e, "target-highlight", -1, p.getRandom());
        Function<BehaviorCell, ExpandRandom> fn = cell -> new ExpandRandom(cell, layerManager, selfChannel, targetChannel, p.getRandom());
        return new ActionDescriptor<>(fn);
    }

    private static ActionDescriptor<ExpandWeighted> expandWeighted(Element e, LayerManager layerManager, GeneralParameters p) {
        Argument<Integer> selfChannel = IntegerArgumentFactory.instantiate(e, "actor-highlight", -1, p.getRandom());
        Argument<Integer> targetChannel = IntegerArgumentFactory.instantiate(e, "target-highlight", -1, p.getRandom());
        Function<BehaviorCell, ExpandWeighted> fn = cell -> new ExpandWeighted(cell, layerManager, selfChannel, targetChannel, p.getRandom());
        return new ActionDescriptor<>(fn);
    }

    private static ActionDescriptor<AdjustHealth> adjustHealth(Element e, LayerManager layerManager) {
        String deltaStr = e.element("delta").getTextTrim();
        double delta = Double.valueOf(deltaStr);
        Function<BehaviorCell, AdjustHealth> fn = cell -> new AdjustHealth(cell, layerManager, delta);
        return new ActionDescriptor<>(fn);
    }

}
