/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
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
