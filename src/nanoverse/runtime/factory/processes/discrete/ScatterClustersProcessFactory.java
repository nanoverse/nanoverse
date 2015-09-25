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

package nanoverse.runtime.factory.processes.discrete;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.factory.control.arguments.IntegerArgumentFactory;
import nanoverse.runtime.factory.processes.ProcessFactory;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.runtime.processes.discrete.*;
import nanoverse.runtime.processes.discrete.cluster.ScatterClustersHelper;
import org.dom4j.Element;

/**
 * Created by dbborens on 11/23/14.
 */
public abstract class ScatterClustersProcessFactory extends ProcessFactory {

    public static ScatterClusters instantiate(Element e, LayerManager layerManager, GeneralParameters p, int id) {
        BaseProcessArguments arguments = makeProcessArguments(e, layerManager, p, id);
        CellProcessArguments cpArguments = makeCellProcessArguments(e, layerManager, p);
        CellDescriptor cellDescriptor = makeCellDescriptor(e, "cell-descriptor", layerManager, p);
        IntegerArgument neighborCount = IntegerArgumentFactory.instantiate(e, "neighbors", 1, p.getRandom());
        ScatterClustersHelper helper = ScatterClustersHelperFactory.instantiate(e, layerManager, p);
        ScatterClusters scatter = new ScatterClusters(arguments, cpArguments, neighborCount, cellDescriptor, helper);

        return scatter;
    }
}
