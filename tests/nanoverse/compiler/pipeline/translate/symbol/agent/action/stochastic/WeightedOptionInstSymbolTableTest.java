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

package nanoverse.compiler.pipeline.translate.symbol.agent.action.stochastic;

import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.tables.MapSymbolTableTest;
import nanoverse.runtime.agent.action.ActionDescriptor;
import nanoverse.runtime.agent.action.stochastic.WeightedOption;
import nanoverse.runtime.control.arguments.ProbabilitySupplierDescriptor;
import org.junit.Test;

public class WeightedOptionInstSymbolTableTest extends MapSymbolTableTest {

    @Override
    protected MapSymbolTable getQuery() {
        return new WeightedOptionInstSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return WeightedOption.class;
    }

    @Test
    public void action() throws Exception {
        verifyReturnSymbol("action", ActionDescriptor.class);
    }

    @Test
    public void weight() throws Exception {
        verifyReturnSymbol("weight", ProbabilitySupplierDescriptor.class);
    }
}