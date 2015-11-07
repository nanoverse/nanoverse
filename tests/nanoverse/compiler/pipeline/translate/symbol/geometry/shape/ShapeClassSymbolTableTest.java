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

package nanoverse.compiler.pipeline.translate.symbol.geometry.shape;

import nanoverse.compiler.pipeline.translate.symbol.ClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.tables.ClassSymbolTableTest;
import nanoverse.runtime.geometry.shape.*;
import org.junit.Test;

public class ShapeClassSymbolTableTest extends ClassSymbolTableTest {

    @Override
    protected ClassSymbolTable getQuery() {
        return new ShapeClassSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return Shape.class;
    }

    @Test
    public void line() throws Exception {
        verifyReturnSymbol("Line", Line.class);
    }

    @Test
    public void rectangle() throws Exception {
        verifyReturnSymbol("Rectangle", Rectangle.class);
    }

    @Test
    public void hexagon() throws Exception {
        verifyReturnSymbol("Hexagon", Hexagon.class);
    }

    @Test
    public void cuboid() throws Exception {
        verifyReturnSymbol("Cuboid", Cuboid.class);
    }
}