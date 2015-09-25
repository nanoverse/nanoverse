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

package nanoverse.compiler.pipeline.translate.symbol;

import com.google.common.reflect.TypeToken;
import nanoverse.compiler.pipeline.instantiate.loader.Loader;

import java.util.function.Supplier;

/**
 * Represents an unstructured mapping of keys to values. Used for user-
 * defined members and variables.
 * <p>
 * Created by dbborens on 7/23/2015.
 */
public class DictionarySymbolTable<T> implements InstantiableSymbolTable, ResolvingSymbolTable {
    private final TypeToken<T> type = new TypeToken<T>(getClass()) {
    };

    private final ClassSymbolTable classSymbolTable;
    private final Supplier<Loader<T>> loaderSupplier;

    public DictionarySymbolTable(ClassSymbolTable classSymbolTable,
                                 Supplier<Loader<T>> loaderSupplier) {

        this.classSymbolTable = classSymbolTable;
        this.loaderSupplier = loaderSupplier;
    }

    @Override
    public Class getInstanceClass() {
        return type.getRawType();
    }

    @Override
    public Loader<T> getLoader() {
        return loaderSupplier.get();
    }

    @Override
    public InstantiableSymbolTable getSymbolTable(String identifier) {
        return classSymbolTable.getSymbolTable(identifier);
    }

    @Override
    public Class getBroadClass() {
        return classSymbolTable.getBroadClass();
    }

    @Override
    public String getDescription() {
        return "A dictionary is an unstructured mapping of keys to values, " +
            "used to define object members (variables, actions, etc).";
    }
}
