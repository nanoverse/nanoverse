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

package processes.continuum;

import org.junit.Before;
import org.junit.Test;
import test.TestBase;

import static org.junit.Assert.assertEquals;

public class DiffusionConstantHelperTest extends TestBase {

    private int dimensionality;
    private int connectivity;
    private double baseConstant;
    private DiffusionConstantHelper query;

    @Before
    public void init() throws Exception {
        // i.e., a triangular 2D lattice
        dimensionality = 2;
        connectivity = 3;

        baseConstant = 0.1;

        query = new DiffusionConstantHelper(baseConstant, connectivity, dimensionality);
    }

    @Test
    public void neighborAccountsForGeometry() {
        double expected = baseConstant * dimensionality / connectivity;
        assertEquals(expected, query.getNeighborValue(), epsilon);
    }

    /**
     * The origin concentration should be the negative of the sum of amount
     * transferred to neighbors. (Negative because the operator is actually
     * Q - I, where Q is the "true" diffusion matrix--i.e., how much the
     * origin will lose.)
     * <p>
     * There are n*m neighbors, and each gets D*n/m. So the origin should
     * lose (n^2) * D.
     */
    @Test
    public void totalConcentrationConserved() {
        double expected = -1.0 * Math.pow(dimensionality, 2.0) * baseConstant;
        assertEquals(expected, query.getDiagonalValue(), epsilon);
    }

    /**
     * If the origin would lose more than the total of its original
     * concentration, then the input is illegal.
     */
    @Test(expected = IllegalArgumentException.class)
    public void excessiveInputThrows() {
        query = new DiffusionConstantHelper(0.51, connectivity, dimensionality);
    }

    /**
     * Diffusion constants cannot be negative.
     */
    @Test(expected = IllegalArgumentException.class)
    public void belowZeroThrows() {
        query = new DiffusionConstantHelper(-1.0, connectivity, dimensionality);
    }
}