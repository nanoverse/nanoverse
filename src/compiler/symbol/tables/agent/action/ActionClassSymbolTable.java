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

package compiler.symbol.tables.agent.action;

import compiler.symbol.tables.*;
import agent.action.ActionDescriptor;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 7/22/2015.
 */
public class ActionClassSymbolTable extends ClassSymbolTable<ActionDescriptor> {
    @Override
    public String getDescription() {
        return "Actions are local events defined with respect to a " +
                "particular agent.";
    }

    @Override
    protected HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        HashMap<String, Supplier<InstantiableSymbolTable>> ret = new HashMap<>();
        mock(ret);
        die(ret);
        adjustHealth(ret);
        trigger(ret);
        cloneTo(ret);
        expand(ret);
        expandTo(ret);
        expandRandom(ret);
        expandWeighted(ret);
        stochasticChoice(ret);
        swap(ret);
        inject(ret);
        thresholdDo(ret);
        nullAction(ret);
        return ret;
    }

    private void nullAction(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {

    }

    private void thresholdDo(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {

    }

    private void inject(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {

    }

    private void swap(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {

    }

    private void stochasticChoice(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {

    }

    private void expandWeighted(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {

    }

    private void expandRandom(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {

    }

    private void expandTo(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {

    }

    private void expand(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {

    }

    private void cloneTo(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {

    }

    private void trigger(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {

    }

    private void adjustHealth(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {

    }

    private void die(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {

    }

    private void mock(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {

    }

}
