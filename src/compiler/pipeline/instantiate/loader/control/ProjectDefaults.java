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

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by dbborens on 8/13/15.
 */
public class ProjectDefaults {
    public GeneralParameters generalParameters() {
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        return new GeneralParameters(random, seed, 100, 1, ".", "nanoverse", false);
    }

    public GeometryDescriptor geometry() {
        Lattice lattice = new RectangularLattice();
        Shape shape = new Rectangle(lattice, 32, 32);
        GeometryDescriptor geom = new GeometryDescriptor(lattice, shape);
        return geom;
    }

    public LayerManager layers(GeometryDescriptor geom) {
        Boundary boundary = new Periodic(geom.getShape(), geom.getLattice());
        Geometry geometry = geom.make(boundary);
        CellLayer layer = new CellLayer(geometry);
        LayerManager lm = new LayerManager();
        lm.setCellLayer(layer);
        return lm;
    }

    public SerializationManager output(GeneralParameters p,
                                              LayerManager lm) {

        ProgressReporter progressReporter = new ProgressReporter(p, lm);
        List<Serializer> serializers = Stream.of(progressReporter)
                .collect(Collectors.toList());

        SerializationManager sm = new SerializationManager(p, lm, serializers);
        return sm;
    }
}
