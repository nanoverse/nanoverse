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

package compiler.pipeline.instantiate.loader.layers.agent;

import compiler.pipeline.instantiate.factory.layers.cell.AgentLayerFactory;
import compiler.pipeline.instantiate.loader.layers.LayerLoader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.arguments.GeometryDescriptor;
import geometry.Geometry;
import geometry.boundaries.Boundary;
import layers.Layer;
import layers.cell.CellLayer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by dbborens on 8/1/2015.
 */
public class AgentLayerLoader extends LayerLoader<CellLayer> {

    private final AgentLayerFactory factory;
    private final AgentLayerInterpolator interpolator;

    public AgentLayerLoader() {
        factory = new AgentLayerFactory();
        interpolator = new AgentLayerInterpolator();
    }

    public AgentLayerLoader(AgentLayerFactory factory, AgentLayerInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public CellLayer instantiate(MapObjectNode child, GeometryDescriptor geom) {
        Boundary boundary = interpolator.boundary(child, geom);
        Geometry geometry = geom.make(boundary);
        return new CellLayer(geometry);
    }

    public CellLayer instantiate(GeometryDescriptor geom) {
        return instantiate(null, geom);
    }
}
