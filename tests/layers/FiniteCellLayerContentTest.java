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

import control.identifiers.Coordinate2D;
import layers.cell.*;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by David B Borenstein on 4/10/14.
 */
public class FiniteCellLayerContentTest extends CellLayerContentTest {

    @Test
    public void testImaginaryBehavior() {

        boolean thrown = false;
        try {
            query.sanityCheck(new Coordinate2D(-1, 0, 0));
        } catch (Exception ex) {
            thrown = true;
        }

        assertTrue(thrown);

        assertNotNull(query.getImaginarySites());
        assertEquals(0, query.getImaginarySites().size());
    }

    @Override
    public CellLayerContent makeQuery() {
        return new FiniteCellLayerContent(geom, indices);
    }
}
