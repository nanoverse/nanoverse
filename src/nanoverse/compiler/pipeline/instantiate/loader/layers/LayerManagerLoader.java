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

package nanoverse.compiler.pipeline.instantiate.loader.layers;

import nanoverse.compiler.error.UserError;
import nanoverse.compiler.pipeline.instantiate.factory.layers.LayerManagerFactory;
import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.layers.agent.AgentLayerLoader;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.GeometryDescriptor;
import nanoverse.runtime.layers.*;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.layers.continuum.ContinuumLayer;

import java.util.HashMap;

/**
 * Created by dbborens on 8/1/2015.
 */
public class LayerManagerLoader extends Loader<LayerManager> {

    private final LayerManagerFactory factory;
    private final HashMap<String, ContinuumLayer> continuumLayers;
    private int numAgentLayers = 0;

    public LayerManagerLoader() {
        factory = new LayerManagerFactory();
        continuumLayers = new HashMap<>();
    }

    public LayerManagerLoader(LayerManagerFactory factory,
                              HashMap<String, ContinuumLayer> continuumLayers) {

        this.factory = factory;
        this.continuumLayers = continuumLayers;
    }

    public LayerManager instantiate(ListObjectNode childNode,
                                    GeometryDescriptor geom,
                                    GeneralParameters p) {

        childNode.getMemberStream()
            .map(o -> (MapObjectNode) o)
            .map(node -> loadLayer(node, geom, p))
            .forEach(this::assignLayer);

        factory.setContinuumLayers(continuumLayers);

        return factory.build();
    }

    private Layer loadLayer(MapObjectNode child, GeometryDescriptor geom, GeneralParameters p) {
        LayerLoader loader = (LayerLoader) child.getSymbolTable().getLoader();
        Layer layer = loader.instantiate(child, geom, p);
        return layer;
    }

    public LayerManager instantiate(GeometryDescriptor geom, GeneralParameters p) {
        AgentLayerLoader loader = new AgentLayerLoader();
        AgentLayer layer = loader.instantiate(geom, p);
        factory.setAgentLayer(layer);
        factory.setContinuumLayers(continuumLayers);
        return factory.build();
    }

    private void assignLayer(Layer layer) {
        if (layer instanceof AgentLayer) {
            assignAgentLayer((AgentLayer) layer);
        } else if (layer instanceof ContinuumLayer) {
            assignContinuumLayer((ContinuumLayer) layer);
        } else {
            throw new IllegalStateException();
        }
    }

    private void assignContinuumLayer(ContinuumLayer layer) {
        String id = layer.getId();
        if (continuumLayers.containsKey(id)) {
            throw new UserError("Duplicate declaration of layer \"" + id + "\"");
        }

        continuumLayers.put(id, layer);
    }

    private void assignAgentLayer(AgentLayer layer) {
        if (numAgentLayers > 0) {
            throw new UnsupportedOperationException("Multiple cell nanoverse.runtime.layers not implemented.");
        }

        factory.setAgentLayer(layer);
        numAgentLayers++;
    }
}
