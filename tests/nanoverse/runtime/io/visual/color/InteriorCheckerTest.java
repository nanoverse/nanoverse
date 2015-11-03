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

package nanoverse.runtime.io.visual.color;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.SystemState;
import org.junit.Before;
import org.junit.Test;
import test.LayerMocks;

import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/28/2015.
 */
public class InteriorCheckerTest extends LayerMocks {
    private InteriorChecker query;
    private Coordinate c;
    private SystemState state;

    @Override @Before
    public void before() throws Exception {
        super.before();
        c = mock(Coordinate.class);
        state = mock(SystemState.class);
        when(state.getLayerManager()).thenReturn(layerManager);

        query = new InteriorChecker();
    }

    @Test
    public void isInteriorYes() throws Exception {
        Stream<String> names = Stream.of("test");
        when(lookup.getNeighborNames(c, false)).thenReturn(names);
        assertTrue(query.isInterior(c, state));
    }

    @Test
    public void isInteriorNo() throws Exception {
        Stream<String> names = Stream.of("test", null);
        when(lookup.getNeighborNames(c, false)).thenReturn(names);
        assertFalse(query.isInterior(c, state));

    }
}