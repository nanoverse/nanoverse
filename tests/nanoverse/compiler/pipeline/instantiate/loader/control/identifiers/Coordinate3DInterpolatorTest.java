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

package nanoverse.compiler.pipeline.instantiate.loader.control.identifiers;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import org.junit.*;

import java.util.function.Supplier;

import static org.mockito.Mockito.*;

public class Coordinate3DInterpolatorTest extends InterpolatorTest {

    private Coordinate3DDefaults defaults;
    private Coordinate3DInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(Coordinate3DDefaults.class);
        query = new Coordinate3DInterpolator(load, defaults);
    }

    @Test
    public void x() throws Exception {
        Supplier<Integer> trigger = () -> query.x(node, random);
        verifyInteger("x", trigger);
    }

    @Test
    public void y() throws Exception {
        Supplier<Integer> trigger = () -> query.y(node, random);
        verifyInteger("y", trigger);

    }

    @Test
    public void z() throws Exception {
        Supplier<Integer> trigger = () -> query.z(node, random);
        verifyInteger("z", trigger);
    }

    @Test
    public void flags() throws Exception {
        Supplier<Integer> trigger = () -> query.flags(node, random);
        verifyInteger("flags", trigger);
    }

    @Test
    public void flagsDefault() throws Exception {
        int expected = 7;
        when(defaults.flags()).thenReturn(expected);
        Runnable trigger = () -> query.flags(node, random);
        verifyIntegerDefault("flags", expected, trigger);
    }
}