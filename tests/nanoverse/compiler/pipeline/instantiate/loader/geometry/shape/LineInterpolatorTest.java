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

package nanoverse.compiler.pipeline.instantiate.loader.geometry.shape;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import org.junit.*;
import org.mockito.ArgumentCaptor;

import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LineInterpolatorTest extends InterpolatorTest {

    private LineInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        query = new LineInterpolator(load);
    }

    @Test
    public void length() throws Exception {
        when(load.anInteger(eq(node), eq("length"), any(), any()))
            .thenReturn(5);
        int actual = query.length(node, random);
        assertEquals(5, actual);
    }

    @Test
    public void lengthDefault() throws Exception {
        ArgumentCaptor<Supplier> captor = ArgumentCaptor.forClass(Supplier.class);
        when(load.anInteger(eq(node), eq("length"), any(), any()))
            .thenReturn(0);

        query.length(node, random);
        verify(load).anInteger(any(), any(), any(), captor.capture());
        int actual = (Integer) captor.getValue().get();
        assertEquals(LineInterpolator.DEFAULT_LENGTH, actual);
    }
}