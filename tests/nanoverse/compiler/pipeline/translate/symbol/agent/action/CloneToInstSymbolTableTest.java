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

package nanoverse.compiler.pipeline.translate.symbol.agent.action;

import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.runtime.agent.action.CloneToDescriptor;
import nanoverse.runtime.agent.targets.TargetDescriptor;
import nanoverse.runtime.control.arguments.*;
import org.junit.Test;

public class CloneToInstSymbolTableTest extends ActionInstSymbolTableTest {

    @Override
    protected MapSymbolTable getQuery() {
        return new CloneToInstSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return CloneToDescriptor.class;
    }

    @Test
    public void targetHighlight() throws Exception {
        verifyReturnSymbol("targetHighlight", IntegerArgument.class);
    }

    @Test
    public void selfHighlight() throws Exception {
        verifyReturnSymbol("selfHighlight", IntegerArgument.class);
    }

    @Test
    public void target() throws Exception {
        verifyReturnSymbol("target", TargetDescriptor.class);
    }

    @Test
    public void noReplacement() throws Exception {
        verifyReturnSymbol("noReplacement", BooleanArgument.class);
    }
}