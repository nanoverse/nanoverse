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

package factory.layers.continuum;

import control.arguments.GeometryDescriptor;
import control.identifiers.Coordinate;
import factory.geometry.boundaries.BoundaryFactory;
import geometry.Geometry;
import geometry.boundaries.Boundary;
import layers.continuum.ContinuumLayer;
import layers.continuum.ContinuumLayerContent;
import layers.continuum.ContinuumLayerScheduler;
import org.dom4j.Element;

import java.util.function.Function;

/**
 * Created by dbborens on 1/8/15.
 */
public abstract class ContinuumLayerFactory {

    public static ContinuumLayer instantiate(Element root, GeometryDescriptor geometryDescriptor) {
        Geometry geom = makeGeometry(root, geometryDescriptor);
        int n = geom.getCanonicalSites().length;
        Function<Coordinate, Integer> indexer = geom.getIndexer();
        String id = root.element("id").getText();

        ContinuumLayerContent content = new ContinuumLayerContent(indexer, geom.getCanonicalSites().length);
        ContinuumLayerScheduler scheduler = ContinuumLayerSchedulerFactory.instantiate(root, content, indexer, n, id);

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
