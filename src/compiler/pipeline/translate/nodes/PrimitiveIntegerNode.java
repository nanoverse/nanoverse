/*
 * Copyright (c) 2015 David Bruce Borenstein and the Trustees
 * of Princeton University. All rights reserved.
 */

package compiler.pipeline.translate.nodes;


import compiler.pipeline.translate.symbol.primitive.PrimitiveSymbolTable;

/**
 * Created by dbborens on 4/26/15.
 */
public class PrimitiveIntegerNode extends PrimitiveObjectNode<Integer> {
    public PrimitiveIntegerNode(PrimitiveSymbolTable<Integer> symbolTable, Integer value) {
        super(symbolTable, value);
    }
}
