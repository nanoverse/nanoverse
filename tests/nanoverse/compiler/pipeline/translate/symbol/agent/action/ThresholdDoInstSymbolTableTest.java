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

import nanoverse.runtime.agent.action.*;
import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.runtime.control.arguments.*;
import org.junit.*;

public class ThresholdDoInstSymbolTableTest extends ActionInstSymbolTableTest {

    @Override
    protected MapSymbolTable getQuery() {
        return new ThresholdDoInstSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return ThresholdDoDescriptor.class;
    }

    @Test
    public void action() throws Exception {
        verifyReturnSymbol("action", ActionDescriptor.class);
    }

    @Test
    public void layer() throws Exception {
        verifyReturnSymbol("layer", StringArgument.class);
    }

    @Test
    public void maximum() throws Exception {
        verifyReturnSymbol("maximum", DoubleArgument.class);
    }

    @Test
    public void minimum() throws Exception {
        verifyReturnSymbol("minimum", DoubleArgument.class);
    }

}