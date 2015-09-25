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

package nanoverse.runtime.layers.continuum;

import nanoverse.runtime.cells.BehaviorCell;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Notifies a ContinuumAgentIndex that a related cell has been added or
 * removed.
 * <p>
 * Created by dbborens on 12/31/14.
 */
public class ContinuumAgentNotifier {

    private BiConsumer<BehaviorCell, Supplier<RelationshipTuple>> adder;
    private Consumer<BehaviorCell> remover;

    public ContinuumAgentNotifier(BiConsumer<BehaviorCell, Supplier<RelationshipTuple>> adder, Consumer<BehaviorCell> remover) {
        this.adder = adder;
        this.remover = remover;
    }

    public void add(BehaviorCell cell, Supplier<RelationshipTuple> supplier) {
        adder.accept(cell, supplier);
    }

    public void remove(BehaviorCell cell) {
        remover.accept(cell);
    }
}
