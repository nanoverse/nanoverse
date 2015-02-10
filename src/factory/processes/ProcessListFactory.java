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
import layers.LayerManager;
import org.dom4j.Element;
import processes.EcoProcess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dbborens on 11/23/14.
 */
public abstract class ProcessListFactory {

    public static List<EcoProcess> instantiate(Element root, LayerManager layerManager, GeneralParameters p) {
        ArrayList<EcoProcess> processes = new ArrayList<>();
        if (root == null) {
            return processes;
        }
        int id = 0;
        for (Object o : root.elements()) {
            Element e = (Element) o;
            EcoProcess process = ProcessFactory.instantiate(e, layerManager, p, id);
            processes.add(process);
            id++;
        }
        return processes;
    }
}
