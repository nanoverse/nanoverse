/*
 * Copyright (c) 2015 David Bruce Borenstein and the Trustees
 * of Princeton University. All rights reserved.
 */

package nanoverse.compiler.pipeline.translate.symbol;

import com.google.common.reflect.TypeToken;
import nanoverse.compiler.pipeline.instantiate.loader.Loader;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by dbborens on 3/4/15.
 */
public class ListSymbolTable<T> implements InstantiableSymbolTable, ResolvingSymbolTable {

    private final TypeToken<T> type = new TypeToken<T>(getClass()) {};
    private final ClassSymbolTable classSymbolTable;
    private final Supplier<Loader> loaderSupplier;

    public ListSymbolTable(ClassSymbolTable classSymbolTable,
                           Supplier<Loader> loaderSupplier) {

        this.classSymbolTable = classSymbolTable;
        this.loaderSupplier = loaderSupplier;
    }

    public Stream getMemberNames(){
        return  classSymbolTable.getMemberNames();
    }

    @Override
    public String getDescription() {
        return "An ordered set of one or more objects with the same parent class.";
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

    @Override
    public Loader getLoader() {
        return loaderSupplier.get();
    }
}
