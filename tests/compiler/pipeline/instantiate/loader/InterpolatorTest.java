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

package compiler.pipeline.instantiate.loader;

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.translate.nodes.MapObjectNode;
import org.junit.Before;
import org.mockito.ArgumentCaptor;

import java.util.Random;
import java.util.function.Supplier;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;


/**
 * Created by dbborens on 8/19/2015.
 */
public class InterpolatorTest {

    protected LoadHelper load;
    protected MapObjectNode node;
    protected Random random;

    @Before
    public void before() throws Exception {
        random = new Random(1);
        load = mock(LoadHelper.class);
        node = mock(MapObjectNode.class);
    }

    protected void verifyIntegerDefault(String member, Integer expected, Runnable trigger) {
        when(load.anInteger(eq(node), eq(member), any(), any())).thenReturn(expected + 1);
        ArgumentCaptor<Supplier> captor = ArgumentCaptor.forClass(Supplier.class);
        trigger.run();
        verify(load).anInteger(any(), any(), eq(random), captor.capture());
        Integer actual = (Integer) captor.getValue().get();
        assertEquals(expected, actual);
    }

    protected void verifyStringDefault(String member, String expected, Runnable trigger) {
        when(load.aString(eq(node), eq(member), any())).thenReturn(expected + "1");
        ArgumentCaptor<Supplier> captor = ArgumentCaptor.forClass(Supplier.class);
        trigger.run();
        verify(load).aString(any(), any(), captor.capture());
        String actual = (String) captor.getValue().get();
        assertEquals(expected, actual);
    }

    protected void verifyBooleanDefault(String member, Boolean expected, Runnable trigger) {
        when(load.aBoolean(eq(node), eq(member), any(), any())).thenReturn(!expected);
        ArgumentCaptor<Supplier> captor = ArgumentCaptor.forClass(Supplier.class);
        trigger.run();
        verify(load).aBoolean(any(), any(), eq(random), captor.capture());
        Boolean actual = (Boolean) captor.getValue().get();
        assertEquals(expected, actual);
    }

}
