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

package nanoverse.runtime.io.factory.io.visual.color;

import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.factory.io.visual.color.SurfaceGrowthColorManagerFactory;
import nanoverse.runtime.io.visual.color.*;
import nanoverse.runtime.structural.MockGeneralParameters;
import org.dom4j.Element;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.assertEquals;

public class SurfaceGrowthColorManagerFactoryTest extends LegacyTest {
    private Element root;
    private MockGeneralParameters p;

    @Before
    public void setUp() throws Exception {

        root = readXmlFile("factories/io/visual/color/SurfaceGrowthColorManagerFactoryTest.xml");
        p = makeMockGeneralParameters();
    }

    @Test
    public void testImplicit() throws Exception {
        Element e = root.element("implicit-case");
        SurfaceGrowthColorManager actual = SurfaceGrowthColorManagerFactory.instantiate(e, p);

        ColorManager base = new DefaultColorManager();
        DoubleArgument saturationScaling = new ConstantDouble(SurfaceGrowthColorManagerFactory.DEFAULT_SATURATION_SCALING);
        DoubleArgument luminanceScaling = new ConstantDouble(SurfaceGrowthColorManagerFactory.DEFAULT_LUMINANCE_SCALING);
        SurfaceGrowthColorManager expected = new SurfaceGrowthColorManager(base, luminanceScaling, saturationScaling);

        assertEquals(expected, actual);
    }

    @Test
    public void testExplicit() throws Exception {
        Element e = root.element("explicit-case");
        SurfaceGrowthColorManager actual = SurfaceGrowthColorManagerFactory.instantiate(e, p);

        ColorManager base = new DefaultColorManager();
        DoubleArgument saturationScaling = new ConstantDouble(0.75);
        DoubleArgument luminanceScaling = new ConstantDouble(0.25);
        SurfaceGrowthColorManager expected = new SurfaceGrowthColorManager(base, luminanceScaling, saturationScaling);

        assertEquals(expected, actual);
    }
}