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

package compiler.symbol.tables.agent;

import compiler.symbol.symbols.MemberSymbol;
import compiler.symbol.tables.*;
import control.arguments.CellDescriptor;

import java.util.HashMap;

/**
 * Created by dbborens on 7/22/2015.
 */
public class AgentDescriptorInstSymbolTable extends MapSymbolTable<CellDescriptor> {
    @Override
    public String getDescription() {
        return "AgentDescriptor describes the properties of a class of agents," +
                " such as behaviors and internal state.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        cellState(ret);
        threshold(ret);
        initialHealth(ret);
        reactions(ret);
        behaviors(ret);
        return ret;
    }

    private void behaviors(HashMap<String, MemberSymbol> ret) {

    }

    private void reactions(HashMap<String, MemberSymbol> ret) {

    }

    private void initialHealth(HashMap<String, MemberSymbol> ret) {

    }

    private void threshold(HashMap<String, MemberSymbol> ret) {

    }

    private void cellState(HashMap<String, MemberSymbol> ret) {

    }

}