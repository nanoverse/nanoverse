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

import org.junit.*;
import test.TestBase;

import static org.junit.Assert.assertEquals;

public class ConstantProbabilitySupplierTest extends TestBase {

    ConstantProbabilitySupplier query;

    @Before
    public void init() throws Exception {
        query = new ConstantProbabilitySupplier(1.0);
    }

    @Test
    public void getReturnsOriginalValue() throws Exception {
        assertEquals(1.0, query.get(), epsilon);
    }

    @Test
    public void cloneReturnsOriginalValue() throws Exception {
        ConstantProbabilitySupplier clone = query.clone(null);
        assertEquals(1.0, clone.get(), epsilon);
    }
}