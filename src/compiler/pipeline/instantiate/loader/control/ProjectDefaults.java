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

import compiler.pipeline.instantiate.loader.geometry.GeometryDescriptorLoader;
import compiler.pipeline.instantiate.loader.io.serialize.OutputManagerLoader;
import compiler.pipeline.instantiate.loader.layers.LayerManagerLoader;
import control.GeneralParameters;
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
import layers.LayerManager;
import layers.cell.CellLayer;
import org.slf4j.*;
import structural.Version;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by dbborens on 8/13/15.
 */
public class ProjectDefaults {

    private final Logger logger = LoggerFactory.getLogger(ProjectDefaults.class);

    public GeneralParameters generalParameters() {
        ParametersLoader loader = new ParametersLoader();
        return loader.instantiate();
    }

    public GeometryDescriptor geometry() {
        GeometryDescriptorLoader loader = new GeometryDescriptorLoader();
        return loader.instantiate();
    }

    public LayerManager layers(GeometryDescriptor geom, GeneralParameters p) {
        LayerManagerLoader loader = new LayerManagerLoader();
        return loader.instantiate(geom, p);
    }

    public SerializationManager output(GeneralParameters p,
                                              LayerManager lm) {

        OutputManagerLoader loader = new OutputManagerLoader();
        return loader.instantiate(p, lm);
    }

    public String version() {
        logger.warn("Nanoverse version not specified in project definition. " +
            "Compiler cannot verify compatibility. Project may not run " +
            "as expected!");

        return Version.VERSION;
    }
}
