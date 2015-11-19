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

package nanoverse.compiler.pipeline.translate.symbol.agent;

import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.runtime.control.arguments.AgentDescriptor;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 7/22/2015.
 */
public class AgentDescriptorClassSymbolTable extends ClassSymbolTable<AgentDescriptor> {

    @Override
    public String getDescription() {
        return "AgentDescriptor describes the properties of a class of agents," +
            " such as behaviors and internal state.";
    }

    @Override
    public HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        HashMap<String, Supplier<InstantiableSymbolTable>> ret = new HashMap<>();
        agent(ret);
        return ret;
    }

    private void agent(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = AgentDescriptorInstSymbolTable::new;
        ret.put("Agent", supplier);
    }
}
