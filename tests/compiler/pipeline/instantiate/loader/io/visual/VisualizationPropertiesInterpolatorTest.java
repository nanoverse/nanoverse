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

package compiler.pipeline.instantiate.loader.io.visual;

import compiler.pipeline.instantiate.loader.InterpolatorTest;
import org.junit.*;

import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class VisualizationPropertiesInterpolatorTest extends InterpolatorTest {

    private VisualizationPropertiesDefaults defaults;
    private VisualizationPropertiesInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(VisualizationPropertiesDefaults.class);
        query = new VisualizationPropertiesInterpolator(load, defaults);
    }

    @Test
    public void edge() throws Exception {
        Supplier<Integer> trigger = () -> query.edge(node, random);
        verifyInteger("edge", trigger);
    }

    @Test
    public void edgeDefault() throws Exception {
        when(defaults.edge()).thenReturn(7);
        verifyIntegerDefault("edge", 7, () -> query.edge(node, random));
    }

    @Test
    public void outline() throws Exception {
        Supplier<Integer> trigger = () -> query.outline(node, random);
        verifyInteger("outline", trigger);
    }

    @Test
    public void outlineDefault() throws Exception {
        when(defaults.outline()).thenReturn(7);
        verifyIntegerDefault("outline", 7, () -> query.outline(node, random));
    }

    @Test
    public void colorModel() throws Exception {
        fail();

    }

    @Test
    public void colorModelDefault() throws Exception {
        fail();

    }

    @Test
    public void highlights() throws Exception {
        fail();

    }

    @Test
    public void highlightsDefault() throws Exception {
        fail();

    }
}