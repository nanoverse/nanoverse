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

package structural.utilities;

import junit.framework.TestCase;
import no.uib.cipr.matrix.Matrices;
import no.uib.cipr.matrix.sparse.CompDiagMatrix;

/**
 * Created by Daniel Greenidge on 2015-06-15.
 */
public class MatrixUtilsTest extends TestCase {

    public void testCompDiagIdentity() throws Exception {
        int size = 3;
        CompDiagMatrix expected = new CompDiagMatrix(Matrices.identity(size));
        CompDiagMatrix actual = MatrixUtils.CompDiagIdentity(size);

        // We have to do this because CompDiagMatrix doesn't override Object.equals()
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                assertTrue(EpsilonUtil.epsilonEquals(expected.get(i, j), actual.get(i, j)));
            }
        }
    }
}