/*
 * Copyright (c) 2015 David Bruce Borenstein and the Trustees
 * of Princeton University. All rights reserved.
 */

package compiler.pipeline.translate.symbol;

import com.google.common.reflect.TypeToken;

/**
 * Created by dbborens on 3/4/15.
 */
public class ListSymbolTable<T> implements InstantiableSymbolTable,  ResolvingSymbolTable {

    @Override
    public String getDescription() {
        return "An ordered set of one or more objects with the same parent class.";
    }

    private final TypeToken<T> type = new TypeToken<T>(getClass()) {};

    private final ClassSymbolTable classSymbolTable;

    public ListSymbolTable (ClassSymbolTable classSymbolTable) {
        this.classSymbolTable = classSymbolTable;
    }

    @Override
    public InstantiableSymbolTable getSymbolTable(String identifier) {
        return classSymbolTable.getSymbolTable(identifier);
    }

    @Override
    public Class getBroadClass() {
        return classSymbolTable.getBroadClass();
    }

    /**
     * Returns the instance class of the list's container.
     *
     * @return
     */
    @Override
    public Class getInstanceClass() {
        return type.getRawType();
    }
}
