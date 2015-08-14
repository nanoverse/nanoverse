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

package compiler.pipeline.instantiate.loader.control;

import compiler.pipeline.instantiate.loader.LoadHelper;
import compiler.pipeline.instantiate.loader.geometry.GeometryDescriptorLoader;
import compiler.pipeline.instantiate.loader.io.serialize.OutputManagerLoader;
import compiler.pipeline.instantiate.loader.layers.LayerManagerLoader;
import compiler.pipeline.instantiate.loader.primitive.strings.StringArgumentLoader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import compiler.pipeline.translate.nodes.ObjectNode;
import control.GeneralParameters;
import control.ProcessManager;
import control.arguments.GeometryDescriptor;
import geometry.Geometry;
import geometry.boundaries.Boundary;
import geometry.boundaries.Periodic;
import geometry.lattice.Lattice;
import geometry.lattice.RectangularLattice;
import geometry.shape.Rectangle;
import geometry.shape.Shape;
import io.serialize.SerializationManager;
import io.serialize.Serializer;
import io.serialize.interactive.ProgressReporter;
import io.visual.map.MapVisualization;
import layers.LayerManager;
import layers.cell.CellLayer;
import structural.Version;
import structural.utilities.EpsilonUtil;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by dbborens on 8/13/15.
 */
public class ProjectInterpolator {

    private final LoadHelper loadHelper;
    private final ProjectDefaults defaults;

    public ProjectInterpolator() {
        loadHelper = new LoadHelper();
        defaults = new ProjectDefaults();
    }

    public ProjectInterpolator(LoadHelper loadHelper, ProjectDefaults defaults) {
        this.loadHelper = loadHelper;
        this.defaults = defaults;
    }



    public void version(MapObjectNode node) {
        ObjectNode childNode = node.getMember("version");
        StringArgumentLoader loader = (StringArgumentLoader)
                loadHelper.getLoader(node, "version", true);

        String version = loader.instantiateToFirst(childNode);

        if (!version.equals(Version.VERSION)) {
            throw new IllegalArgumentException("Version mismatch: Source file " +
                    "written for Nanoverse " + version + ", but this is " +
                    Version.VERSION + ".");
        }
    }

    public GeneralParameters generalParameters(MapObjectNode node) {
        ObjectNode childNode = node.getMember("parameters");
        ParametersLoader loader = (ParametersLoader)
                loadHelper.getLoader(node, "parameters", false);

        if (loader == null) {
            return defaults.generalParameters();
        }

        GeneralParameters p = loader.instantiate(childNode);
        return p;
    }

    public GeometryDescriptor geometry(MapObjectNode node) {
        ObjectNode childNode = node.getMember("geometry");
        GeometryDescriptorLoader loader = (GeometryDescriptorLoader)
                loadHelper.getLoader(node, "geometry", false);

        if (loader == null) {
            return defaults.geometry();
        }

        GeometryDescriptor geom = loader.instantiate(childNode);
        return geom;
    }

    public LayerManager layers(MapObjectNode node, GeometryDescriptor geom) {
        ObjectNode childNode = node.getMember("layers");
        LayerManagerLoader loader = (LayerManagerLoader)
                loadHelper.getLoader(node, "layers", false);

        if (loader == null) {
            return defaults.layers(geom);
        }

        LayerManager layerManager = loader.instantiate(childNode, geom);
        return layerManager;
    }

    public SerializationManager output(MapObjectNode node, GeneralParameters p, LayerManager layerManager) {
        ObjectNode childNode = node.getMember("output");
        OutputManagerLoader loader = (OutputManagerLoader)
                loadHelper.getLoader(node, "output", false);

        if (loader == null) {
            return defaults.output(p, layerManager);
        }

        SerializationManager output = loader.instantiate(childNode, p, layerManager);
        return output;
    }

    public ProcessManager processes(MapObjectNode node, GeneralParameters p, LayerManager layerManager) {
        ObjectNode childNode = node.getMember("processes");
        ProcessManagerLoader loader = (ProcessManagerLoader)
                loadHelper.getLoader(node, "processes", true);

        ProcessManager processes = loader.instantiate(childNode, p, layerManager);
        return processes;
    }


}
