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

package nanoverse.runtime.io.visual.glyph;

import nanoverse.runtime.factory.io.visual.color.ColorFactory;
import org.dom4j.Element;
import org.junit.*;
import test.LegacyTest;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by dbborens on 4/4/14.
 */
public class ColorFactoryTest extends LegacyTest {

    Element root;

    @Before
    public void setUp() throws Exception {
        root = readXmlFile("factories/ColorFactoryTest.xml");
    }

    @Test
    public void testDefaultCase() throws Exception {
        Element e = null;
        Color actual = ColorFactory.instantiate(e, Color.WHITE);
        Color expected = Color.WHITE;
        assertEquals(expected, actual);
    }

    @Test
    public void testHexCase() throws Exception {
        Element e = root.element("hex-case");
        Color actual = ColorFactory.instantiate(e, null);
        Color expected = Color.decode("0xAABBCC");
        assertEquals(expected, actual);
    }
}

