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

package compiler.pipeline.translate.symbol.agent.targets;

import compiler.pipeline.translate.symbol.*;
import control.arguments.TargetDescriptor;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 7/23/2015.
 */
public class TargetRuleClassSymbolTable extends ClassSymbolTable<TargetDescriptor> {

    @Override
    public String getDescription() {
        return "Target rules define which locations are valid target for a " +
                "particular transitive action.";
    }
    @Override
    protected HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        HashMap<String, Supplier<InstantiableSymbolTable>> ret = new HashMap<>();
        allNeighbors(ret);
        occupiedNeighbors(ret);
        vacantNeighbors(ret);
        caller(ret);
        self(ret);
        return ret;
    }

    private void self(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = TargetSelfInstSymbolTable::new;
        ret.put("Self", supplier);
    }

    private void caller(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = TargetCallerInstSymbolTable::new;
        ret.put("Caller", supplier);
    }

    private void vacantNeighbors(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = TargetVacantNeighborsInstSymbolTable::new;
        ret.put("VacantNeighbors", supplier);
    }

    private void occupiedNeighbors(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = TargetOccupiedNeighborsInstSymbolTable::new;
        ret.put("OccupiedNeighbors", supplier);
    }

    private void allNeighbors(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = TargetAllNeighborsInstSymbolTable::new;
        ret.put("AllNeighbors", supplier);
    }
}
