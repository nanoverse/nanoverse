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

package structural.postprocess;

import control.identifiers.Coordinate;
import control.identifiers.Flags;
import geometry.MockGeometry;
import junit.framework.TestCase;
import no.uib.cipr.matrix.DenseVector;
import structural.utilities.EpsilonUtil;

/**
 * Created by dbborens on 12/16/13.
 */
public class SolutionViewerTest extends TestCase {
    private MockGeometry geom;
    private SolutionViewer viewer;
    private Coordinate origin;
    private Coordinate other;

    @Override
    protected void setUp() throws Exception {
        geom = new MockGeometry();

        origin = new Coordinate(1, 1, Flags.VECTOR);
        other = new Coordinate(1, 2, Flags.VECTOR);

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

    public void testGet() throws Exception {
        Coordinate originOffset = new Coordinate(0, 0, Flags.VECTOR);
        Coordinate otherOffset = new Coordinate(0, 1, Flags.VECTOR);

        assertEquals(1.0, viewer.getRelative(originOffset), EpsilonUtil.epsilon());
        assertEquals(5.0, viewer.getRelative(otherOffset), EpsilonUtil.epsilon());
    }

    public void testGetSolution() throws Exception {
        DenseVector solution = viewer.getSolution();

        assertEquals(2, solution.size());
        assertEquals(1.0, solution.get(0), EpsilonUtil.epsilon());
        assertEquals(5.0, solution.get(1), EpsilonUtil.epsilon());
    }

    public void testGetAbsolute() throws Exception {
        assertEquals(1.0, viewer.getAbsolute(origin.canonicalize()), EpsilonUtil.epsilon());
        assertEquals(5.0, viewer.getAbsolute(other.canonicalize()), EpsilonUtil.epsilon());
    }
}
