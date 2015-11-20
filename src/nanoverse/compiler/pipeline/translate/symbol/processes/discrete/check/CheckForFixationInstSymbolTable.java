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

package nanoverse.compiler.pipeline.translate.symbol.processes.discrete.check;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.check.CheckForFixationLoader;
import nanoverse.compiler.pipeline.translate.symbol.MemberSymbol;
import nanoverse.compiler.pipeline.translate.symbol.processes.discrete.DiscreteProcessInstSymbolTable;
import nanoverse.runtime.processes.discrete.check.CheckForFixation;

import java.util.HashMap;

/**
 * Created by dbborens on 7/21/2015.
 */
public class CheckForFixationInstSymbolTable extends DiscreteProcessInstSymbolTable<CheckForFixation> {
    @Override
    public String getDescription() {
        return "Halt the simulation if only one type of nanoverse.runtime.agent exists.";
    }

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        return super.resolveMembers();
    }

    @Override
    public Loader getLoader() {
        return new CheckForFixationLoader();
    }
}
