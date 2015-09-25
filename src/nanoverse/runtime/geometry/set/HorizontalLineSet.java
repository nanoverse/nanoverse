/*
 *  Copyright (c) 2014 David Bruce Borenstein and the Trustees of
 *  Princeton University. All rights reserved.
 */

package nanoverse.runtime.geometry.set;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.structural.NotYetImplementedException;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * This is a cloodge to create a horizontal line in a 2D nanoverse.runtime.geometry (tri or rect).
 * In order to create an arbitrary line in an arbitrary goemetry, I would need
 * to implement the Bresenham algorithm, which would then require substantial
 * finnicking to get it to work with all geometries. Briefly, if I want to use
 * Bresenham in 2 dimensions, I need to handle each octant; if I want to use it
 * in 3 dimensions (or on a triangular lattice), I need to do two Bresenhams,
 * with one dimension in common.
 *
 * One of the uglier parts of this cloodge is manually adding in the
 * x-correction to the y-coordinate on triangular lattices, despite there being
 * code to handle that on the Lattice class itself. The entire Geometry
 * hierarchy is up for a big refactor, so I'm not going to worry about that.
 *
 * Created by dbborens on 7/28/14.
 */
public class HorizontalLineSet extends CoordinateSet {

    @FactoryTarget(displayName = "HLineCoordinateSet")
    public HorizontalLineSet(Geometry geom, Coordinate start, int length) {
        if (geom.getDimensionality() != 2) {
            throw new NotYetImplementedException();
        }

        if (length < 0) {
            throw new NotYetImplementedException();
        }

        Function<Integer, Coordinate> mapping;
        if (geom.getConnectivity() == 2) {
            mapping = dx -> dxRect(geom, start, dx);
        } else if (geom.getConnectivity() == 3) {
            mapping = dx -> dxTri(geom, start, dx);
        } else {
            throw new IllegalArgumentException("Unrecognized 2D nanoverse.runtime.geometry");
        }
        apply(length, mapping);
    }


    private void apply(int length, Function<Integer, Coordinate> mapping) {
        IntStream.range(0, length)
                .mapToObj(mapping::apply)
                .forEach(this::add);
    }

    private Coordinate dxRect(Geometry geom, Coordinate start, int dx) {
        Coordinate disp = new Coordinate2D(dx, 0, 0);
        return geom.rel2abs(start, disp, Geometry.APPLY_BOUNDARIES);
    }

    private Coordinate dxTri(Geometry geom, Coordinate start, int dx) {
        // I really need to refactor the whole nanoverse.runtime.geometry hierarchy.
        int yAdj = (start.x() + dx) / 2;
        Coordinate disp = new Coordinate3D(dx, 0, yAdj, 0);
        return geom.rel2abs(start, disp, Geometry.APPLY_BOUNDARIES);
    }
}
