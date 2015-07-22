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

package compiler.symbol.tables.processes.discrete.cluster;

import compiler.symbol.tables.*;
import processes.discrete.cluster.ScatterClustersHelper;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 7/22/2015.
 */
public class ScatterClustersHelperClassSymbolTable extends ClassSymbolTable<ScatterClustersHelper> {
    @Override
    public String getDescription() {
        return "Defines the manner in which cell clusters should " +
                "be separated when using semi-structured scatter rules.";
    }

    @Override
    protected HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        return null;
    }
}
