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

package compiler.pipeline.translate.symbol.agent.action;

import agent.action.*;
import compiler.pipeline.translate.symbol.*;
import compiler.pipeline.translate.symbol.tables.*;
import org.junit.*;

import static org.junit.Assert.*;

public class ActionClassSymbolTableTest extends ClassSymbolTableTest {

    @Override
    protected ClassSymbolTable getQuery() {
        return new ActionClassSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return ActionDescriptor.class;
    }

    private void verifyActionClass(String identifier, Class expected) throws Exception {
        InstantiableSymbolTable ist = query.getSymbolTable(identifier);
        Class actual = ((ActionInstSymbolTable) ist).getActionClass();
        assertEquals(expected, actual);
    }

    @Test
    public void nullAction() throws Exception {
        verifyActionClass("Null", NullAction.class);
    }

    @Test
    public void thresholdDo() throws Exception {
        verifyActionClass("ThresholdDo", ThresholdDo.class);
    }

    @Test
    public void inject() throws Exception {
        verifyActionClass("Inject", Inject.class);
    }

    @Test
    public void swap() throws Exception {
        verifyActionClass("Swap", Swap.class);
    }

    @Test
    public void stochasticChoice() throws Exception {
        verifyActionClass("StochasticChoice", StochasticChoice.class);
    }

    @Test
    public void expandWeighted() throws Exception {
        verifyActionClass("ExpandWeighted", ExpandWeighted.class);
    }

    @Test
    public void expandRandom() throws Exception {
        verifyActionClass("ExpandRandom", ExpandRandom.class);
    }

    @Test
    public void expandTo() throws Exception {
        verifyActionClass("ExpandTo", ExpandTo.class);
    }

    @Test
    public void expand() throws Exception {
        verifyActionClass("Expand", Expand.class);
    }

    @Test
    public void cloneTo() throws Exception {
        verifyActionClass("CloneTo", CloneTo.class);
    }

    @Test
    public void trigger() throws Exception {
        verifyActionClass("Trigger", Trigger.class);
    }

    @Test
    public void adjustHealth() throws Exception {
        verifyActionClass("AdjustHealth", AdjustHealth.class);
    }

    @Test
    public void die() throws Exception {
        verifyActionClass("Die", Die.class);
    }

    @Test
    public void mock() throws Exception {
        verifyActionClass("Mock", MockAction.class);
    }
}