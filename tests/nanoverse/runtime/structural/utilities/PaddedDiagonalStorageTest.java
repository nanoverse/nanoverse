/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package nanoverse.runtime.structural.utilities;

import no.uib.cipr.matrix.sparse.CompDiagMatrix;
import org.junit.*;
import test.TestBase;

import static org.junit.Assert.assertEquals;

/**
 * Created on 6/17/15.
 *
 * @author Daniel Greenidge
 */
public class PaddedDiagonalStorageTest extends TestBase {
    private PaddedDiagonalStorage storage;
    private double[][] expectedStorage;

    @Before
    public void setUp() {
        CompDiagMatrix original = new CompDiagMatrix(4, 4);

        /**
         * We will be testing with this matrix:
         *
         * 1    0   7   0
         * 0    2   0   8
         * 5    0   3   0
         * 0    6   0   4
         *
         * The padded storage should behave like this:
         *
         * 5    6   0   0
         * 1    2   3   4
         * 0    0   7   8
         */

        original.set(0, 0, 1);
        original.set(0, 2, 7);

        original.set(1, 1, 2);
        original.set(1, 3, 8);

        original.set(2, 0, 5);
        original.set(2, 2, 3);

        original.set(3, 1, 6);
        original.set(3, 3, 4);

        storage = new PaddedDiagonalStorage(original);

        expectedStorage = new double[][]{
            {5, 6, 0, 0},
            {1, 2, 3, 4},
            {0, 0, 7, 8},
        };
    }

    @Test
    public void testGet() throws Exception {
        for (int i = 0; i < expectedStorage.length; i++) {
            for (int j = 0; j < expectedStorage[0].length; j++) {
                assertEquals(expectedStorage[i][j], storage.get(i, j), epsilon);
            }
        }
    }

    @Test
    public void testGetNumColumns() throws Exception {
        assertEquals(storage.getNumColumns(), expectedStorage[0].length);
    }

    @Test
    public void testGetNumRows() throws Exception {
        assertEquals(storage.getNumRows(), expectedStorage.length);
    }
}