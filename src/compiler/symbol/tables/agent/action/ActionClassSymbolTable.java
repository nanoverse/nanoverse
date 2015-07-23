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

import agent.action.*;
import compiler.symbol.tables.*;

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
        Supplier<InstantiableSymbolTable> supplier = NullActionInstSymbolTable::new;
        ret.put("Null", supplier);
    }

    private void thresholdDo(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = ThresholdDoInstSymbolTable::new;
        ret.put("ThresholdDo", supplier);
    }

    private void inject(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = InjectInstSymbolTable::new;
        ret.put("Inject", supplier);
    }

    private void swap(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = SwapInstSymbolTable::new;
        ret.put("Swap", supplier);
    }

    private void stochasticChoice(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = StochasticChoiceInstSymbolTable::new;
        ret.put("StochasticChoice", supplier);
    }

    private void expandWeighted(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = ExpandWeightedInstSymbolTable::new;
        ret.put("ExpandWeighted", supplier);
    }

    private void expandRandom(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = ExpandRandomInstSymbolTable::new;
        ret.put("ExpandRandom", supplier);
    }

    private void expandTo(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = ExpandToInstSymbolTable::new;
        ret.put("ExpandTo", supplier);
    }

    private void expand(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = ExpandInstSymbolTable::new;
        ret.put("Expand", supplier);
    }

    private void cloneTo(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = CloneToInstSymbolTable::new;
        ret.put("CloneTo", supplier);
    }

    private void trigger(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = TriggerActionInstSymbolTable::new;
        ret.put("Trigger", supplier);
    }

    private void adjustHealth(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = AdjustHealthInstSymbolTable::new;
        ret.put("AdjustHealth", supplier);
    }

    private void die(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = DieInstSymbolTable::new;
        ret.put("Die", supplier);
    }

    private void mock(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = MockActionInstSymbolTable::new;
        ret.put("Mock", supplier);
    }

}
