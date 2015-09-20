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

package factory.control;//import junit.framework.TestCase;

import control.GeneralParameters;
import control.ProcessManager;
import org.dom4j.Element;
import processes.BaseProcessArguments;
import processes.NanoverseProcess;
import processes.MockProcess;
import test.EslimeLatticeTestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ProcessManagerFactoryTest extends EslimeLatticeTestCase {

    private GeneralParameters p;
    private Element root;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        p = makeMockGeneralParameters();

        root = readXmlFile("factories/control/ProcessManagerFactoryTest.xml");
    }

    public void testImplicit() throws Exception {
        Element implicitRoot = root.element("implicit-case");

        ProcessManager actual = ProcessManagerFactory.instantiate(implicitRoot, p, layerManager);

        Stream<NanoverseProcess> processes = Stream.empty();
        ProcessManager expected = new ProcessManager(processes, layerManager);

        assertEquals(expected, actual);
    }

    public void testExplicit() throws Exception {
        Element explicitRoot = root.element("explicit-case");

        ProcessManager actual = ProcessManagerFactory.instantiate(explicitRoot, p, layerManager);

        Stream<NanoverseProcess> processes = Stream.of(
            mockProcess("test1"),
            mockProcess("test2"));

        ProcessManager expected = new ProcessManager(processes, layerManager);

        assertEquals(expected, actual);
    }

    private NanoverseProcess mockProcess(String identifier) {
        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, p);

        return new MockProcess(arguments, identifier, 1.0, 1);
    }
}