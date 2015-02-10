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

package layers.continuum;

import factory.cell.Reaction;
import org.junit.Before;
import org.junit.Test;
import test.LinearMocks;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RelationshipTupleTest extends LinearMocks {

    private RelationshipTuple query;
    private Reaction reaction;

    @Before
    public void init() throws Exception {
        reaction = mock(Reaction.class);
        when(reaction.getExp()).thenReturn(1.0);
        when(reaction.getInj()).thenReturn(2.0);
        query = new RelationshipTuple(c, reaction);
    }

    @Test
    public void getCoordinate() throws Exception {
        assertEquals(c, query.getCoordinate());
    }

    public void getExp() throws Exception {
        assertEquals(1.0, query.getExp(), epsilon);
    }

    public void getInj() throws Exception {
        assertEquals(2.0, query.getInj(), epsilon);
    }
}