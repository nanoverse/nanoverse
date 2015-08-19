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

package compiler.pipeline.instantiate.loader.control;

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.instantiate.loader.InterpolatorTest;
import compiler.pipeline.translate.nodes.MapObjectNode;
import compiler.pipeline.translate.nodes.ObjectNode;
import org.junit.*;
import org.mockito.ArgumentCaptor;
import org.mockito.internal.matchers.Null;

import java.beans.ParameterDescriptor;
import java.util.Random;
import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ParametersInterpolatorTest extends InterpolatorTest {

    private LoadHelper load;
    private ParametersDefaults defaults;
    private MapObjectNode node;
    private Random random;
    private ParametersInterpolator query;

    @Before
    public void before() throws Exception {
        random = new Random(1);
        load = mock(LoadHelper.class);
        defaults = mock(ParametersDefaults.class);
        node = mock(MapObjectNode.class);
        query = new ParametersInterpolator(load, defaults);
    }

    @Test
    public void randomSeedStar() throws Exception {
        long minimum = System.currentTimeMillis();
        when(load.aString(eq(node), eq("seed"), any())).thenReturn("*");
        long actual = query.randomSeed(node);
        assertTrue(actual >= minimum);
    }

    @Test
    public void randomSeedNumber() throws Exception {
        when(load.aString(eq(node), eq("seed"), any())).thenReturn("7");
        long actual = query.randomSeed(node);
        assertEquals(7, actual);
    }

    @Test
    public void randomSeedDefault() throws Exception {
        when(load.aString(eq(node), eq("seed"), any())).thenReturn("0");
        when(defaults.randomSeed()).thenReturn("7");
        ArgumentCaptor<Supplier> captor = ArgumentCaptor.forClass(Supplier.class);
        query.randomSeed(node);
        verify(load).aString(any(), any(), captor.capture());
        String actual = (String) captor.getValue().get();
        assertEquals("7", actual);
    }

    @Test
    public void maxStep() throws Exception {
        ObjectNode cNode = configureIntegerValue("maxStep", random, 5, load, node);

    }

    @Test
    public void maxStepDefault() throws Exception {

    }

    @Test
    public void instances() throws Exception {

    }

    @Test
    public void instancesDefault() throws Exception {

    }

    @Test
    public void path() throws Exception {

    }

    @Test
    public void pathDefault() throws Exception {

    }

    @Test
    public void project() throws Exception {

    }

    @Test
    public void projectDefault() throws Exception {

    }

    @Test
    public void date() throws Exception {

    }

    @Test
    public void dateDefault() throws Exception {

    }

    @Test
    public void random() throws Exception {

    }
}