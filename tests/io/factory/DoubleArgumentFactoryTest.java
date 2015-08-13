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
import factory.control.arguments.DoubleArgumentFactory;
import org.dom4j.Element;
import test.EslimeTestCase;

import java.util.Random;

/**
 * Created by David B Borenstein on 4/7/14.
 */
public class DoubleArgumentFactoryTest extends EslimeTestCase {
    private Element root;
    private Random random;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        root = readXmlFile("factories/DoubleArgumentFactoryTest.xml");
        random = new Random(RANDOM_SEED);
    }

    public void testNullNoDefault() {
        Element element = root.element("null-case");

        boolean thrown = false;

        try {
            DoubleArgumentFactory.instantiate(element, "not-there", random);
        } catch (Exception ex) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    public void testNullWithDefault() {
        Element element = root.element("null-case");

        Argument<Double> actual = DoubleArgumentFactory.instantiate(element, "not-there", 5.0, random);
        Argument<Double> expected = new ConstantDouble(5.0);

        assertEquals(expected, actual);
    }

    public void testConstantImplicit() {
        Element element = root.element("constant-implicit-case");

        Argument<Double> actual = DoubleArgumentFactory.instantiate(element, "test", 6.0, random);
        Argument<Double> expected = new ConstantDouble(5.0);

        assertEquals(expected, actual);
    }

    public void testConstantExplicit() {
        Argument<Double> actual = DoubleArgumentFactory.instantiate(root, "constant-explicit-case", 6.0, random);
        Argument<Double> expected = new ConstantDouble(5.0);

        assertEquals(expected, actual);
    }

    public void testUniform() {
        Argument<Double> actual = DoubleArgumentFactory.instantiate(root, "uniform-case", 6.0, random);
        Argument<Double> expected = new UniformDouble(1.7, 2.4, random);

        assertEquals(expected, actual);
    }

    public void testRecursive() {
        Argument<Double> actual = DoubleArgumentFactory.instantiate(root, "recursive-case", 6.0, random);
        Argument<Double> expected = new UniformDouble(-1.0, 2.0, random);

        assertEquals(expected, actual);
    }
}
