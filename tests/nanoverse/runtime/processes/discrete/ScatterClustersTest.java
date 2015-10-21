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

package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.control.halt.LatticeFullEvent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.processes.discrete.cluster.*;
import org.junit.*;
import test.AgentProcessTestBase;

import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class ScatterClustersTest extends AgentProcessTestBase {

    private IntegerArgument neighborCount;
    private AgentDescriptor cellDescriptor;
    private ScatterClusters query;
    private ScatterClustersHelper helper;

    @Before
    public void before() throws Exception {
        setup();
        helper = new ContactClustersHelper(layer);
        neighborCount = new ConstantInteger(1);
        cellDescriptor = mock(AgentDescriptor.class);
        when(cellDescriptor.next()).thenAnswer(invocation -> {
            Agent ret = mock(Agent.class);
            when(ret.getName()).thenReturn("test");
            return ret;
        });
        ConstantInteger maxTargets = new ConstantInteger(1);
        when(cpArguments.getMaxTargets()).thenReturn(maxTargets);
        query = new ScatterClusters(arguments, cpArguments, neighborCount, cellDescriptor, helper);
    }

    @Test(expected = LatticeFullEvent.class)
    public void insufficientVacancies() throws Exception {
        doTest("3");
    }

    private void doTest(String neighbor) throws Exception {
        Stream<String> neighborNames = Stream.of(neighbor);
        makeActiveSites(a);
        when(lookup.getNeighborNames(a, false))
            .thenReturn(neighborNames);
        query.target(null);
        query.fire(null);
    }

    @Test
    public void sufficientVacancies() throws Exception {
        Coordinate[] vacancy = new Coordinate[]{b};
        when(lookup.getNearestVacancies(a, 1)).thenReturn(vacancy);
        doTest("0");
        verify(update).place(any(), eq(a));
        verify(update).place(any(), eq(b));
    }

    @Test
    public void alreadyHasEnoughNeighbors() throws Exception {
        Coordinate[] vacancy = new Coordinate[]{b};
        when(lookup.getNearestVacancies(a, 1)).thenReturn(vacancy);
        doTest("1");
        verify(update).place(any(), any());
    }

    @Test(expected = LatticeFullEvent.class)
    public void noMoreCandidatesHalts() throws Exception {
        makeActiveSites();
        query.target(null);
        query.fire(null);
    }
}