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

package nanoverse.compiler.pipeline.translate.symbol.primitive.doubles;

import nanoverse.compiler.pipeline.interpret.visitors.NanoPrimitiveDoubleVisitor;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.runtime.control.arguments.DoubleArgument;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 3/18/15.
 */
public class DoubleClassSymbolTable extends ClassSymbolTable<DoubleArgument> {


    @Override
    public String getDescription() {
        return "Functions that return floating point (FP) values. All " +
            "floating point values in Nanoverse are double-precision.";
    }

    @Override
    protected HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        HashMap<String, Supplier<InstantiableSymbolTable>> ret = new HashMap<>(1);
        primitive(ret);
        return ret;
    }

    private void primitive(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = ConstantDoubleInstSymbolTable::new;
        ret.put(NanoPrimitiveDoubleVisitor.IDENTIFIER, supplier);
    }
}
