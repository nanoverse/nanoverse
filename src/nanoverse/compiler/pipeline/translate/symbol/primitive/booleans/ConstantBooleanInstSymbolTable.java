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

package nanoverse.compiler.pipeline.translate.symbol.primitive.booleans;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.primitive.booleans.ConstantBooleanLoader;
import nanoverse.compiler.pipeline.translate.symbol.primitive.ConstantPrimitiveSymbolTable;
import nanoverse.runtime.control.arguments.*;
import org.slf4j.*;

/**
 * Created by dbborens on 7/22/2015.
 */
public class ConstantBooleanInstSymbolTable extends ConstantPrimitiveSymbolTable<ConstantBoolean, Boolean> {

    private final Logger logger = LoggerFactory.getLogger(ConstantBooleanInstSymbolTable.class);

    @Override
    public String getDescription() {
        return "A constant boolean.";
    }

    @Override
    public Boolean getValue(String valueStr) {
        logger.debug("Attempting to interpret literal \"{}\" as Boolean.", valueStr);
        Boolean value = Boolean.valueOf(valueStr);
        logger.debug("Translated literal \"{}\" as {}", valueStr, value);
        return value;
    }

    @Override
    public Loader getLoader() {
        return new ConstantBooleanLoader();
    }
}
