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

package nanoverse.compiler.pipeline.instantiate.loader.control;

import nanoverse.compiler.pipeline.instantiate.loader.geometry.GeometryDescriptorLoader;
import nanoverse.compiler.pipeline.instantiate.loader.io.serialize.OutputManagerLoader;
import nanoverse.compiler.pipeline.instantiate.loader.layers.LayerManagerLoader;
import nanoverse.runtime.control.*;
import nanoverse.runtime.control.arguments.GeometryDescriptor;
import nanoverse.runtime.io.serialize.SerializationManager;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.structural.Version;
import org.slf4j.*;

/**
 * Created by dbborens on 8/13/15.
 */
public class ProjectDefaults {

    private final Logger logger = LoggerFactory.getLogger(ProjectDefaults.class);

    public GeneralParameters generalParameters() {
        ParametersLoader loader = new ParametersLoader();
        return loader.instantiate();
    }

    public GeometryDescriptor geometry(GeneralParameters p) {
        GeometryDescriptorLoader loader = new GeometryDescriptorLoader();
        return loader.instantiate(p);
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

    public ProcessManager processes(GeneralParameters p, LayerManager layerManager) {
        ProcessManagerLoader loader = new ProcessManagerLoader();
        return loader.instantiate(layerManager, p);
    }
}
