/*
 * Copyright (c) 2015 David Bruce Borenstein and the Trustees
 * of Princeton University. All rights reserved.
 */

package compiler.pipeline.translate.nodes;

import compiler.pipeline.translate.symbol.InstantiableSymbolTable;
import compiler.pipeline.translate.symbol.primitive.PrimitiveSymbolTable;

import java.util.function.Supplier;

/**
 * Created by dbborens on 4/26/15.
 */
public abstract class PrimitiveObjectNode<T> implements ObjectNode {

    private final T value;
    private final PrimitiveSymbolTable<T> symbolTable;

    public PrimitiveObjectNode(PrimitiveSymbolTable<T> symbolTable, T value) {
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
        return Supplier.class;
    }
}
