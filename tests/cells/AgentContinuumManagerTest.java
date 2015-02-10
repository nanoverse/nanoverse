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

package cells;

import control.identifiers.Coordinate;
import layers.continuum.ContinuumAgentLinker;
import layers.continuum.ContinuumAgentNotifier;
import org.junit.Before;
import test.LinearMocks;

import java.util.function.Supplier;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class AgentContinuumManagerTest extends LinearMocks {

    private ContinuumAgentLinker linker;
    private BehaviorCell cell;
    private Supplier<Coordinate> locate;
    private RemoverIndex index;
    private ContinuumAgentNotifier notifier;
    private String id;

    private AgentContinuumManager query;

    @Before
    public void init() throws Exception {
        id = "test";

        notifier = mock(ContinuumAgentNotifier.class);

        linker = mock(ContinuumAgentLinker.class);
        when(linker.get(any())).thenReturn(1.0);
        when(linker.getNotifier()).thenReturn(notifier);

        cell = mock(BehaviorCell.class);

        locate = (Supplier<Coordinate>) mock(Supplier.class);
        when(locate.get()).thenReturn(a);

        index = mock(RemoverIndex.class);

//        query = new AgentContinuumScheduler(cell, index, locate, );
    }

    public void nothing() throws Exception {
        fail("Implement me");
    }
}