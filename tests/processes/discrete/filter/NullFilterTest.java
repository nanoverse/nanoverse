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

package processes.discrete.filter;

import control.identifiers.Coordinate;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collection;

public class NullFilterTest extends TestCase {
    private ArrayList<Coordinate> original, cloned;
    private NullFilter query;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        original = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            original.add(new Coordinate(i, i, 0));
        }

        cloned = (ArrayList<Coordinate>) original.clone();

        query = new NullFilter();
    }

    public void testApply() throws Exception {
        Collection<Coordinate> actual = query.apply(original);

        // The result should be identical to the original.
        assertEquals(original, actual);

        // The original should be identical to its replicate (ie, unmodified).
        assertEquals(cloned, original);
    }
}