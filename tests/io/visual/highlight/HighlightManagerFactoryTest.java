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

package io.visual.highlight;

import factory.io.visual.highlight.HighlightManagerFactory;
import io.visual.glyph.MockGlyph;
import org.dom4j.Element;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.assertEquals;
/**
 * Created by dbborens on 4/3/14.
 */
public class HighlightManagerFactoryTest extends LegacyTest {
    private Element root;

    @Before
    public void setUp() throws Exception {
        root = readXmlFile("HighlightManagerFactoryTest.xml");
    }

    @Test
    public void testPopulate() throws Exception {
        Element highlights = root.element("highlights");

        HighlightManager expected = buildExpected();
        HighlightManager actual = HighlightManagerFactory.instantiate(highlights);

        assertEquals(expected, actual);
    }

    private HighlightManager buildExpected() {
        HighlightManager expected = new HighlightManager();
        expected.setGlyph(0, new MockGlyph());
        expected.setGlyph(3, new MockGlyph());
        return expected;
    }

    /**
     * If there is no highlight tag, we should get back an empty highlight
     * manager and no exceptions should be thrown.
     *
     * @throws Exception
     */
    @Test
    public void testNullCase() throws Exception {
        HighlightManager expected = new HighlightManager();
        HighlightManager actual = HighlightManagerFactory.instantiate(null);
        assertEquals(expected, actual);
    }
}
