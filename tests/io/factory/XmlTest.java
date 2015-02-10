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

package io.factory;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import test.EslimeTestCase;

import java.io.File;
import java.util.List;

/**
 * Created by David B Borenstein on 12/22/13.
 */
public class XmlTest extends EslimeTestCase {

    public void testOrder() throws DocumentException {

        // Open the fixture.
        String filename = fixturePath + "XmlTest.xml";
        File testFile = new File(filename);
        SAXReader reader = new SAXReader();
        Document document = reader.read(testFile);
        Element root = document.getRootElement();

        // There should be three elements within the root.
        List<Element> middles = root.elements();
        assertEquals(3, middles.size());

        Element query;
        // The first element should have four children.
        query = middles.get(0);
        assertEquals(4, query.elements().size());

        int i = 0;
        String[] expected = new String[]{"a", "b", "c", "d"};

        for (Object inner : query.elements()) {
            Element e = (Element) inner;

            // Verify the data tags (in order) of the child elements
            Element dataElem = e.element("data");
            assertNotNull(dataElem);

            String data = dataElem.getText();

            assertEquals(expected[i], data);
            i++;
        }

        // The second element should also have two children.
        query = middles.get(1);
        assertEquals(2, query.elements().size());

        // The third element should have three children, of different classes.
        query = middles.get(2);
        assertEquals(3, query.elements().size());

        String[] expectedNames = {"alpha", "beta", "alpha"};
        String[] expectedIDs = {"1", "3", "2"};
        i = 0;
        for (Object inner : query.elements()) {
            Element e = (Element) inner;

            // Verify the elements are loaded in specified order.
            assertEquals(expectedNames[i], e.getName());
            Attribute id = e.attribute("id");
            assertEquals(expectedIDs[i], id.getValue());

            i++;
        }
    }
}
