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

import compiler.pipeline.instantiate.loader.InterpolatorTest;
import org.junit.*;

import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ParametersInterpolatorTest extends InterpolatorTest {

    private ParametersDefaults defaults;
    private ParametersInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(ParametersDefaults.class);
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
        when(defaults.randomSeed()).thenReturn("7");
        verifyStringDefault("seed", "7", () -> query.randomSeed(node));
    }

    @Test
    public void maxStep() throws Exception {
        when(load.anInteger(eq(node), eq("maxStep"), any(), any())).thenReturn(5);
        int actual = query.maxStep(node, random);
        assertEquals(5, actual);
    }

    @Test
    public void maxStepDefault() throws Exception {
        when(defaults.maxStep()).thenReturn(3);
        verifyIntegerDefault("maxStep", 3,
                () -> query.maxStep(node, random));
    }

    @Test
    public void instances() throws Exception {
        when(load.anInteger(eq(node), eq("instances"), any(), any()))
                .thenReturn(5);
        int actual = query.instances(node, random);
        assertEquals(5, actual);
    }

    @Test
    public void instancesDefault() throws Exception {
        when(defaults.instances()).thenReturn(3);
        verifyIntegerDefault("instances", 3,
                () -> query.instances(node, random));
    }

    @Test
    public void path() throws Exception {
        String expected = "/my/path";
        when(load.aString(eq(node), eq("path"), any())).thenReturn(expected);
        String actual = query.path(node);
        assertEquals(expected, actual);
    }

    @Test
    public void pathDefault() throws Exception {
        String expected = "/my/path";
        when(defaults.path()).thenReturn(expected);
        verifyStringDefault("path", expected, () -> query.path(node));
    }

    @Test
    public void project() throws Exception {
        String expected = "myProject";
        when(load.aString(eq(node), eq("project"), any()))
                .thenReturn(expected);
        String actual = query.project(node);
        assertEquals(expected, actual);
    }

    @Test
    public void projectDefault() throws Exception {
        String expected = "myProject";
        when(defaults.project()).thenReturn(expected);
        verifyStringDefault("project", expected, () -> query.project(node));
    }

    @Test
    public void date() throws Exception {
        Boolean expected = true;
        when(load.aBoolean(eq(node), eq("date"), any(), any()))
                .thenReturn(expected);
        Boolean actual = query.date(node, random);
        assertEquals(expected, actual);
    }

    @Test
    public void dateDefault() throws Exception {
        Boolean expected = true;
        when(defaults.date()).thenReturn(expected);
        verifyBooleanDefault("date", expected, () -> query.date(node, random));
    }

    @Test
    public void random() throws Exception {
        Random expected = new Random(1);
        Random actual = query.random(1);

        for (int i = 0; i < 20; i++) {
            int p = expected.nextInt();
            int q = actual.nextInt();
            assertEquals(p, q);
        }
    }
}