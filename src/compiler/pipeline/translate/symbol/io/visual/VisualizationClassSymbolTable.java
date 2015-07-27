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

package compiler.pipeline.translate.symbol.io.visual;

import compiler.pipeline.translate.symbol.*;
import io.visual.Visualization;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 7/27/2015.
 */
public class VisualizationClassSymbolTable extends ClassSymbolTable<Visualization> {

    @Override
    public String getDescription() {
        return "A visualization is a graphical representation of some aspect " +
                "of the state of the simulation. Visualizations can capture " +
                "the state at multiple times per simulation, often creating " +
                "multiple images in the process.";
    }

    @Override
    protected HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        HashMap<String, Supplier<InstantiableSymbolTable>> ret = new HashMap<>();
        map(ret);
        kymograph(ret);
        mock(ret);
        return ret;
    }

    private void map(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier supplier = MapVisualizationInstSymbolTable::new;
        ret.put("Map", supplier);
    }

    private void kymograph(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier supplier = KymographInstSymbolTable::new;
        ret.put("Kymograph", supplier);
    }

    private void mock(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier supplier = MockVisualizationInstSymbolTable::new;
        ret.put("Mock", supplier);
    }
}
