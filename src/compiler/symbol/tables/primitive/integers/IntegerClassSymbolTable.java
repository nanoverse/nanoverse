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

package compiler.symbol.tables.primitive.integers;

import compiler.pipeline.interpret.nodes.ASTPrimitiveInteger;
import compiler.symbol.tables.*;
import control.arguments.Argument;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 3/18/15.
 */
public class IntegerClassSymbolTable extends ClassSymbolTable<Argument<Integer>> {

    @Override
    public String getDescription() {
        return "Functions that return integer values.";
    }

    @Override
    protected HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        HashMap<String, Supplier<InstantiableSymbolTable>> ret = new HashMap<>(1);
        primitive(ret);
        return ret;
    }

    private void primitive(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = PrimitiveIntegerSymbolTable::new;
        ret.put(ASTPrimitiveInteger.IDENTIFIER, supplier);
    }
}
