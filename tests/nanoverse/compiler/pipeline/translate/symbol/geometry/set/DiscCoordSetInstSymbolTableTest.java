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

package nanoverse.compiler.pipeline.translate.symbol.geometry.set;

import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.tables.MapSymbolTableTest;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.set.DiscSet;
import org.junit.Test;

public class DiscCoordSetInstSymbolTableTest extends MapSymbolTableTest {


    @Override
    protected MapSymbolTable getQuery() {
        return new DiscCoordSetInstSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return DiscSet.class;
    }

    @Test
    public void origin() throws Exception {
        verifyReturnSymbol("origin", Coordinate.class);
    }

    @Test
    public void radius() throws Exception {
        verifyReturnSymbol("radius", IntegerArgument.class);
    }
}