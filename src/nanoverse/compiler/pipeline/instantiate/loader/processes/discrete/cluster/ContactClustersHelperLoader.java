/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.cluster;

import nanoverse.compiler.pipeline.instantiate.factory.processes.discrete.cluster.ContactClustersHelperFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.processes.discrete.cluster.ContactClustersHelper;

/**
 * Created by dbborens on 8/29/15.
 */
public class ContactClustersHelperLoader extends ScatterClustersHelperLoader<ContactClustersHelper> {

    private ContactClustersHelperFactory factory;

    public ContactClustersHelperLoader() {
        this.factory = factory;
    }

    public ContactClustersHelperLoader
        (ContactClustersHelperFactory factory) {

        this.factory = factory;
    }

    public ContactClustersHelper instantiate(LayerManager lm, GeneralParameters p) {
        return instantiate(null, lm, p);
    }

    @Override
    public ContactClustersHelper instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        AgentLayer layer = lm.getAgentLayer();
        factory.setLayer(layer);
        return factory.build();
    }
}
