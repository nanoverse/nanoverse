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

package compiler.symbol.tables.processes.discrete;

import compiler.symbol.symbols.MemberSymbol;
import processes.discrete.OccupiedNeighborSwap;

import java.util.HashMap;

/**
 * Created by dbborens on 7/21/2015.
 */
public class OccupiedNeighborSwapInstSymbolTable extends DiscreteProcessInstSymbolTable<OccupiedNeighborSwap> {
    @Override
    public String getDescription() {
        return "Swap the specified agent with one of its occupied neighbors.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        return super.resolveMembers();
    }

}
