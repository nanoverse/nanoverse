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

import nanoverse.runtime.agent.BehaviorAgent;
import org.junit.*;

import java.util.function.*;

import static org.mockito.Mockito.*;

public class ContinuumAgentNotifierTest {

    private BiConsumer<BehaviorAgent, Supplier<RelationshipTuple>> adder;
    private Consumer<BehaviorAgent> remover;
    private Supplier<RelationshipTuple> supplier;
    private ContinuumAgentNotifier query;
    private BehaviorAgent cell;

    @Before
    public void init() throws Exception {
        adder = (BiConsumer<BehaviorAgent, Supplier<RelationshipTuple>>)
            mock(BiConsumer.class);
        remover = (Consumer<BehaviorAgent>) mock(Consumer.class);
        cell = mock(BehaviorAgent.class);
        supplier = (Supplier<RelationshipTuple>) mock(Supplier.class);

        query = new ContinuumAgentNotifier(adder, remover);
    }

    @Test
    public void addPassesAgentAndSupplier() {
        query.add(cell, supplier);
        verify(adder).accept(cell, supplier);
    }

    @Test
    public void removePassesAgent() {
        query.remove(cell);
        verify(remover).accept(cell);

    }
}