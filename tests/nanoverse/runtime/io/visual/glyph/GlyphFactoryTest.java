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

import nanoverse.runtime.factory.io.visual.glyph.GlyphFactory;
import nanoverse.runtime.io.visual.highlight.*;
import org.dom4j.Element;
import org.junit.*;
import test.LegacyTest;

import java.awt.*;

import static org.junit.Assert.assertEquals;
/**
 * Created by dbborens on 4/4/14.
 */
public class GlyphFactoryTest extends LegacyTest {
    private Element root;

    @Before
    public void setUp() throws Exception {
        root = readXmlFile("factories/GlyphFactoryTest.xml");
    }

    @Test
    public void testMockCase() throws Exception {
        Element e = root.element("mock-case");
        Glyph actual = GlyphFactory.instantiate(e);
        Glyph expected = new MockGlyph();

        assertEquals(expected, actual);
    }

    @Test
    public void testDotCompleteCase() throws Exception {
        Element e = root.element("dot-case-complete");
        Glyph actual = GlyphFactory.instantiate(e);

        Color color = Color.decode("0xFF00AA");
        double size = 0.4;
        Glyph expected = new DotGlyph(color, size);

        assertEquals(expected, actual);
    }

    /**
     * We test one class of glyph for default handling
     */
    @Test
    public void testDotDefaultCase() throws Exception {
        Element e = root.element("dot-case-default");
        Glyph actual = GlyphFactory.instantiate(e);

        Glyph expected = new DotGlyph(Color.WHITE,
                0.1);

        assertEquals(expected, actual);
    }

    @Test
    public void testBullseyeGlyphCase() throws Exception {
        Element e = root.element("bullseye-case");

        Glyph actual = GlyphFactory.instantiate(e);

        Color primary = Color.decode("0x001122");
        Color secondary = Color.decode("0x334455");
        Glyph expected = new BullseyeGlyph(primary,
                secondary, 0.3);

        assertEquals(expected, actual);
    }

    @Test
    public void testCrosshairsCase() throws Exception {
        Element e = root.element("crosshairs-case");

        Glyph actual = GlyphFactory.instantiate(e);

        Color color = Color.decode("0xAABBCC");
        Glyph expected = new CrosshairsGlyph(color, 0.2, 2.0);

        assertEquals(expected, actual);
    }
}
