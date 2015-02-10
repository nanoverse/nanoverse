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

package io.factory.io.visual.color;

import control.arguments.Argument;
import control.arguments.ConstantDouble;
import factory.io.visual.color.SurfaceGrowthColorManagerFactory;
import io.visual.color.ColorManager;
import io.visual.color.DefaultColorManager;
import io.visual.color.SurfaceGrowthColorManager;
import org.dom4j.Element;
import structural.MockGeneralParameters;
import test.EslimeTestCase;

public class SurfaceGrowthColorManagerFactoryTest extends EslimeTestCase {
    private Element root;
    private MockGeneralParameters p;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        root = readXmlFile("factories/io/visual/color/SurfaceGrowthColorManagerFactoryTest.xml");
        p = makeMockGeneralParameters();
    }

    public void testImplicit() throws Exception {
        Element e = root.element("implicit-case");
        SurfaceGrowthColorManager actual = SurfaceGrowthColorManagerFactory.instantiate(e, p);

        ColorManager base = new DefaultColorManager();
        Argument<Double> saturationScaling = new ConstantDouble(SurfaceGrowthColorManagerFactory.DEFAULT_SATURATION_SCALING);
        Argument<Double> luminanceScaling = new ConstantDouble(SurfaceGrowthColorManagerFactory.DEFAULT_LUMINANCE_SCALING);
        SurfaceGrowthColorManager expected = new SurfaceGrowthColorManager(base, luminanceScaling, saturationScaling);

        assertEquals(expected, actual);
    }

    public void testExplicit() throws Exception {
        Element e = root.element("explicit-case");
        SurfaceGrowthColorManager actual = SurfaceGrowthColorManagerFactory.instantiate(e, p);

        ColorManager base = new DefaultColorManager();
        Argument<Double> saturationScaling = new ConstantDouble(0.75);
        Argument<Double> luminanceScaling = new ConstantDouble(0.25);
        SurfaceGrowthColorManager expected = new SurfaceGrowthColorManager(base, luminanceScaling, saturationScaling);

        assertEquals(expected, actual);
    }
}