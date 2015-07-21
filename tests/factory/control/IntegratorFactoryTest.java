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
import control.Integrator;
import control.ProcessManager;
import io.serialize.SerializationManager;
import io.serialize.Serializer;
import io.serialize.interactive.ProgressReporter;
import org.dom4j.Element;
import processes.BaseProcessArguments;
import processes.NanoverseProcess;
import processes.MockProcess;
import structural.MockGeneralParameters;
import test.EslimeLatticeTestCase;

import java.util.ArrayList;
import java.util.List;

public class IntegratorFactoryTest extends EslimeLatticeTestCase {

    private GeneralParameters p;
    private Element root;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        p = new MockGeneralParameters();
        root = readXmlFile("factories/control/IntegratorFactoryTest.xml");
    }

    public void testExplicitCase() throws Exception {
        Element explicitRoot = root.element("explicit-case");
        Integrator actual = IntegratorFactory.instantiate(explicitRoot, p, layerManager);

        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, p);
        NanoverseProcess process = new MockProcess(arguments, "test1", 1.0, 1);
        List<NanoverseProcess> processes = new ArrayList<>(1);
        processes.add(process);
        ProcessManager processManager = new ProcessManager(processes, layerManager);

        Serializer writer = new ProgressReporter(p, layerManager);
        List<Serializer> writers = new ArrayList<>(1);
        writers.add(writer);
        SerializationManager serializationManager = new SerializationManager(p, layerManager, writers);

        Integrator expected = new Integrator(p, processManager, serializationManager);

        assertEquals(expected, actual);
    }

    public void testImplicitCase() throws Exception {
        Element implicitRoot = root.element("implicit-case");
        Integrator actual = IntegratorFactory.instantiate(implicitRoot, p, layerManager);
        ProcessManager processManager = new ProcessManager(new ArrayList<NanoverseProcess>(0), layerManager);
        SerializationManager serializationManager = new SerializationManager(p, layerManager, new ArrayList<Serializer>(0));
        Integrator expected = new Integrator(p, processManager, serializationManager);

        assertEquals(expected, actual);
    }
}
