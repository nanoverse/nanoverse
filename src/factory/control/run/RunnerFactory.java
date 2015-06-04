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

package factory.control.run;

import control.GeneralParameters;
import control.Integrator;
import control.arguments.GeometryDescriptor;
import control.run.Runner;
import factory.control.IntegratorFactory;
import factory.control.arguments.GeometryDescriptorFactory;
import factory.layers.LayerManagerFactory;
import layers.LayerManager;
import org.dom4j.Element;

/**
 * Created by dbborens on 11/23/14.
 */
public abstract class RunnerFactory {

    // Version -- checked against parameters file to make sure they're
    // compatible
    private final static String VERSION = "0.6.7";

    // This line is included so that git will detect a change

    public static Runner instantiate(String projectPath) {
        Element root = DocumentFactory.instantiate(projectPath);
        validate(root);
        GeneralParameters p = makeGeneralParameters(root);
        GeometryDescriptor geometryDescriptor = makeGeometryDescriptor(root);
        LayerManager layerManager = makeLayerManager(root, geometryDescriptor);
        Integrator integrator = makeIntegrator(root, p, layerManager);
        Runner runner = new Runner(p, integrator);
        return runner;
    }

    private static LayerManager makeLayerManager(Element root, GeometryDescriptor geometryDescriptor) {
        Element layerRoot = root.element("layers");
        LayerManager layerManager = LayerManagerFactory.instantiate(layerRoot, geometryDescriptor);
        return layerManager;
    }

    private static Integrator makeIntegrator(Element root, GeneralParameters p, LayerManager lm) {
        return IntegratorFactory.instantiate(root, p, lm);
    }

    private static GeneralParameters makeGeneralParameters(Element root) {
        Element gpRoot = root.element("general");
        GeneralParameters p = new GeneralParameters(gpRoot);
        return p;
    }

    private static GeometryDescriptor makeGeometryDescriptor(Element root) {
        Element geometryElem = root.element("geometry");
        GeometryDescriptor geometryDescriptor = GeometryDescriptorFactory.instantiate(geometryElem);
        return geometryDescriptor;
    }

    private static void validate(Element root) {
        Element ve = root.element("version");

        if (ve == null) {
            System.out.println("The specified project file does not contain an eSLIME version number.");
        }
        String version = ve.getText();

        if (!version.equalsIgnoreCase(VERSION)) {

            String msg = "Version mismatch. Parameter file written for eSLIME "
                    + version + ", but this is " + VERSION + ".";
            throw new IllegalArgumentException(msg);

        }
    }

    public static String getVersion() {
        return VERSION;
    }
}
