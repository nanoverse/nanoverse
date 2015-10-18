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

package nanoverse.compiler.pipeline.translate.symbol.processes.discrete.check;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.check.CheckForExtinctionLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.processes.discrete.DiscreteProcessInstSymbolTable;
import nanoverse.runtime.processes.discrete.check.CheckForExtinction;

import java.util.HashMap;

/**
 * Created by dbborens on 7/21/2015.
 */
public class CheckForExtinctionInstSymbolTable extends DiscreteProcessInstSymbolTable<CheckForExtinction> {
    @Override
    public String getDescription() {
        return "Halt simulation if all sites are vacant.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        threshold(ret);
        return ret;
    }

    private void threshold(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Minimum occupancy before halt is triggered.");
        ret.put("threshold", ms);
    }

    @Override
    public Loader getLoader() {
        return new CheckForExtinctionLoader();
    }
}