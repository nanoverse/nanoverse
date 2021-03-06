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

package nanoverse.runtime.structural.postprocess;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.MockGeometry;
import nanoverse.runtime.structural.utilities.EpsilonUtil;
import no.uib.cipr.matrix.DenseVector;
import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by dbborens on 12/16/13.
 */
public class SolutionViewerTest {
    private MockGeometry geom;
    private SolutionViewer viewer;
    private Coordinate origin;
    private Coordinate other;

    @Before
    public void setUp() throws Exception {
        geom = new MockGeometry();

        origin = new Coordinate2D(1, 1, Flags.VECTOR);
        other = new Coordinate2D(1, 2, Flags.VECTOR);

        Coordinate[] cc = new Coordinate[]{
            origin.canonicalize(),
            other.canonicalize()
        };

        geom.setCanonicalSites(cc);

        // Make sure that got assigned the way we expect
        assertEquals(0, geom.getIndexer().apply(origin).intValue());
        assertEquals(1, geom.getIndexer().apply(other).intValue());

        geom.setCenter(origin.canonicalize());

        DenseVector solution = new DenseVector(2);
        solution.set(0, 1.0);
        solution.set(1, 5.0);

        viewer = new SolutionViewer(solution, geom);
    }

    @Test
    public void get() throws Exception {
        Coordinate originOffset = new Coordinate2D(0, 0, Flags.VECTOR);
        Coordinate otherOffset = new Coordinate2D(0, 1, Flags.VECTOR);

        assertEquals(1.0, viewer.getRelative(originOffset), EpsilonUtil.epsilon());
        assertEquals(5.0, viewer.getRelative(otherOffset), EpsilonUtil.epsilon());
    }

    @Test
    public void getSolution() throws Exception {
        DenseVector solution = viewer.getSolution();

        assertEquals(2, solution.size());
        assertEquals(1.0, solution.get(0), EpsilonUtil.epsilon());
        assertEquals(5.0, solution.get(1), EpsilonUtil.epsilon());
    }

    @Test
    public void getAbsolute() throws Exception {
        assertEquals(1.0, viewer.getAbsolute(origin.canonicalize()), EpsilonUtil.epsilon());
        assertEquals(5.0, viewer.getAbsolute(other.canonicalize()), EpsilonUtil.epsilon());
    }
}
