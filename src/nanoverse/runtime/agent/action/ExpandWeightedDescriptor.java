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

package nanoverse.runtime.agent.action;

import nanoverse.runtime.cells.BehaviorCell;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.Random;
import java.util.function.Function;

/**
 * Created by dbborens on 8/3/2015.
 */
public class ExpandWeightedDescriptor extends ActionDescriptor<ExpandWeighted> {

    private final Function<BehaviorCell, ExpandWeighted> constructor;

    @FactoryTarget(displayName = "ExpandWeighted")
    public ExpandWeightedDescriptor(LayerManager layerManager,
                                    IntegerArgument selfChannel, IntegerArgument targetChannel, Random random) {

        constructor = cell -> new ExpandWeighted(cell, layerManager, selfChannel, targetChannel, random);
    }

    @Override
    protected Function<BehaviorCell, ExpandWeighted> resolveConstructor() {
        return constructor;
    }
}
