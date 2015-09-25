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

package nanoverse.runtime.factory.processes.discrete;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.processes.*;
import org.dom4j.Element;
import org.junit.*;
import test.LegacyLatticeTest;

import static org.junit.Assert.assertEquals;

public class MockProcessFactoryTest extends LegacyLatticeTest {

    private GeneralParameters p;
    private Element root;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        root = readXmlFile("factories/processes/discrete/MockProcessFactoryTest.xml");
        p = makeMockGeneralParameters();
    }

    @Test
    public void testDefault() throws Exception {
        Element testElem = root.element("implicit-case");
        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, p);

        MockProcess expected = new MockProcess(arguments, "", 1.0, 1);
        MockProcess actual = MockProcessFactory.instantiate(testElem, layerManager, p, 0);

        assertEquals(expected, actual);
    }

    @Test
    public void testExplicit() throws Exception {
        Element testElem = root.element("explicit-case");
        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, p);

        MockProcess expected = new MockProcess(arguments, "test", 4.0, 2);
        MockProcess actual = MockProcessFactory.instantiate(testElem, layerManager, p, 0);

        assertEquals(expected, actual);
    }
}