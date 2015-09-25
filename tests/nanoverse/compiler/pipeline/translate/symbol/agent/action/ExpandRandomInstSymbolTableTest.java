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

package nanoverse.compiler.pipeline.translate.symbol.agent.action;

import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.runtime.agent.action.ExpandRandomDescriptor;
import nanoverse.runtime.control.arguments.IntegerArgument;
import org.junit.Test;

public class ExpandRandomInstSymbolTableTest extends ActionInstSymbolTableTest {

    @Override
    protected MapSymbolTable getQuery() {
        return new ExpandRandomInstSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return ExpandRandomDescriptor.class;
    }

    @Test
    public void targetHighlight() throws Exception {
        verifyReturnSymbol("targetHighlight", IntegerArgument.class);
    }

    @Test
    public void selfHighlight() throws Exception {
        verifyReturnSymbol("selfHighlight", IntegerArgument.class);
    }

}