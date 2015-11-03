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

package nanoverse.compiler.pipeline.translate.symbol.io.serialize;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.io.serialize.text.CensusWriterLoader;
import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.runtime.io.serialize.text.CensusWriter;

/**
 * Created by dbborens on 7/26/2015.
 */
public class CensusWriterInstSymbolTable extends MapSymbolTable<CensusWriter> {
    @Override
    public String getDescription() {
        return "Census writer produces one tab-delimited file per simulation " +
            "instance. Each row in the file corresponds to a time point, " +
            "and the columns capture the number of agents of a given " +
            "state (class) observed at that time. There is one column " +
            "for each state observed through the course of the simulation.";
    }

    @Override
    public Loader getLoader() {
        return new CensusWriterLoader();
    }
}
