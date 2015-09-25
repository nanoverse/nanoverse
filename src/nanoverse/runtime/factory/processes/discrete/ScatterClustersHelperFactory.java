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
import nanoverse.runtime.layers.LayerManager;
import org.dom4j.Element;
import nanoverse.runtime.processes.NoContactClustersHelper;
import nanoverse.runtime.processes.discrete.cluster.CompactSeparatedClustersHelper;
import nanoverse.runtime.processes.discrete.cluster.ContactClustersHelper;
import nanoverse.runtime.processes.discrete.cluster.ScatterClustersHelper;
import nanoverse.runtime.processes.discrete.cluster.StrictSeparationClusterHelper;
import nanoverse.runtime.structural.utilities.XmlUtil;

/**
 * Created by dbborens on 6/14/2015.
 */
public class ScatterClustersHelperFactory {
    public static ScatterClustersHelper instantiate(Element e, LayerManager layerManager, GeneralParameters p) {
        String separation = XmlUtil.getString(e, "separation", "none");

        if (separation.equalsIgnoreCase("none")) {
            return new ContactClustersHelper(layerManager.getCellLayer());
        } else if (separation.equalsIgnoreCase("neighbors")) {
            return new NoContactClustersHelper(layerManager.getCellLayer());
        } else if (separation.equalsIgnoreCase("strict")) {
            return new StrictSeparationClusterHelper(layerManager.getCellLayer());
        } else if (separation.equalsIgnoreCase("compact")) {
            return new CompactSeparatedClustersHelper(layerManager.getCellLayer(), p);

        } else {
            throw new IllegalArgumentException("Unrecognized separation rule '" + separation + "'");
        }
    }
}
