package nanoverse.runtime.geometry.basis;

import nanoverse.runtime.control.identifiers.Coordinate;

/**
 * Created by dbborens on 11/16/2015.
 */
public class RectangularBasisHelper implements BasisHelper2D {

    @Override
    public Coordinate adjust(Coordinate c) {
        return c;
    }

    @Override
    public Coordinate invAdjust(Coordinate c) {
        return c;
    }
}
