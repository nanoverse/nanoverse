/*
 * Copyright (c) 2015 David Bruce Borenstein and the Trustees
 * of Princeton University. All rights reserved.
 */

package nanoverse.compiler.pipeline.translate.nodes;

import nanoverse.compiler.pipeline.translate.symbol.InstantiableSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.ConstantPrimitiveSymbolTable;

/**
 * Created by dbborens on 4/26/15.
 */
public class PrimitiveObjectNode<T> implements ObjectNode {

    private final ConstantPrimitiveSymbolTable symbolTable;
    private final T value;

    public PrimitiveObjectNode(ConstantPrimitiveSymbolTable symbolTable, T value) {
        this.symbolTable = symbolTable;
        this.value = value;
    }

    @Override
    public InstantiableSymbolTable getSymbolTable() {
        return symbolTable;
    }

    public T getValue() {
        return value;
    }

    @Override
    public Class getInstantiatingClass() {
        return symbolTable.getInstanceClass();
    }
}
