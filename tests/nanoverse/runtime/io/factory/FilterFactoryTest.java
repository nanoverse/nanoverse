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

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.ConstantInteger;
import nanoverse.runtime.factory.processes.discrete.filter.FilterFactory;
import nanoverse.runtime.processes.discrete.filter.*;
import org.dom4j.Element;
import org.junit.*;
import test.LegacyLatticeTest;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class FilterFactoryTest extends LegacyLatticeTest {
    private Element root;
    private GeneralParameters p;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        root = readXmlFile("./factories/FilterFactoryTest.xml");
        p = makeMockGeneralParameters();
    }

    @Test
    public void testDefault() throws Exception {
        Element e = root.element("does-not-exist");
        Filter actual = FilterFactory.instantiate(e, layerManager, p);
        Filter expected = new NullFilter();
        assertEquals(expected, actual);
    }

    @Test
    public void testImplicitNullFilter() throws Exception {
        Element e = root.element("implicit-null-case");
        Filter actual = FilterFactory.instantiate(e, layerManager, p);
        Filter expected = new NullFilter();
        assertEquals(expected, actual);
    }

    @Test
    public void testExplicitNullFilter() throws Exception {
        Element e = root.element("explicit-null-case");
        Filter actual = FilterFactory.instantiate(e, layerManager, p);
        Filter expected = new NullFilter();
        assertEquals(expected, actual);
    }

    @Test
    public void testCompositeFilter() throws Exception {
        Element e = root.element("composite-case");

        Stream<Filter> children = Stream.of(
            new NullFilter(),
            new NullFilter()
        );

        Filter expected = new CompositeFilter(children);
        Filter actual = FilterFactory.instantiate(e, layerManager, p);
        assertEquals(expected, actual);
    }

    @Test
    public void testNestedComposite() throws Exception {
        Element e = root.element("nested-composite-case");

        Stream<Filter> subChildren = Stream.of(new NullFilter());

        Stream<Filter> children = Stream.of(
            new CompositeFilter(subChildren),
            new NullFilter()
        );

        Filter expected = new CompositeFilter(children);
        Filter actual = FilterFactory.instantiate(e, layerManager, p);
        assertEquals(expected, actual);
    }

    @Test
    public void testStateFilter() throws Exception {
        Element e = root.element("state-filter-case");

        Filter expected = new CellStateFilter(null, new ConstantInteger(1));
        Filter actual = FilterFactory.instantiate(e, layerManager, p);
        assertEquals(expected, actual);
    }

    @Test
    public void testDepthFilter() throws Exception {
        Element e = root.element("depth-filter-case");

        Filter expected = new DepthFilter(null, new ConstantInteger(1));
        Filter actual = FilterFactory.instantiate(e, layerManager, p);
        assertEquals(expected, actual);
    }
}