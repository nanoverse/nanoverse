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
import nanoverse.runtime.factory.io.serialize.SerializationFactory;
import nanoverse.runtime.io.serialize.SerializationManager;
import nanoverse.runtime.layers.LayerManager;
import org.dom4j.Element;

/**
 * Created by dbborens on 11/26/14.
 */
public abstract class IntegratorFactory {

    public static Integrator instantiate(Element root, GeneralParameters p, LayerManager lm) {
        ProcessManager processManager = makeProcessManager(root, p, lm);
        SerializationManager serializationManager = makeSerializationManager(root, p, lm);
        Integrator integrator = new Integrator(p, processManager, serializationManager);
        return integrator;
    }


    private static SerializationManager makeSerializationManager(Element root, GeneralParameters p, LayerManager lm) {
        Element writers = root.element("writers");
        SerializationManager mgr = SerializationFactory.makeManager(writers, lm, p);
        return mgr;
    }

    private static ProcessManager makeProcessManager(Element root, GeneralParameters p, LayerManager lm) {
        ProcessManager processManager = ProcessManagerFactory.instantiate(root, p, lm);
        return processManager;
    }

}
