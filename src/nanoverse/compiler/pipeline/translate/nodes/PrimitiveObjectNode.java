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

package nanoverse.compiler.pipeline.translate.nodes;

import nanoverse.compiler.pipeline.translate.symbol.InstantiableSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.ConstantPrimitiveSymbolTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dbborens on 4/26/15.
 */
public class PrimitiveObjectNode<T> implements ObjectNode {
    private final Logger logger;

    private final ConstantPrimitiveSymbolTable symbolTable;
    private final T value;
    private final int lineNumber;

    public PrimitiveObjectNode(ConstantPrimitiveSymbolTable symbolTable, T value, int lineNumber) {
        logger = LoggerFactory.getLogger(PrimitiveObjectNode.class);

        this.symbolTable = symbolTable;
        this.value = value;
        this.lineNumber = lineNumber;

        logger.debug("Primitive object {} on line number {}", value, lineNumber);
    }

    public T getValue() {
        return value;
    }

    public int getLineNumber() { return lineNumber; }

    @Override
    public Class getInstantiatingClass() {
        return symbolTable.getInstanceClass();
    }

    @Override
    public InstantiableSymbolTable getSymbolTable() {
        return symbolTable;
    }
}
