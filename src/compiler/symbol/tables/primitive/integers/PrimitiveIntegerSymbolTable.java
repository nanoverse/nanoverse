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

import compiler.pipeline.interpret.nodes.ASTPrimitiveNode;
import compiler.pipeline.translate.nodes.*;
import compiler.symbol.tables.primitive.PrimitiveSymbolTable;
import control.arguments.ConstantInteger;

/**
 * Created by dbborens on 3/5/15.
 */
public class PrimitiveIntegerSymbolTable extends PrimitiveSymbolTable<Integer> implements IntegerInstanceSymbolTable {

    @Override
    public String getDescription() {
        return "A constant integer.";
    }

    @Override
    public PrimitiveObjectNode<Integer> getObjectNode(ASTPrimitiveNode<Integer> astNode) {
        return new PrimitiveIntegerNode(this, astNode.getContent());
    }

    @Override
    public Class getInstanceClass() {
        return ConstantInteger.class;
    }
}
