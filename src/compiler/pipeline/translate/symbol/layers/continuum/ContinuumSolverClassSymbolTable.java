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

package compiler.pipeline.translate.symbol.layers.continuum;

import compiler.pipeline.translate.symbol.*;
import layers.continuum.solvers.ContinuumSolver;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 7/28/2015.
 */
public class ContinuumSolverClassSymbolTable extends ClassSymbolTable<ContinuumSolver> {

    @Override
    public String getDescription() {
        return "A continuum solver specifies how to transform the continuum " +
                "state using matrix operations.";
    }

    @Override
    protected HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        HashMap<String, Supplier<InstantiableSymbolTable>> ret = new HashMap<>();
        equilibrium(ret);
        nonEquilibrium(ret);
        return ret;
    }

    private void equilibrium(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = EquilibriumSolverInstSymbolTable::new;
        ret.put("Equilibrium", supplier);
    }

    private void nonEquilibrium(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = NonEquilibriumSolverInstSymbolTable::new;
        ret.put("NonEquilibrium", supplier);
    }
}
