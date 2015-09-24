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

package factory.processes;

import control.GeneralParameters;
import org.dom4j.Element;
import org.junit.*;
import processes.*;
import test.EslimeLatticeTestCase;

import java.util.List;
import java.util.stream.*;

import static org.junit.Assert.assertEquals;
public class ProcessListFactoryTest extends EslimeLatticeTestCase {

    private GeneralParameters p;
    private Element root;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        p = makeMockGeneralParameters();
        root = readXmlFile("factories/processes/ProcessListFactoryTest.xml");
    }

    @Test
    public void testImplicit() throws Exception {
        Element implicit = root.element("implicit-case");
        Stream<NanoverseProcess> expected = Stream.empty();
        Stream actual = ProcessListFactory.instantiate(implicit, layerManager, p);

        doComparison(expected, actual);
    }

    private void doComparison(Stream<NanoverseProcess> expStream, Stream<NanoverseProcess> actStream) {
        List<NanoverseProcess> expected = expStream.collect(Collectors.toList());
        List<NanoverseProcess> actual = actStream.collect(Collectors.toList());
        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test
    public void testSingleton() throws Exception {
        Element singleton = root.element("singleton-case");
        Stream<NanoverseProcess> expected = Stream.of(makeProcess("test"));
        Stream<NanoverseProcess> actual = ProcessListFactory.instantiate(singleton, layerManager, p);

        doComparison(expected, actual);
    }

    private NanoverseProcess makeProcess(String identifier) {
        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, p);
        return new MockProcess(arguments, identifier, 1.0, 1);
    }

    @Test
    public void testMultiple() throws Exception {
        Element multiple = root.element("multiple-case");
        Stream<NanoverseProcess> expected = Stream.of(
            makeProcess("test1"),
            makeProcess("test2"));
        Stream<NanoverseProcess> actual = ProcessListFactory.instantiate(multiple, layerManager, p);

        doComparison(expected, actual);
    }
}