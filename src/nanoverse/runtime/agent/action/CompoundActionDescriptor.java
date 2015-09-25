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
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.List;
import java.util.function.Function;
import java.util.stream.*;

/**
 * Created by dbborens on 8/3/2015.
 */
public class CompoundActionDescriptor extends ActionDescriptor<CompoundAction> {
    private final Function<BehaviorCell, CompoundAction> constructor;
    private final List<ActionDescriptor> childList;

    @FactoryTarget(displayName = "CompoundAction")
    public CompoundActionDescriptor(LayerManager layerManager, Stream<ActionDescriptor> children) {
        childList = children.collect(Collectors.toList());
        constructor = cell -> {
            Action[] instanceChildren = childList.stream()
                    .map(descriptor -> descriptor.instantiate(cell))
                    .collect(Collectors.toList())
                    .toArray(new Action[0]);
            return new CompoundAction(cell, layerManager, instanceChildren);
        };
    }

    @Override
    protected Function<BehaviorCell, CompoundAction> resolveConstructor() {
        return constructor;
    }
}
