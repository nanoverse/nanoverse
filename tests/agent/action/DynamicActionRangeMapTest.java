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


import agent.action.stochastic.ProbabilitySupplier;
import cells.BehaviorCell;
import layers.LayerManager;
import org.junit.Before;
import org.junit.Test;
import test.TestBase;

import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DynamicActionRangeMapTest extends TestBase {

    private LayerManager layerManager;
    private Action a1, a2;
    private ProbabilitySupplier p1, p2;

    private DynamicActionRangeMap query;

    @Before
    public void init() throws Exception {
        layerManager = mock(LayerManager.class);
        query = new DynamicActionRangeMap(layerManager);

        a1 = mock(Action.class);
        p1 = mockProbabilitySupplier(0.5);
        query.add(a1, p1);

        a2 = mock(Action.class);
        p2 = mockProbabilitySupplier(1.0);
        query.add(a2, p2);

        query.refresh();
    }

    @Test
    public void addIncrementsTotalWeight() throws Exception {
        assertEquals(1.5, query.getTotalWeight(), epsilon);
    }

    @Test(expected = IllegalStateException.class)
    public void selectOutofBoundsThrows() throws Exception {
        query.selectTarget(1.6);
    }

    @Test
    public void selectGetsTarget() throws Exception {
        doTargetCheck(query, a1, 5, a2, 10);
    }

    @Test
    public void cloneBehavesAsExpected() throws Exception {
        // "Cloned" suppliers have new values
        ProbabilitySupplier cp1 = mockProbabilitySupplier(0.3);
        ProbabilitySupplier cp2 = mockProbabilitySupplier(1.2);
        when(p1.clone(any())).thenReturn(cp1);
        when(p2.clone(any())).thenReturn(cp2);

        Action ca1 = mock(Action.class);
        Action ca2 = mock(Action.class);
        when(a1.clone(any())).thenReturn(ca1);
        when(a2.clone(any())).thenReturn(ca2);

        BehaviorCell child = mock(BehaviorCell.class);
        DynamicActionRangeMap cloned = query.clone(child);
        cloned.refresh();
        doTargetCheck(cloned, ca1, 3, ca2, 12);
    }


    private void doTargetCheck(DynamicActionRangeMap target,
                               Action aa1, long n1, Action aa2, long n2) {
        Map<Action, Long> resultCount = IntStream.range(0, 15)
                .boxed()
                .map(k -> k / 10.0)
                .map(x -> target.selectTarget(x))
                .collect(groupingBy(x -> x, counting()));

        assertEquals(n1, resultCount.get(aa1).longValue());
        assertEquals(n2, resultCount.get(aa2).longValue());
    }

    private ProbabilitySupplier mockProbabilitySupplier(double p) {
        ProbabilitySupplier ret = mock(ProbabilitySupplier.class);
        when(ret.get()).thenReturn(p);
        return ret;
    }
}