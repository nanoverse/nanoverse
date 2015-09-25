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

package nanoverse.compiler.pipeline.instantiate.loader;

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.layers.LayerManager;
import org.junit.Before;
import org.mockito.ArgumentCaptor;
import test.TestBase;

import java.util.Random;
import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 8/19/2015.
 */
public class InterpolatorTest extends TestBase {

    protected LoadHelper load;
    protected MapObjectNode node;
    protected Random random;
    protected GeneralParameters p;
    protected LayerManager lm;

    @Before
    public void before() throws Exception {
        random = new Random(1);
        load = mock(LoadHelper.class);
        node = mock(MapObjectNode.class);
        p = mock(GeneralParameters.class);
        lm = mock(LayerManager.class);
    }

    protected void verifyIntegerDefault(String member, Integer expected, Runnable trigger) {
        when(load.anInteger(eq(node), eq(member), any(), any())).thenReturn(expected + 1);
        ArgumentCaptor<Supplier> captor = ArgumentCaptor.forClass(Supplier.class);
        trigger.run();
        verify(load).anInteger(any(), any(), eq(random), captor.capture());
        Integer actual = (Integer) captor.getValue().get();
        assertEquals(expected, actual);
    }

    protected void verifyIntegerArgumentDefault(String member, IntegerArgument expected, Runnable trigger) {
        IntegerArgument notExpected = mock(IntegerArgument.class);
        when(load.anIntegerArgument(eq(node), eq(member), any(), any()))
            .thenReturn(notExpected);
        ArgumentCaptor<Supplier> captor = ArgumentCaptor.forClass(Supplier.class);
        trigger.run();
        verify(load).anIntegerArgument(any(), any(), eq(random), captor.capture());
        IntegerArgument actual = (IntegerArgument) captor.getValue().get();
        assertSame(expected, actual);
    }

    protected void verifyString(String member, Supplier<String> trigger) {
        String expected = "expected";

        // For values with defaults
        when(load.aString(eq(node), eq(member), any())).thenReturn(expected);

        // For values without defaults
        when(load.aString(eq(node), eq(member))).thenReturn(expected);

        String actual = trigger.get();
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

    protected void verifyBoolean(String member, Supplier<Boolean> trigger) {
        boolean expected = true;

        // For values with defaults
        when(load.aBoolean(eq(node), eq(member), eq(random), any()))
            .thenReturn(expected);

        // For values without defaults
        when(load.aBoolean(eq(node), eq(member), eq(random)))
            .thenReturn(expected);

        boolean actual = trigger.get();
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

    protected void verifyInteger(String member, Supplier<Integer> trigger) {
        int expected = 7;

        // For values with defaults
        when(load.anInteger(eq(node), eq(member), eq(random), any())).thenReturn(expected);

        // For values without defaults
        when(load.anInteger(eq(node), eq(member), eq(random))).thenReturn(expected);

        int actual = trigger.get();
        assertEquals(expected, actual);
    }

    protected void verifyIntegerArgument(String member, Supplier<IntegerArgument> trigger) {
        IntegerArgument expected = mock(IntegerArgument.class);

        // For values with defaults
        when(load.anIntegerArgument(eq(node), eq(member), eq(random), any()))
            .thenReturn(expected);

        // For values without defaults
        when(load.anIntegerArgument(eq(node), eq(member), eq(random)))
            .thenReturn(expected);

        IntegerArgument actual = trigger.get();
        assertSame(expected, actual);
    }

    protected void verifyDoubleDefault(String member, Double expected, Runnable trigger) {
        when(load.aDouble(eq(node), eq(member), any(), any())).thenReturn(expected + 1.0);
        ArgumentCaptor<Supplier> captor = ArgumentCaptor.forClass(Supplier.class);
        trigger.run();
        verify(load).aDouble(any(), any(), eq(random), captor.capture());
        Double actual = (Double) captor.getValue().get();
        assertEquals(expected, actual, epsilon);
    }

    protected void verifyDouble(String member, Supplier<Double> trigger) {
        double expected = 7.0;

        // For values with defaults
        when(load.aDouble(eq(node), eq(member), eq(random), any())).thenReturn(expected);

        // For values without defaults
        when(load.aDouble(eq(node), eq(member), eq(random))).thenReturn(expected);

        double actual = trigger.get();
        assertEquals(expected, actual, epsilon);
    }

    protected void verifyDoubleArgumentDefault(String member, DoubleArgument expected, Runnable trigger) {
        DoubleArgument notExpected = mock(DoubleArgument.class);
        when(load.aDoubleArgument(eq(node), eq(member), any(), any()))
            .thenReturn(notExpected);
        ArgumentCaptor<Supplier> captor = ArgumentCaptor.forClass(Supplier.class);
        trigger.run();
        verify(load).aDoubleArgument(any(), any(), eq(random), captor.capture());
        DoubleArgument actual = (DoubleArgument) captor.getValue().get();
        assertSame(expected, actual);
    }

    protected void verifyDoubleArgument(String member, Supplier<DoubleArgument> trigger) {
        DoubleArgument expected = mock(DoubleArgument.class);

        // For values with defaults
        when(load.aDoubleArgument(eq(node), eq(member), eq(random), any()))
            .thenReturn(expected);

        // For values without defaults
        when(load.aDoubleArgument(eq(node), eq(member), eq(random)))
            .thenReturn(expected);

        DoubleArgument actual = trigger.get();
        assertSame(expected, actual);
    }
}
