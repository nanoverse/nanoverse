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
import java.util.stream.Stream;

public class CompositeFilterTest extends TestCase {

    public void testApply() throws Exception {
        Filter child1 = new MockFilter();
        Filter child2 = new MockFilter();
        Stream<Filter> children = Stream.of(child1, child2);
        CompositeFilter query = new CompositeFilter(children);
        query.apply(new ArrayList<>(0));
        assertTrue(child1.isCalled());
        assertTrue(child2.isCalled());
    }
}