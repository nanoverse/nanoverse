package nanoverse.runtime.geometry.basis;

import nanoverse.runtime.geometry.lattice.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BasisHelperSupplier2DTest {

    @Test
    public void basisHelperRectangle() throws Exception {
        RectangularLattice lattice = new RectangularLattice();
        Class actual = BasisHelperSupplier2D
            .resolveBasisHelper(lattice).getClass();

        Class expected = RectangularBasisHelper.class;
        assertEquals(expected, actual);
    }

    @Test
    public void basisHelperTriangle() throws Exception {
        TriangularLattice lattice = new TriangularLattice();
        Class actual = BasisHelperSupplier2D
            .resolveBasisHelper(lattice).getClass();

        Class expected = TriangularBasisHelper.class;
        assertEquals(expected, actual);
    }

}