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
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;
import test.LinearMocks;

import java.util.function.*;

import static org.mockito.Mockito.*;

public class ContinuumAgentLinkerTest extends LinearMocks {

    private ContinuumAgentNotifier notifier;
    private Function<Coordinate, Double> stateLookup;
    private ContinuumAgentLinker query;

    @Before
    public void init() throws Exception {
        notifier = mock(ContinuumAgentNotifier.class);
        stateLookup = (Function<Coordinate, Double>) mock(Function.class);
        when(stateLookup.apply(any())).thenReturn(1.0);

        query = new ContinuumAgentLinker(notifier, stateLookup);
    }

    @Test
    public void getAsksStateLookup() throws Exception {
        Supplier<Coordinate> supplier = () -> a;
        query.get(supplier);
        verify(stateLookup).apply(a);
    }

    @Test
    public void add() throws Exception {
        BehaviorCell cell = mock(BehaviorCell.class);
        Supplier<RelationshipTuple> tuple = mock(Supplier.class);
        query.add(cell, tuple);
        verify(notifier).add(cell, tuple);
    }

    @Test
    public void getRemover() throws Exception {
        BehaviorCell cell = mock(BehaviorCell.class);
        Runnable remover = query.getRemover(cell);
        remover.run();
        verify(notifier).remove(cell);
    }
}