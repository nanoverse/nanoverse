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

package io.visual.glyph;

import factory.io.visual.color.ColorFactory;
import org.dom4j.Element;
import test.EslimeTestCase;

import java.awt.*;

/**
 * Created by dbborens on 4/4/14.
 */
public class ColorFactoryTest extends EslimeTestCase {

    Element root;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        root = readXmlFile("factories/ColorFactoryTest.xml");
    }

    public void testDefaultCase() throws Exception {
        Element e = null;
        Color actual = ColorFactory.instantiate(e, Color.WHITE);
        Color expected = Color.WHITE;
        assertEquals(expected, actual);
    }

    public void testHexCase() throws Exception {
        Element e = root.element("hex-case");
        Color actual = ColorFactory.instantiate(e, null);
        Color expected = Color.decode("0xAABBCC");
        assertEquals(expected, actual);
    }
}

