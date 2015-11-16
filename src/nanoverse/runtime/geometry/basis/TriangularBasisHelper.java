package nanoverse.runtime.geometry.basis;

import nanoverse.runtime.control.identifiers.*;

/**
 * Created by dbborens on 11/16/2015.
 */
public class TriangularBasisHelper implements BasisHelper2D {

    @Override
    public Coordinate adjust(Coordinate i) {
        if (!i.hasFlag(Flags.PLANAR)) {
            throw new IllegalArgumentException("Triangular lattice is a planar nanoverse.runtime.geometry.");
        }

        int yAdj;
        int x = i.x();
        if (x >= 0) {
            yAdj = (x / 2);
        } else {
            yAdj = ((x - 1) / 2);
        }

        Coordinate o = new Coordinate2D(i.x(), i.y() + yAdj, i.flags());

        return o;
    }

    @Override
    public Coordinate invAdjust(Coordinate i) {
        if (!i.hasFlag(Flags.PLANAR)) {
            throw new IllegalArgumentException("Triangular lattice is a planar nanoverse.runtime.geometry.");
        }

        int yAdj;
        int x = i.x();
        if (x >= 0) {
            yAdj = (x / 2);
        } else {
            yAdj = ((x - 1) / 2);
        }
        Coordinate o = new Coordinate2D(i.x(), i.y() - yAdj, i.flags());

        return o;
    }
}
