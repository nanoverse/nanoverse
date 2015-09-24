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

package compiler.pipeline.instantiate.loader.layers;

import compiler.error.UserError;
import compiler.pipeline.instantiate.factory.layers.LayerManagerFactory;
import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.instantiate.loader.layers.agent.AgentLayerLoader;
import compiler.pipeline.translate.nodes.*;
import control.GeneralParameters;
import control.arguments.GeometryDescriptor;
import layers.*;
import layers.cell.CellLayer;
import layers.continuum.ContinuumLayer;

import java.util.HashMap;

/**
 * Created by dbborens on 8/1/2015.
 */
public class LayerManagerLoader extends Loader<LayerManager> {

    private final LayerManagerFactory factory;
    private final HashMap<String, ContinuumLayer> continuumLayers;
    private int numCellLayers = 0;

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

    public LayerManager instantiate(GeometryDescriptor geom, GeneralParameters p) {
        AgentLayerLoader loader = new AgentLayerLoader();
        CellLayer layer = loader.instantiate(geom, p);
        factory.setCellLayer(layer);
        factory.setContinuumLayers(continuumLayers);
        return factory.build();
    }

    private void assignLayer(Layer layer) {
        if (layer instanceof CellLayer) {
            assignCellLayer((CellLayer) layer);
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

    private void assignCellLayer(CellLayer layer) {
        if (numCellLayers > 0) {
            throw new UnsupportedOperationException("Multiple cell layers not implemented.") ;
        }

        factory.setCellLayer(layer);
        numCellLayers++;
    }

    private Layer loadLayer(MapObjectNode child, GeometryDescriptor geom, GeneralParameters p) {
        LayerLoader loader = (LayerLoader) child.getSymbolTable().getLoader();
        Layer layer = loader.instantiate(child, geom, p);
        return layer;
    }
}
