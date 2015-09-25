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
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.tables.*;
import org.junit.*;

public class ActionClassSymbolTableTest extends ClassSymbolTableTest {

    @Override
    protected ClassSymbolTable getQuery() {
        return new ActionClassSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return ActionDescriptor.class;
    }


    @Test
    public void nullAction() throws Exception {
        verifyReturnSymbol("Null", NullActionDescriptor.class);
    }

    @Test
    public void thresholdDo() throws Exception {
        verifyReturnSymbol("ThresholdDo", ThresholdDoDescriptor.class);
    }

    @Test
    public void inject() throws Exception {
        verifyReturnSymbol("Inject", InjectDescriptor.class);
    }

    @Test
    public void swap() throws Exception {
        verifyReturnSymbol("Swap", SwapDescriptor.class);
    }

    @Test
    public void stochasticChoice() throws Exception {
        verifyReturnSymbol("StochasticChoice", StochasticChoiceDescriptor.class);
    }

    @Test
    public void expandWeighted() throws Exception {
        verifyReturnSymbol("ExpandWeighted", ExpandWeightedDescriptor.class);
    }

    @Test
    public void expandRandom() throws Exception {
        verifyReturnSymbol("ExpandRandom", ExpandRandomDescriptor.class);
    }

    @Test
    public void expandTo() throws Exception {
        verifyReturnSymbol("ExpandTo", ExpandToDescriptor.class);
    }

    @Test
    public void expand() throws Exception {
        verifyReturnSymbol("Expand", ExpandDescriptor.class);
    }

    @Test
    public void cloneTo() throws Exception {
        verifyReturnSymbol("CloneTo", CloneToDescriptor.class);
    }

    @Test
    public void trigger() throws Exception {
        verifyReturnSymbol("Trigger", TriggerDescriptor.class);
    }

    @Test
    public void adjustHealth() throws Exception {
        verifyReturnSymbol("AdjustHealth", AdjustHealthDescriptor.class);
    }

    @Test
    public void die() throws Exception {
        verifyReturnSymbol("Die", DieDescriptor.class);
    }

    @Test
    public void mock() throws Exception {
        verifyReturnSymbol("Mock", MockActionDescriptor.class);
    }
}