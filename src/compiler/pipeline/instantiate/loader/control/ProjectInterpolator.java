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

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.instantiate.loader.geometry.GeometryDescriptorLoader;
import compiler.pipeline.instantiate.loader.io.serialize.OutputManagerLoader;
import compiler.pipeline.instantiate.loader.layers.LayerManagerLoader;
import compiler.pipeline.instantiate.loader.primitive.strings.StringArgumentLoader;
import compiler.pipeline.translate.nodes.*;
import control.GeneralParameters;
import control.ProcessManager;
import control.arguments.GeometryDescriptor;
import io.serialize.SerializationManager;
import layers.LayerManager;
import structural.Version;

/**
 * Created by dbborens on 8/13/15.
 */
public class ProjectInterpolator {

    private final LoadHelper load;
    private final ProjectDefaults defaults;

    public ProjectInterpolator() {
        load = new LoadHelper();
        defaults = new ProjectDefaults();
    }

    public ProjectInterpolator(LoadHelper load, ProjectDefaults defaults) {
        this.load = load;
        this.defaults = defaults;
    }



    public void version(MapObjectNode node) {
        String version = load.aString(node, "version", defaults::version);

        if (!version.equals(Version.VERSION)) {
            throw new IllegalArgumentException("Version mismatch: Source file " +
                    "written for Nanoverse v. " + version + ", but this is v. " +
                    Version.VERSION + ".");
        }
    }

    public GeneralParameters generalParameters(MapObjectNode node) {
        ParametersLoader loader = (ParametersLoader)
            load.getLoader(node, "parameters", false);

        if (loader == null) {
            return defaults.generalParameters();
        }

        MapObjectNode childNode = (MapObjectNode) node.getMember("parameters");

        GeneralParameters p = loader.instantiate(childNode);
        return p;
    }

    public GeometryDescriptor geometry(MapObjectNode node, GeneralParameters p) {
        GeometryDescriptorLoader loader = (GeometryDescriptorLoader)
                load.getLoader(node, "geometry", false);

        if (loader == null) {
            return defaults.geometry(p);
        }

        MapObjectNode childNode = (MapObjectNode) node.getMember("geometry");

        GeometryDescriptor geom = loader.instantiate(childNode, p);
        return geom;
    }

    public LayerManager layers(MapObjectNode node,
                               GeometryDescriptor geom,
                               GeneralParameters p) {

        LayerManagerLoader loader = (LayerManagerLoader)
                load.getLoader(node, "layers", false);

        if (loader == null) {
            return defaults.layers(geom, p);
        }

        ListObjectNode childNode = (ListObjectNode) node.getMember("layers");


        LayerManager layerManager = loader.instantiate(childNode, geom, p);
        return layerManager;
    }

    public SerializationManager output(MapObjectNode node, GeneralParameters p, LayerManager layerManager) {
        OutputManagerLoader loader = (OutputManagerLoader)
                load.getLoader(node, "output", false);

        if (loader == null) {
            return defaults.output(p, layerManager);
        }

        ListObjectNode childNode = (ListObjectNode) node.getMember("output");

        SerializationManager output = loader.instantiate(childNode, p, layerManager);
        return output;
    }

    public ProcessManager processes(MapObjectNode node, GeneralParameters p, LayerManager layerManager) {
        ProcessManagerLoader loader = (ProcessManagerLoader)
                load.getLoader(node, "processes", true);

        ListObjectNode childNode = (ListObjectNode) node.getMember("processes");

        ProcessManager processes = loader.instantiate(childNode, layerManager, p);
        return processes;
    }
}
