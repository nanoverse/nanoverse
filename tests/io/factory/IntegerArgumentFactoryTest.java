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

import control.arguments.*;
import factory.control.arguments.IntegerArgumentFactory;
import org.dom4j.Element;
import test.EslimeTestCase;

import java.util.Random;

/**
 * Created by David B Borenstein on 4/7/14.
 */
public class IntegerArgumentFactoryTest extends EslimeTestCase {

    private Element root;
    private Random random;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        root = readXmlFile("factories/IntegerArgumentFactoryTest.xml");
        random = new Random(RANDOM_SEED);
    }

    public void testNullNoDefault() {
        Element element = root.element("null-case");

        boolean thrown = false;

        try {
            IntegerArgumentFactory.instantiate(element, "not-there", random);
        } catch (Exception ex) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    public void testNullWithDefault() {
        Element element = root.element("null-case");

        IntegerArgument actual = IntegerArgumentFactory.instantiate(element, "not-there", 5, random);
        IntegerArgument expected = new ConstantInteger(5);

        assertEquals(expected, actual);
    }

    public void testConstantImplicit() {
        Element element = root.element("constant-implicit-case");

        IntegerArgument actual = IntegerArgumentFactory.instantiate(element, "test", 6, random);
        IntegerArgument expected = new ConstantInteger(5);

        assertEquals(expected, actual);
    }

    public void testConstantExplicit() {
        IntegerArgument actual = IntegerArgumentFactory.instantiate(root, "constant-explicit-case", 6, random);
        IntegerArgument expected = new ConstantInteger(5);

        assertEquals(expected, actual);
    }

    public void testUniform() {
        IntegerArgument actual = IntegerArgumentFactory.instantiate(root, "uniform-case", 6, random);
        IntegerArgument expected = new UniformInteger(1, 3, random);

        assertEquals(expected, actual);
    }

    public void testRecursive() {
        IntegerArgument actual = IntegerArgumentFactory.instantiate(root, "recursive-case", 6, random);
        IntegerArgument expected = new UniformInteger(-1, 2, random);

        assertEquals(expected, actual);
    }
}
