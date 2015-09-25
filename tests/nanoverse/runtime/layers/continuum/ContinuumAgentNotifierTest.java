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
import org.junit.Before;
import org.junit.Test;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ContinuumAgentNotifierTest {

    private BiConsumer<BehaviorCell, Supplier<RelationshipTuple>> adder;
    private Consumer<BehaviorCell> remover;
    private Supplier<RelationshipTuple> supplier;
    private ContinuumAgentNotifier query;
    private BehaviorCell cell;

    @Before
    public void init() throws Exception {
        adder = (BiConsumer<BehaviorCell, Supplier<RelationshipTuple>>)
                mock(BiConsumer.class);
        remover = (Consumer<BehaviorCell>) mock(Consumer.class);
        cell = mock(BehaviorCell.class);
        supplier = (Supplier<RelationshipTuple>) mock(Supplier.class);

        query = new ContinuumAgentNotifier(adder, remover);
    }

    @Test
    public void addPassesCellAndSupplier() {
        query.add(cell, supplier);
        verify(adder).accept(cell, supplier);
    }

    @Test
    public void removePassesCell() {
        query.remove(cell);
        verify(remover).accept(cell);

    }
}