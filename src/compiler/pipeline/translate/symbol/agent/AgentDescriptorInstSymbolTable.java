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

package compiler.pipeline.translate.symbol.agent;

import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.instantiate.loader.agent.AgentDescriptorLoader;
import compiler.pipeline.instantiate.loader.layers.continuum.ReactionStreamLoader;
import compiler.pipeline.translate.nodes.ObjectNode;
import compiler.pipeline.translate.symbol.*;
import compiler.pipeline.translate.symbol.agent.action.ActionClassSymbolTable;
import compiler.pipeline.translate.symbol.layers.continuum.ReactionClassSymbolTable;
import compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import compiler.pipeline.translate.symbol.primitive.integers.IntegerClassSymbolTable;
import control.arguments.CellDescriptor;

import java.util.HashMap;

/**
 * Created by dbborens on 7/22/2015.
 */
public class AgentDescriptorInstSymbolTable extends MapSymbolTable<CellDescriptor> {
    @Override
    public String getDescription() {
        return "AgentDescriptor describes the properties of a class of agents," +
                " such as behaviors and internal state.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        cellState(ret);
        threshold(ret);
        initialHealth(ret);
        reactions(ret);
        behaviors(ret);
        return ret;
    }

    private void behaviors(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new ActionClassSymbolTable();
        ResolvingSymbolTable rst = new DictionarySymbolTable<>(cst);
        MemberSymbol ms = new MemberSymbol(rst, "List of named behaviors and their corresponding action sequences.");
        ret.put("behaviors", ms);
    }

    private void reactions(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new ReactionClassSymbolTable();
        ResolvingSymbolTable rst = new ListSymbolTable<>(cst, ReactionStreamLoader::new);
        MemberSymbol ms = new MemberSymbol(rst, "List of interactions, if " +
                "any, between the agents being described and continuum " +
                "layers.");
        ret.put("reactions", ms);
    }

    private void initialHealth(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(cst, "LEGACY: Defines the initial " +
                "health value of the agent.");
        ret.put("initialHealth", ms);
    }

    private void threshold(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(cst, "LEGACY: the minimum health " +
                "at which an agent becomes eligible for cell division.");
        ret.put("threshold", ms);
    }

    private void cellState(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(cst, "A numerical ID for the " +
                "category of agent being described. Soon to be replaced...");
        ret.put("class", ms);
    }

    @Override
    public Loader getLoader() {
        return new AgentDescriptorLoader();
    }
}
