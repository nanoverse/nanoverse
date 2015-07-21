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

package compiler.pipeline.translate.visitors;

import compiler.pipeline.interpret.nodes.ASTPrimitiveNode;
import compiler.pipeline.translate.nodes.ObjectNode;
import compiler.symbol.tables.runtime.primitive.PrimitiveSymbolTable;
import compiler.symbol.tables.runtime.primitive.doubles.PrimitiveDoubleSymbolTable;
import compiler.symbol.tables.runtime.primitive.integers.PrimitiveIntegerSymbolTable;
import org.slf4j.*;

/**
 * Takes a primitive AST node and a primitive symbol table,
 * and returns a primitive object node.
 *
 * Created by dbborens on 4/22/15.
 */
public class PrimitiveVisitor {
    private final Logger logger;

    public PrimitiveVisitor() {
        logger = LoggerFactory.getLogger(PrimitiveVisitor.class);
    }

    public ObjectNode translate(ASTPrimitiveNode toTranslate, PrimitiveSymbolTable symbolTable) {
        logger.debug("Translating primitive {}", toTranslate.getContent());
        if (symbolTable instanceof PrimitiveIntegerSymbolTable) {
            return ((PrimitiveIntegerSymbolTable) symbolTable).getObjectNode(toTranslate);
        } else if (symbolTable instanceof PrimitiveDoubleSymbolTable) {
            return ((PrimitiveDoubleSymbolTable) symbolTable).getObjectNode(toTranslate);
        } else {
            throw new IllegalStateException("Unrecognized primitive");
        }
    }
}
