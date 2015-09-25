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

package nanoverse.runtime.layers.continuum;

import no.uib.cipr.matrix.*;
import org.junit.*;
import test.LinearMocks;

import java.util.List;
import java.util.stream.*;

public class AgentToOperatorHelperTest extends LinearMocks {

    private AgentToOperatorHelper query;
    private List<RelationshipTuple> list;
    private Reaction reaction;

    @Before
    public void init() throws Exception {
        reaction = new Reaction(1.0, 2.0, "test");
        RelationshipTuple relationship = new RelationshipTuple(a, reaction);
        list = Stream.of(relationship).collect(Collectors.toList());
        query = new AgentToOperatorHelper(geom, true);
    }

    @Test
    public void getSource() {
        DenseVector expected = vector(1.0, 0.0, 0.0);
        DenseVector actual = query.getSource(list);
        assertVectorsEqual(expected, actual, epsilon);
    }

    @Test
    public void getOperator() {
        Matrix expected = matrix(2.0, 0.0, 0.0);
        Matrix actual = query.getOperator(list);
        assertMatricesEqual(expected, actual, epsilon);
    }

}