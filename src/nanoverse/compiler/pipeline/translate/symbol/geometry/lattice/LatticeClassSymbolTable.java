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

package nanoverse.compiler.pipeline.translate.symbol.geometry.lattice;

import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.runtime.geometry.lattice.Lattice;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 7/25/2015.
 */
public class LatticeClassSymbolTable extends ClassSymbolTable<Lattice> {

    @Override
    public String getDescription() {
        return "The lattice represents the topology of an nanoverse.runtime.agent's " +
            "neighborhood. As of this writing, all lattice topologies in " +
            "Nanoverse are regular.";
    }

    @Override
    protected HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        HashMap<String, Supplier<InstantiableSymbolTable>> ret = new HashMap<>();
        linear(ret);
        rectangular(ret);
        triangular(ret);
        cubic(ret);
        return ret;
    }

    private void cubic(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = CubicLatticeInstSymbolTable::new;
        ret.put("Cubic", supplier);
    }

    private void triangular(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = TriangularLatticeInstSymbolTable::new;
        ret.put("Triangular", supplier);
    }

    private void rectangular(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = RectangularLatticeInstSymbolTable::new;
        ret.put("Rectangular", supplier);
    }

    private void linear(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = LinearLatticeInstSymbolTable::new;
        ret.put("Linear", supplier);
    }
}
