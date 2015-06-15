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

package test;

import control.identifiers.Coordinate;
import geometry.Geometry;
import no.uib.cipr.matrix.DenseVector;
import no.uib.cipr.matrix.Matrix;
import no.uib.cipr.matrix.Vector;
import no.uib.cipr.matrix.sparse.CompDiagMatrix;
import org.junit.Before;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by dbborens on 1/9/15.
 */
public abstract class LinearMocks extends TestBase {

    protected Geometry geom;
    protected Coordinate[] cc;
    protected Coordinate a, b, c;
    protected Function<Coordinate, Integer> indexer;

    protected static Coordinate[] makeCanonicalCoordinates() {
        Coordinate[] cc = new Coordinate[3];
        cc = IntStream.range(0, 3)
                .boxed()
                .map(x -> new Coordinate(0, x, 0))
                .collect(Collectors.toList()).toArray(cc);

        return cc;
    }

    protected static DenseVector vector(double x0, double x1, double x2) {
        double[] arr = new double[]{x0, x1, x2};
        return new DenseVector(arr);
    }

    protected static CompDiagMatrix matrix(double x00, double x11, double x22) {
        CompDiagMatrix matrix = new CompDiagMatrix(3, 3);
        matrix.add(0, 0, x00);
        matrix.add(1, 1, x11);
        matrix.add(2, 2, x22);
        return matrix;
    }

    @Before
    public void makeGeometry() {
        cc = makeCanonicalCoordinates();

        geom = mock(Geometry.class);
        when(geom.getCanonicalSites()).thenReturn(cc);
        a = cc[0];
        b = cc[1];
        c = cc[2];

        indexer = c -> c.y();
        when(geom.getIndexer()).thenReturn(indexer);

        makeNeighbors();
    }

    /**
     * Absorbing boundary conditions
     */
    private void makeNeighbors() {
        when(geom.getNeighbors(eq(a), anyInt())).thenReturn(new Coordinate[]{b});
        when(geom.getNeighbors(eq(b), anyInt())).thenReturn(new Coordinate[]{a, c});
        when(geom.getNeighbors(eq(c), anyInt())).thenReturn(new Coordinate[]{b});
    }

    protected void checkMatrix(Matrix actual, double x0, double x1, double x2) {
        Matrix expected = matrix(x0, x1, x2);
        assertMatricesEqual(expected, actual, epsilon);
    }

    protected void checkVector(Vector actual, double x0, double x1, double x2) {
        DenseVector expected = vector(x0, x1, x2);
        assertVectorsEqual(expected, actual, epsilon);
    }
}
