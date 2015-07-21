/*
 * Copyright (c) 2015 David Bruce Borenstein and the Trustees
 * of Princeton University. All rights reserved.
 */

package compiler.symbol.tables;

/**
 * Created by dbborens on 3/4/15.
 */
public class ListSymbolTable implements InstantiableSymbolTable,  ResolvingSymbolTable {

    private final ClassSymbolTable classSymbolTable;

    public ListSymbolTable (ClassSymbolTable classSymbolTable) {
        this.classSymbolTable = classSymbolTable;
    }

    @Override
    public InstantiableSymbolTable getSymbolTable(String identifier) {
        return classSymbolTable.getSymbolTable(identifier);
    }

    public Class getMemberClass() {
        return classSymbolTable.getBroadClass();
    }
}
