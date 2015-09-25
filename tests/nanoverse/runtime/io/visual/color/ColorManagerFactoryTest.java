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

package nanoverse.runtime.io.visual.color;

import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.factory.io.visual.color.*;
import org.dom4j.Element;
import org.junit.*;
import nanoverse.runtime.structural.MockGeneralParameters;
import test.LegacyTest;

import static org.junit.Assert.assertEquals;
/**
 * Created by dbborens on 4/2/14.
 */
public class ColorManagerFactoryTest extends LegacyTest {
    private Element fixtureRoot;
    private MockGeneralParameters p;

    @Before
    public void setUp() throws Exception {
        fixtureRoot = readXmlFile("ColorManagerFactoryTest.xml");
        p = makeMockGeneralParameters();
    }

    @Test
    public void testDefaultColorCase() {
        Element element = fixtureRoot.element("default-case");
        ColorManager actual = ColorManagerFactory.instantiate(element, p);
        ColorManager expected = new DefaultColorManager();
        assertEquals(expected, actual);
    }

    @Test
    public void testSurfaceGrowthCase() {
        Element element = fixtureRoot.element("surface-growth-case");

        ColorManager base = new DefaultColorManager();
        DoubleArgument saturationScaling = new ConstantDouble(SurfaceGrowthColorManagerFactory.DEFAULT_SATURATION_SCALING);
        DoubleArgument luminanceScaling = new ConstantDouble(SurfaceGrowthColorManagerFactory.DEFAULT_LUMINANCE_SCALING);
        SurfaceGrowthColorManager expected = new SurfaceGrowthColorManager(base, luminanceScaling, saturationScaling);

        ColorManager actual = ColorManagerFactory.instantiate(element, p);

        assertEquals(expected, actual);
    }

    @Test
    public void testNullCase() {
        ColorManager actual = ColorManagerFactory.instantiate(null, p);
        ColorManager expected = new DefaultColorManager();
        assertEquals(expected, actual);
    }
}
