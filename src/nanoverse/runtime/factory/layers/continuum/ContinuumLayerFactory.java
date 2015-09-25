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

package nanoverse.runtime.factory.layers.continuum;

import nanoverse.runtime.control.arguments.GeometryDescriptor;
import nanoverse.runtime.factory.geometry.boundaries.BoundaryFactory;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.Boundary;
import nanoverse.runtime.layers.continuum.ContinuumLayer;
import nanoverse.runtime.layers.continuum.ContinuumLayerContent;
import nanoverse.runtime.layers.continuum.ContinuumLayerScheduler;
import org.dom4j.Element;

/**
 * Created by dbborens on 1/8/15.
 */
public abstract class ContinuumLayerFactory {

    public static ContinuumLayer instantiate(Element root, GeometryDescriptor geometryDescriptor) {
        Geometry geom = makeGeometry(root, geometryDescriptor);
        String id = root.element("id").getText();
        ContinuumLayerContent content = new ContinuumLayerContent(geom);
        ContinuumLayerScheduler scheduler = ContinuumLayerSchedulerFactory.instantiate(root, content, geom, id);

        return new ContinuumLayer(scheduler, content, geom);
    }

    private static Geometry makeGeometry(Element root, GeometryDescriptor geometryDescriptor) {
        Boundary boundary = makeBoundary(root, geometryDescriptor);
        Geometry geom = geometryDescriptor.make(boundary);
        return geom;
    }

    private static Boundary makeBoundary(Element root, GeometryDescriptor geometryDescriptor) {
        Element boundaryElem = root.element("boundary");
        Boundary boundary = BoundaryFactory.instantiate(boundaryElem, geometryDescriptor);
        return boundary;
    }
}
