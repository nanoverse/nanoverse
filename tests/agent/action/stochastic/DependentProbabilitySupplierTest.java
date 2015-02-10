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

package agent.action.stochastic;

import cells.BehaviorCell;
import org.junit.Before;
import org.junit.Test;
import test.TestBase;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DependentProbabilitySupplierTest extends TestBase {

    private Function<BehaviorCell, Double> valueLookup;
    private double coefficient;
    private double offset;
    private BehaviorCell cell, child;

    private DependentProbabilitySupplier query;

    @Before
    public void init() throws Exception {
        cell = mock(BehaviorCell.class);
        child = mock(BehaviorCell.class);

        valueLookup = (Function<BehaviorCell, Double>) mock(Function.class);
        when(valueLookup.apply(cell)).thenReturn(1.0);
        when(valueLookup.apply(child)).thenReturn(2.0);

        coefficient = 3.0;
        offset = 0.5;

        query = new DependentProbabilitySupplier(valueLookup, cell, coefficient, offset);
    }

    @Test
    public void getAppliesAllArguments() throws Exception {
        assertEquals(3.5, query.get(), epsilon);
    }

    @Test
    public void cloneReturnsEquivalent() throws Exception {
        DependentProbabilitySupplier clone = query.clone(child);
        assertEquals(6.5, clone.get(), epsilon);
    }

}