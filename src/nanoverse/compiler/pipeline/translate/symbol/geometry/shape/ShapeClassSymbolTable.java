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

package nanoverse.compiler.pipeline.translate.symbol.geometry.shape;

import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.runtime.geometry.shape.Shape;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 7/25/2015.
 */
public class ShapeClassSymbolTable extends ClassSymbolTable<Shape> {
    @Override
    public String getDescription() {
        return "Shape objects represent the shape of the boundary that " +
            "defines the spatial environment of the simulation.";
    }

    @Override
    public HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        HashMap<String, Supplier<InstantiableSymbolTable>> ret = new HashMap<>();
        line(ret);
        rectangle(ret);
        hexagon(ret);
        cuboid(ret);
        return ret;
    }

    private void line(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = LineInstSymbolTable::new;
        ret.put("Line", supplier);
    }

    private void rectangle(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = RectangleInstSymbolTable::new;
        ret.put("Rectangle", supplier);
    }

    private void hexagon(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = HexagonInstSymbolTable::new;
        ret.put("Hexagon", supplier);
    }

    private void cuboid(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = CuboidInstSymbolTable::new;
        ret.put("Cuboid", supplier);
    }
}
