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

package agent.action;

import agent.targets.*;
import cells.BehaviorCell;
import control.arguments.*;
import layers.LayerManager;
import structural.annotations.FactoryTarget;

import java.util.Random;
import java.util.function.Function;

/**
 * Created by dbborens on 8/3/2015.
 */
public class ExpandToDescriptor extends ActionDescriptor<ExpandTo> {

    private final Function<BehaviorCell,ExpandTo> constructor;

    @FactoryTarget(displayName = "ExpandTo")
    public ExpandToDescriptor(LayerManager layerManager,
                              TargetDescriptor ruleDescriptor,
                              Argument<Integer> selfChannel,
                              Argument<Integer> targetChannel, Random
                                          random) {

        constructor = cell -> {
            TargetRule targetRule = ruleDescriptor.instantiate(cell);
            return new ExpandTo(cell, layerManager, targetRule,
                    selfChannel, targetChannel, random);
        };
    }

    @Override
    protected Function<BehaviorCell, ExpandTo> resolveConstructor() {
        return constructor;
    }
}
