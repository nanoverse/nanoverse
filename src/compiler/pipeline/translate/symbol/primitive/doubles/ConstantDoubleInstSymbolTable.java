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

package compiler.pipeline.translate.symbol.primitive.doubles;

import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.instantiate.loader.primitive.doubles.ConstantDoubleLoader;
import compiler.pipeline.translate.nodes.*;
import compiler.pipeline.translate.symbol.primitive.ConstantPrimitiveSymbolTable;
import control.arguments.*;

/**
 * Created by dbborens on 3/5/15.
 */
public class ConstantDoubleInstSymbolTable extends ConstantPrimitiveSymbolTable<DoubleArgument, Double> {

    @Override
    public String getDescription() {
        return "A constant Double.";
    }

    @Override
    public Double getValue(String valueStr) {
        Double value = Double.valueOf(valueStr);
        return value;
    }

    @Override
    public Loader getLoader() {
        return new ConstantDoubleLoader();
    }
}
