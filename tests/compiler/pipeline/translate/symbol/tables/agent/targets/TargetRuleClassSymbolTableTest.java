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

package compiler.pipeline.translate.symbol.tables.agent.targets;

import agent.targets.*;
import compiler.pipeline.translate.symbol.tables.*;
import control.arguments.TargetDescriptor;
import org.junit.*;

import static org.junit.Assert.*;

public class TargetRuleClassSymbolTableTest extends ClassSymbolTableTest {

    @Override
    protected ClassSymbolTable getQuery() {
        return new TargetRuleClassSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return TargetDescriptor.class;
    }

    private void verifyTargetRuleClass(String identifier, Class expected) throws Exception {
        InstantiableSymbolTable ist = query.getSymbolTable(identifier);
        Class actual = ((TargetRuleInstSymbolTable) ist).getTargetRuleClass();
        assertEquals(expected, actual);
    }

    @Test
    public void vacantNeighbors() throws Exception {
        verifyTargetRuleClass("VacantNeighbors", TargetVacantNeighbors.class);
    }

    @Test
    public void occupiedNeighbors() throws Exception {
        verifyTargetRuleClass("OccupiedNeighbors", TargetOccupiedNeighbors.class);
    }

    @Test
    public void allNeighbors() throws Exception {
        verifyTargetRuleClass("AllNeighbors", TargetAllNeighbors.class);
    }

    @Test
    public void self() throws Exception {
        verifyTargetRuleClass("Self", TargetSelf.class);
    }

    @Test
    public void caller() throws Exception {
        verifyTargetRuleClass("Caller", TargetCaller.class);
    }

}