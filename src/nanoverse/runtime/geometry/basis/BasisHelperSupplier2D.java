package nanoverse.runtime.geometry.basis;

import nanoverse.runtime.geometry.lattice.*;

/**
 * Created by dbborens on 11/16/2015.
 */
public abstract class BasisHelperSupplier2D {
    public static BasisHelper2D resolveBasisHelper(Lattice lattice) {
        if (lattice instanceof RectangularLattice) {
            return new RectangularBasisHelper();
        } else if (lattice instanceof TriangularLattice) {
            return new TriangularBasisHelper();
        } else {
            throw new IllegalStateException("Unrecognized 2D lattice \"" +
                lattice.getClass().getSimpleName() + ".\"");
        }
    }
}
