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
import io.serialize.SerializationManager;
import layers.LayerManager;
import structural.Version;
import structural.utilities.EpsilonUtil;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Random;

/**
 * Created by dbborens on 8/13/15.
 */
public class ProjectInterpolator {

    public void version(MapObjectNode node) {
        ObjectNode childNode = node.getMember("version");
        StringArgumentLoader loader = (StringArgumentLoader)
                LoadHelper.getLoader(node, "version", true);

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
                LoadHelper.getLoader(node, "parameters", false);

        if (loader == null) {
            return defaultGeneralParameters();
        }

        GeneralParameters p = loader.instantiate(childNode);
        return p;
    }

    public GeometryDescriptor geometry(MapObjectNode node) {
        ObjectNode childNode = node.getMember("geometry");
        GeometryDescriptorLoader loader = (GeometryDescriptorLoader)
                LoadHelper.getLoader(node, "geometry", false);

        if (loader == null) {
            // TODO Implement default!
            throw new NotImplementedException();
        }

        GeometryDescriptor geom = loader.instantiate(childNode);
        return geom;
    }

    public LayerManager layers(MapObjectNode node, GeometryDescriptor geom) {
        ObjectNode childNode = node.getMember("layers");
        LayerManagerLoader loader = (LayerManagerLoader)
                LoadHelper.getLoader(node, "layers", false);

        if (loader == null) {
            // TODO Implement default!
            throw new NotImplementedException();
        }

        LayerManager layerManager = loader.instantiate(childNode, geom);
        return layerManager;
    }

    public SerializationManager output(MapObjectNode node, GeneralParameters p, LayerManager layerManager) {
        ObjectNode childNode = node.getMember("output");
        OutputManagerLoader loader = (OutputManagerLoader)
                LoadHelper.getLoader(node, "output", false);

        if (loader == null) {
            // TODO Implement default!
            throw new NotImplementedException();
        }

        SerializationManager output = loader.instantiate(childNode, p, layerManager);
        return output;
    }

    public ProcessManager processes(MapObjectNode node, GeneralParameters p, LayerManager layerManager) {
        ObjectNode childNode = node.getMember("processes");
        ProcessManagerLoader loader = (ProcessManagerLoader)
                LoadHelper.getLoader(node, "processes", true);

        ProcessManager processes = loader.instantiate(childNode, p, layerManager);
        return processes;
    }

    /* DEFAULTS */

    private GeneralParameters defaultGeneralParameters() {
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        double epsilon = EpsilonUtil.epsilon();
        return new GeneralParameters(random, seed, 100, 1, ".", "nanoverse", false, epsilon);
    }


}
