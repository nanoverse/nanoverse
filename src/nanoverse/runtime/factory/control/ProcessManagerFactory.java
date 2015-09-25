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

package nanoverse.runtime.factory.control;

import nanoverse.runtime.control.*;
import nanoverse.runtime.factory.processes.ProcessListFactory;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.NanoverseProcess;
import org.dom4j.Element;

import java.util.stream.Stream;

/**
 * Created by dbborens on 11/26/14.
 */
public abstract class ProcessManagerFactory {

    public static ProcessManager instantiate(Element root, GeneralParameters p, LayerManager lm) {
        if (root == null) {
            return nullCase(lm, p);
        }
        Element processElem = root.element("processes");
        Stream<NanoverseProcess> processes = ProcessListFactory.instantiate(processElem, lm, p);
        ProcessManager processManager = new ProcessManager(processes, lm);
        return processManager;
    }

    private static ProcessManager nullCase(LayerManager lm, GeneralParameters p) {
        Stream<NanoverseProcess> processes = ProcessListFactory.instantiate(null, lm, p);
        ProcessManager processManager = new ProcessManager(processes, lm);
        return processManager;
    }

}
