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

package factory.control.run;//import junit.framework.TestCase;

import org.dom4j.Element;
import org.dom4j.tree.BaseElement;
import test.EslimeTestCase;

public class DocumentFactoryTest extends EslimeTestCase {

    public void testInstantiate() throws Exception {
        Element expected = new BaseElement("fixture");

        String path = fixturePath + "factories/control/run/DocumentFactoryTest.xml";
        Element actual = DocumentFactory.instantiate(path);

        // TODO change this to a direct comparison after creating EcoElement objects
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.elements().size(), actual.elements().size());
    }
}