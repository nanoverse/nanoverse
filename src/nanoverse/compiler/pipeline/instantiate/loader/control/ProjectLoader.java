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

import nanoverse.compiler.pipeline.instantiate.factory.control.run.ProjectFactory;
import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.Integrator;
import nanoverse.runtime.control.ProcessManager;
import nanoverse.runtime.control.arguments.GeometryDescriptor;
import nanoverse.runtime.control.run.Runner;
import nanoverse.runtime.io.serialize.SerializationManager;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 8/1/2015.
 */
public class ProjectLoader extends Loader<Runner> {

    private final ProjectFactory factory;
    private final IntegratorLoader integratorLoader;
    private final ProjectInterpolator interpolator;

    public ProjectLoader() {
        factory = new ProjectFactory();
        interpolator = new ProjectInterpolator();
        integratorLoader = new IntegratorLoader();
    }

    public ProjectLoader(ProjectFactory factory, ProjectInterpolator interpolator, IntegratorLoader integratorLoader) {
        this.factory = factory;
        this.interpolator = interpolator;
        this.integratorLoader = integratorLoader;
    }

    public Runner instantiate(MapObjectNode node) {
        interpolator.version(node);

        GeneralParameters p = interpolator.generalParameters(node);
        factory.setP(p);

        Integrator integrator = integrator(node, p);
        factory.setIntegrator(integrator);

        return factory.build();
    }

    private Integrator integrator(MapObjectNode node, GeneralParameters p) {
        GeometryDescriptor geom = interpolator.geometry(node, p);
        LayerManager layerManager = interpolator.layers(node, geom, p);
        SerializationManager output = interpolator.output(node, p, layerManager);
        ProcessManager processes = interpolator.processes(node, p, layerManager);

        return integratorLoader.instantiate(p, processes, output);
    }
}
