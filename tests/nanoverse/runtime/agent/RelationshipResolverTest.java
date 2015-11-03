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

package nanoverse.runtime.agent;

import nanoverse.runtime.agent.RelationshipResolver;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.continuum.*;
import org.junit.*;
import test.TestBase;

import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RelationshipResolverTest extends TestBase {

    private Supplier<Coordinate> locator;
    private RelationshipResolver query;

    @Before
    public void before() throws Exception {
        locator = mock(Supplier.class);
        query = new RelationshipResolver(locator);
    }

    @Test
    public void resolve() throws Exception {
        Coordinate c = mock(Coordinate.class);
        when(locator.get()).thenReturn(c);

        Reaction r = mock(Reaction.class);
        Supplier<RelationshipTuple> supplier = query.resolve(r);

        RelationshipTuple tuple = supplier.get();
        assertSame(c, tuple.getCoordinate());

        when(r.getExp()).thenReturn(-1.0);
        assertEquals(-1.0, tuple.getExp(), epsilon);

        when(r.getInj()).thenReturn(3.0);
        assertEquals(3.0, tuple.getInj(), epsilon);
    }
}