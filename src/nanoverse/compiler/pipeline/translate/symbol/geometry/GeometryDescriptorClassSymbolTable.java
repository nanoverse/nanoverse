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

package nanoverse.compiler.pipeline.translate.symbol.geometry;

import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.runtime.control.arguments.GeometryDescriptor;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 7/21/2015.
 */
public class GeometryDescriptorClassSymbolTable extends ClassSymbolTable<GeometryDescriptor> {
    @Override
    public String getDescription() {
        return "The top-level geometric properties of the simulation. " +
            "Properties specified here will carry over to all nanoverse.runtime.layers of " +
            "the simulation.";
    }

    @Override
    protected HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        HashMap<String, Supplier<InstantiableSymbolTable>> ret = new HashMap<>();
        geometry(ret);
        return ret;
    }

    private void geometry(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = GeometryDescriptorInstanceSymbolTable::new;
        ret.put("Geometry", supplier);
    }
}
