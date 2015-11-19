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

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.agent.*;
import nanoverse.compiler.pipeline.instantiate.loader.layers.continuum.ReactionStreamLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.agent.action.ActionClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.layers.continuum.ReactionClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.integers.IntegerClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import nanoverse.runtime.control.arguments.AgentDescriptor;

import java.util.HashMap;

/**
 * Created by dbborens on 7/22/2015.
 */
public class AgentDescriptorInstSymbolTable extends MapSymbolTable<AgentDescriptor> {

    @Override
    public String getDescription() {
        return "AgentDescriptor describes the properties of a class of agents," +
            " such as behaviors and internal state.";
    }

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        name(ret);
        threshold(ret);
        initialHealth(ret);
        reactions(ret);
        behaviors(ret);
        return ret;
    }

    private void behaviors(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new ActionClassSymbolTable();
        ResolvingSymbolTable rst = new DictionarySymbolTable<>(cst, BehaviorMapLoader::new);
        MemberSymbol ms = new MemberSymbol(rst, "List of named behaviors and their corresponding action sequences.");
        ret.put("behaviors", ms);
    }

    private void reactions(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new ReactionClassSymbolTable();
        ResolvingSymbolTable rst = new ListSymbolTable<>(cst, ReactionStreamLoader::new);
        MemberSymbol ms = new MemberSymbol(rst, "List of interactions, if " +
            "any, between the agents being described and continuum " +
            "nanoverse.runtime.layers.");
        ret.put("reactions", ms);
    }

    private void initialHealth(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(cst, "LEGACY: Defines the initial " +
            "health value of the nanoverse.runtime.agent.");
        ret.put("initialHealth", ms);
    }

    private void threshold(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(cst, "LEGACY: the minimum health " +
            "at which an nanoverse.runtime.agent becomes eligible for cell division.");
        ret.put("threshold", ms);
    }

    private void name(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(cst, "A textual name or " +
            "description for the agent being described.");
        ret.put("name", ms);
    }

    @Override
    public Loader getLoader() {
        return new AgentDescriptorLoader();
    }
}
