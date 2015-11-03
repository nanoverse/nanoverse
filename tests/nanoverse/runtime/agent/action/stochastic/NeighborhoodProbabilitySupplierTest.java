/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package nanoverse.runtime.agent.action.stochastic;

import org.junit.Before;
import org.junit.Test;
import test.TestBase;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/13/2015.
 */
public class NeighborhoodProbabilitySupplierTest extends TestBase {

    private NeighborhoodCountHelper helper;
    private NeighborhoodProbabilitySupplier query;
    private double coefficient;
    private double offset;

    @Before
    public void before() throws Exception {
        helper = mock(NeighborhoodCountHelper.class);
        coefficient = 2.0;
        offset = 1.0;
        query = new NeighborhoodProbabilitySupplier(helper, coefficient, offset);
    }

    @Test
    public void testGet() throws Exception {
        when(helper.getNeighborCount()).thenReturn(3.0);
        double actual = query.get();
        assertEquals(7.0, actual, epsilon);
    }
}