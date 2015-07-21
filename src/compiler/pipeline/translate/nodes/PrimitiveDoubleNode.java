/*
 * Copyright (c) 2015 David Bruce Borenstein and the Trustees
 * of Princeton University. All rights reserved.
 */

package compiler.pipeline.translate.nodes;

import compiler.symbol.tables.runtime.primitive.PrimitiveSymbolTable;

/**
 * Created by dbborens on 4/26/15.
 */
public class PrimitiveDoubleNode extends PrimitiveObjectNode<Double> {

    public PrimitiveDoubleNode(PrimitiveSymbolTable<Double> symbolTable, Double value) {
        super(symbolTable, value);
    }

}
