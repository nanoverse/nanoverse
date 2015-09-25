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

package factory.control.arguments;

import control.GeneralParameters;
import control.arguments.*;
import org.dom4j.Element;
import org.junit.*;
import test.LegacyLatticeTest;

import static org.junit.Assert.assertEquals;

public class CellDescriptorFactoryTest extends LegacyLatticeTest {

    private Element root;
    private GeneralParameters p;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        root = readXmlFile("factories/control/arguments/CellDescriptorFactoryTest.xml");
        p = makeMockGeneralParameters();
    }

    @Test
    public void testImplicit() throws Exception {
        Element implicitRoot = root.element("implicit-case");
        CellDescriptor actual = CellDescriptorFactory.instantiate(implicitRoot, layerManager, p);

        CellDescriptor expected = new CellDescriptor(layerManager);
        expected.setCellState(new ConstantInteger(1));
        expected.setInitialHealth(new ConstantDouble(0.5));
        expected.setThreshold(new ConstantDouble(1.0));

        assertEquals(expected, actual);
    }
}