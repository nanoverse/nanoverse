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

package nanoverse.runtime.processes.discrete.filter;

import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.identifiers.*;
import org.junit.*;
import test.LayerMocks;

import java.util.*;
import java.util.stream.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DepthFilterTest extends LayerMocks {

    private static final int DEPTH = 2;

    private IntegerArgument depthArg;
    private DepthFilter query;
    private Coordinate c;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        depthArg = mock(IntegerArgument.class);
        when(depthArg.next()).thenReturn(DEPTH);
        c = mock(Coordinate.class);

        query = new DepthFilter(agentLayer, depthArg);

    }

    @Test
    public void hasVacanciesPasses() throws Exception {
        configureVacancies(true);
        List<Coordinate> input = Stream.of(c).collect(Collectors.toList());
        List<Coordinate> expected = input;
        List<Coordinate> actual = query.apply(input);
        assertEquals(expected, actual);
    }

    private void configureVacancies(boolean hasVacancies) {
        Coordinate[] vacancies = hasVacancies ?
            new Coordinate[]{new Coordinate1D(0, 0)} :
            new Coordinate[0];

        when(lookup.getNearestVacancies(c, DEPTH + 1))
            .thenReturn(vacancies);
    }

    @Test
    public void noVacanciesFiltered() throws Exception {
        configureVacancies(false);
        List<Coordinate> input = Stream.of(c).collect(Collectors.toList());
        List<Coordinate> expected = new ArrayList<>(0);
        List<Coordinate> actual = query.apply(input);
        assertEquals(expected, actual);
    }
}