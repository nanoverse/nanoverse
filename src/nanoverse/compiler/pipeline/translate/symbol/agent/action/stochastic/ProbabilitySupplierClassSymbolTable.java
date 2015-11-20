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

import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.runtime.control.arguments.ProbabilitySupplierDescriptor;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 8/25/2015.
 */
public class ProbabilitySupplierClassSymbolTable extends ClassSymbolTable<ProbabilitySupplierDescriptor> {

    @Override
    public HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        HashMap<String, Supplier<InstantiableSymbolTable>> ret = new HashMap<>();
        constant(ret);
        continuum(ret);
        neighborhood(ret);
        return ret;
    }

    private void continuum(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier =
            ContinuumProbabilitySupplierInstSymbolTable::new;

        ret.put("Continuum", supplier);
    }

    private void neighborhood(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier =
                NeighborhoodProbabilitySupplierInstSymbolTable::new;

        ret.put("Neighborhood", supplier);
    }

    private void constant(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier =
            ConstantProbabilitySupplierInstSymbolTable::new;

        ret.put("Constant", supplier);
    }

    @Override
    public String getDescription() {
        return "Represents the probability that a particular stochastic " +
            "option will be chosen. Soon to be replaced with an alternative " +
            "approach based on waiting time.";
    }
}
