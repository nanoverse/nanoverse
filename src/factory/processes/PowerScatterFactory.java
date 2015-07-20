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
import control.arguments.*;
import factory.processes.discrete.ScatterClustersHelperFactory;
import layers.LayerManager;
import org.dom4j.Element;
import processes.*;
import processes.discrete.*;
import processes.discrete.cluster.ScatterClustersHelper;

/**
 * Created by dbborens on 11/23/14.
 */
public abstract class PowerScatterFactory extends ProcessFactory {

    public static PowerScatter instantiate(Element e, LayerManager layerManager, GeneralParameters p, int id) {
        BaseProcessArguments arguments = makeProcessArguments(e, layerManager, p, id);
        CellProcessArguments cpArguments = makeCellProcessArguments(e, layerManager, p);
        CellDescriptor cellDescriptor = makeCellDescriptor(e, "cell-descriptor", layerManager, p);
        ScatterClustersHelper helper = ScatterClustersHelperFactory.instantiate(e, layerManager, p);
        PowerScatter scatter = new PowerScatter(arguments, cpArguments, cellDescriptor, helper);

        return scatter;
    }

}
