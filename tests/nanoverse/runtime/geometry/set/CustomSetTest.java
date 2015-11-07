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

package nanoverse.runtime.geometry.set;

import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;
import org.junit.Test;
import test.LegacyTest;

import static org.junit.Assert.*;

/**
 * Since the custom set is agnostic to nanoverse.runtime.geometry,
 * this test looks a little different from the other
 * coordinate set tests.
 *
 * @throws Exception
 */
public class CustomSetTest extends LegacyTest {

    @Test
    public void testLifeCycle() throws Exception {
        Coordinate c = new Coordinate2D(0, 0, 0);
        CustomSet set = new CustomSet();

        assertEquals(0, set.size());
        assertFalse(set.contains(c));

        set.add(c);

        assertEquals(1, set.size());
        assertTrue(set.contains(c));
    }

    /**
     * Confirm that coordinate sets are equivalent as long
     * as they have the same contents
     *
     * @throws Exception
     */
    @Test
    public void testEquivalency() throws Exception {
        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, 3);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        IntegerArgument radiusArg = new ConstantInteger(0);
        Coordinate offset = new Coordinate2D(0, -1, 0);
        DiscSet discSet = new DiscSet(geom, radiusArg, offset);

        Coordinate c = new Coordinate2D(0, 0, 0);
        CustomSet customSet = new CustomSet();
        customSet.add(c);

        assertEquals(discSet, customSet);
    }
}