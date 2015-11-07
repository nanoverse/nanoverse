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

package nanoverse.runtime.processes;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.*;
import org.junit.Test;
import test.LegacyLatticeTest;

import static org.junit.Assert.assertTrue;

public class BaseProcessArgumentsTest extends LegacyLatticeTest {

    private GeneralParameters p;
    private int id;
    private IntegerArgument start;
    private IntegerArgument period;

    private BaseProcessArguments query;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        p = makeMockGeneralParameters();
        id = 7;
        start = new ConstantInteger(4);
        period = new ConstantInteger(11);

        query = new BaseProcessArguments(layerManager, p, id, start, period);
    }

    @Test
    public void testGetPeriod() throws Exception {
        assertTrue(period == query.getPeriod());
    }

    @Test
    public void testGetStart() throws Exception {
        assertTrue(start == query.getStart());

    }

    @Test
    public void testGetId() throws Exception {
        assertTrue(id == query.getId());

    }

    @Test
    public void testGetGeneralParameters() throws Exception {
        assertTrue(p == query.getGeneralParameters());
    }

    @Test
    public void testGetLayerManager() throws Exception {
        assertTrue(layerManager == query.getLayerManager());
    }
}