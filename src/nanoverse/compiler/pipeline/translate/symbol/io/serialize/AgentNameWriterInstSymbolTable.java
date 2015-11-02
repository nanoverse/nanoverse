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

package nanoverse.compiler.pipeline.translate.symbol.io.serialize;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.io.serialize.binary.AgentNameWriterLoader;
import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.runtime.io.serialize.binary.AgentNameWriter;

/**
 * Created by dbborens on 7/26/2015.
 */
public class AgentNameWriterInstSymbolTable extends MapSymbolTable<AgentNameWriter> {
    @Override
    public String getDescription() {
        return "Maps each observed agent name to a unique numeric ID. Emits " +
            "two files: a binary file containing vectors of name IDs by " +
            "time, and a text file providing a mapping between numeric IDs " +
            "and text names. Used, eg, for generating visualizations.";
    }

    @Override
    public Loader getLoader() {
        return new AgentNameWriterLoader();
    }
}
