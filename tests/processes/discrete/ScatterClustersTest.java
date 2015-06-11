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

package processes.discrete;

import cells.BehaviorCell;
import control.arguments.*;
import control.halt.LatticeFullEvent;
import control.identifiers.Coordinate;
import geometry.set.*;
import layers.LayerManager;
import layers.cell.*;
import org.junit.*;
import processes.BaseProcessArguments;
import test.CellProcessTestBase;

import java.util.List;
import java.util.stream.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ScatterClustersTest extends CellProcessTestBase {

    private Argument<Integer> neighborCount;
    private CellDescriptor cellDescriptor;
    private ScatterClusters query;

    @Before
    public void before() throws Exception {
        setup();
        neighborCount = new ConstantInteger(1);
        cellDescriptor = mock(CellDescriptor.class);
        when(cellDescriptor.next()).thenAnswer(invocation -> {
            BehaviorCell ret = mock(BehaviorCell.class);
            when(ret.getState()).thenReturn(1);
            return ret;
        });
        ConstantInteger maxTargets = new ConstantInteger(1);
        when(cpArguments.getMaxTargets()).thenReturn(maxTargets);
        query = new ScatterClusters(arguments, cpArguments, neighborCount, cellDescriptor);
    }


    private void doTest(int neighbor) throws Exception {
        int[] neighborStates = new int[] {neighbor};
        makeActiveSites(a);
        when(lookup.getNeighborStates(a, false))
                .thenReturn(neighborStates);
        query.target(null);
        query.fire(null);
    }

    @Test(expected = LatticeFullEvent.class)
    public void insufficientVacancies() throws Exception {
        doTest(3);
    }

    @Test
    public void sufficientVacancies() throws Exception {
        Coordinate[] vacancy = new Coordinate[] {b};
        when(lookup.getNearestVacancies(a, 1)).thenReturn(vacancy);
        doTest(0);
        verify(update).place(any(), eq(a));
        verify(update).place(any(), eq(b));
    }

    @Test
    public void alreadyHasEnoughNeighbors() throws Exception {
        Coordinate[] vacancy = new Coordinate[] {b};
        when(lookup.getNearestVacancies(a, 1)).thenReturn(vacancy);
        doTest(1);
        verify(update).place(any(), any());
    }

    @Test(expected = LatticeFullEvent.class)
    public void noMoreCandidatesHalts() throws Exception {
        makeActiveSites();
        query.target(null);
        query.fire(null);
    }
}