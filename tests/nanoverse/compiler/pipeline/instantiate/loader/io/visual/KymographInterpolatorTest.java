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

package nanoverse.compiler.pipeline.instantiate.loader.io.visual;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.visual.VisualizationProperties;
import org.junit.*;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class KymographInterpolatorTest extends InterpolatorTest {

    private KymographDefaults defaults;
    private VisualizationPropertiesLoader vpLoader;
    private GeneralParameters p;
    private KymographInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(KymographDefaults.class);
        vpLoader = mock(VisualizationPropertiesLoader.class);
        p = mock(GeneralParameters.class);
        query = new KymographInterpolator(defaults, vpLoader);
    }

    @Test
    public void properties() throws Exception {
        VisualizationProperties expected = mock(VisualizationProperties.class);
        when(vpLoader.instantiate(node, p)).thenReturn(expected);
        VisualizationProperties actual = query.properties(node, p);
        assertSame(expected, actual);
    }

    @Test
    public void propertiesDefault() throws Exception {
        VisualizationProperties expected = mock(VisualizationProperties.class);
        when(defaults.properties(p)).thenReturn(expected);
        VisualizationProperties actual = query.properties(null, p);
        assertSame(expected, actual);
    }
}