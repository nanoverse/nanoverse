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

package layers;

import control.identifiers.Coordinate;
import layers.cell.CellLayerContent;
import layers.cell.InfiniteCellLayerContent;

import java.util.Set;

/**
 * Created by David B Borenstein on 4/10/14.
 */
public class InfiniteCellLayerContentTest extends CellLayerContentTest {
    public void testSanityCheck() {
        boolean thrown = false;
        try {
            query.sanityCheck(new Coordinate(-1, 0, 0));
        } catch (Exception ex) {
            thrown = true;
        }

        assertFalse(thrown);
    }

    public void testGetImaginarySites() {
        Coordinate imaginary = new Coordinate(-1, 0, 0);
        Coordinate real = new Coordinate(0, 0, 0);

        indices.setOccupied(imaginary, true);
        indices.setOccupied(real, true);

        Set<Coordinate> result = query.getImaginarySites();

        assertEquals(1, result.size());
        assertTrue(result.contains(imaginary));
        assertFalse(result.contains(real));
    }

    @Override
    public CellLayerContent makeQuery() {
        return new InfiniteCellLayerContent(geom, indices);
    }
}
