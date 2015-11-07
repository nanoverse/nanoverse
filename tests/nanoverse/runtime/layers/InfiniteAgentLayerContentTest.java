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

package nanoverse.runtime.layers;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.layers.cell.*;
import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by David B Borenstein on 4/10/14.
 */
public class InfiniteAgentLayerContentTest extends AgentLayerContentTest {

    @Test
    public void testSanityCheck() {
        boolean thrown = false;
        try {
            query.sanityCheck(new Coordinate2D(-1, 0, 0));
        } catch (Exception ex) {
            thrown = true;
        }

        assertFalse(thrown);
    }

    @Test
    public void testGetImaginarySites() {
        Coordinate imaginary = new Coordinate2D(-1, 0, 0);
        Coordinate real = new Coordinate2D(0, 0, 0);

        indices.setOccupied(imaginary, true);
        indices.setOccupied(real, true);

        Set<Coordinate> result = query.getImaginarySites().collect(Collectors.toSet());

        assertEquals(1, result.size());
        assertTrue(result.contains(imaginary));
        assertFalse(result.contains(real));
    }

    @Override
    public AgentLayerContent makeQuery() {
        return new InfiniteAgentLayerContent(geom, indices);
    }
}
