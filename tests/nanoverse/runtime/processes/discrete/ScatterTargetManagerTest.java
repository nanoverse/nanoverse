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

package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.set.*;
import nanoverse.runtime.processes.discrete.filter.Filter;
import org.junit.*;

import java.util.List;
import java.util.function.*;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class ScatterTargetManagerTest {

    private Filter siteCleaner;
    private Supplier<CoordinateSet> activeSites;
    private Consumer<List<Coordinate>> shuffler;
    private Coordinate c;
    private List<Coordinate> candidates;
    private ScatterTargetManager query;

    @Before
    public void before() throws Exception {
        c = mock(Coordinate.class);
        activeSites = () -> CustomSet.of(c);

        siteCleaner = mock(Filter.class);
        shuffler = mock(Consumer.class);
        candidates = mock(List.class);
        when(siteCleaner.apply(any())).thenReturn(candidates);

        query = new ScatterTargetManager(siteCleaner, activeSites, shuffler);
    }

    @Test
    public void noMax() throws Exception {
        doUnshuffledTest(-1, 2);
    }

    private void doUnshuffledTest(int maxTargets, int numTargets) throws Exception {
        when(candidates.size()).thenReturn(numTargets);
        query.target(null);
        List<Coordinate> actual = query.getTargets(maxTargets);
        assertSame(candidates, actual);
        verifyZeroInteractions(shuffler);

    }

    @Test
    public void belowMax() throws Exception {
        doUnshuffledTest(3, 2);
    }

    @Test
    public void atMax() throws Exception {
        doUnshuffledTest(3, 3);
    }

    @Test
    public void aboveMax() throws Exception {
        List<Coordinate> expected = mock(List.class);
        when(candidates.subList(0, 3)).thenReturn(expected);
        when(candidates.size()).thenReturn(4);

        query.target(null);
        List<Coordinate> actual = query.getTargets(3);
        assertSame(expected, actual);
    }

}