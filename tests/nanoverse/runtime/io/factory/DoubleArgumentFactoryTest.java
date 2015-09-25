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

package nanoverse.runtime.io.factory;

import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.factory.control.arguments.DoubleArgumentFactory;
import org.dom4j.Element;
import org.junit.*;
import test.LegacyTest;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by David B Borenstein on 4/7/14.
 */
public class DoubleArgumentFactoryTest extends LegacyTest {
    private Element root;
    private Random random;

    @Before
    public void setUp() throws Exception {
        root = readXmlFile("factories/DoubleArgumentFactoryTest.xml");
        random = new Random(RANDOM_SEED);
    }

    @Test
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

    @Test
    public void testNullWithDefault() {
        Element element = root.element("null-case");

        DoubleArgument actual = DoubleArgumentFactory.instantiate(element, "not-there", 5.0, random);
        DoubleArgument expected = new ConstantDouble(5.0);

        assertEquals(expected, actual);
    }

    @Test
    public void testConstantImplicit() {
        Element element = root.element("constant-implicit-case");

        DoubleArgument actual = DoubleArgumentFactory.instantiate(element, "test", 6.0, random);
        DoubleArgument expected = new ConstantDouble(5.0);

        assertEquals(expected, actual);
    }

    @Test
    public void testConstantExplicit() {
        DoubleArgument actual = DoubleArgumentFactory.instantiate(root, "constant-explicit-case", 6.0, random);
        DoubleArgument expected = new ConstantDouble(5.0);

        assertEquals(expected, actual);
    }

    @Test
    public void testUniform() {
        DoubleArgument actual = DoubleArgumentFactory.instantiate(root, "uniform-case", 6.0, random);
        DoubleArgument expected = new UniformDouble(1.7, 2.4, random);

        assertEquals(expected, actual);
    }

    @Test
    public void testRecursive() {
        DoubleArgument actual = DoubleArgumentFactory.instantiate(root, "recursive-case", 6.0, random);
        DoubleArgument expected = new UniformDouble(-1.0, 2.0, random);

        assertEquals(expected, actual);
    }
}
