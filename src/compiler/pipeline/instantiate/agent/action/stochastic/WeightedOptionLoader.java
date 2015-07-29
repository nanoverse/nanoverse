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

package compiler.pipeline.instantiate.agent.action.stochastic;

import agent.action.*;
import agent.action.stochastic.WeightedOption;
import compiler.pipeline.instantiate.*;
import compiler.pipeline.translate.nodes.*;
import compiler.pipeline.translate.symbol.*;
import compiler.pipeline.translate.symbol.agent.action.NullActionInstSymbolTable;
import compiler.pipeline.translate.symbol.primitive.PrimitiveSymbolTable;
import compiler.pipeline.translate.symbol.primitive.doubles.PrimitiveDoubleSymbolTable;
import control.arguments.DoubleArgument;
import control.halt.HaltCondition;

/**
 * Created by dbborens on 7/29/2015.
 */
public class WeightedOptionLoader extends Loader<WeightedOption>  {

    public WeightedOptionLoader(ObjectNode node) {
        super(node);
    }

    public WeightedOptionLoader(ObjectNode node, Factory<WeightedOption> factory) {
        super(node, factory);
    }

    @Override
    protected WeightedOption doInstantiate() {
        MapObjectNode mNode = (MapObjectNode) node;
        action(mNode);
        weight(mNode);
        return factory.build();
    }

    private void weight(MapObjectNode node) {
        PrimitiveSymbolTable stDefault = new PrimitiveDoubleSymbolTable();
        PrimitiveObjectNode mDefault = new PrimitiveDoubleNode(stDefault, 1.0);
        ObjectNode weightNode = getObjectWithDefault(node, "weight", mDefault);
        Loader<? extends DoubleArgument> weightLoader = weightNode
                .getSymbolTable()
                .getLoader(weightNode);

        double weight;
        try {
            weight = weightLoader.instantiate().next();
        } catch (HaltCondition haltCondition) {
            throw new IllegalStateException();
        }

        ((WeightedOptionFactory) factory).setWeight(weight);
    }

    // TODO Create helpers for things like the following--try to get them to one line
    private void action(MapObjectNode mNode) {
        MapSymbolTable stDefault = new NullActionInstSymbolTable();
        MapObjectNode mDefault = new MapObjectNode(stDefault);
        ObjectNode actionNode = getObjectWithDefault(mNode, "action", mDefault);
        Loader<? extends ActionDescriptor> actionLoader = actionNode
                .getSymbolTable()
                .getLoader(actionNode);
        ActionDescriptor actionDescriptor = actionLoader.instantiate();
        ((WeightedOptionFactory) factory).setAction(actionDescriptor);
    }

    @Override
    protected WeightedOptionFactory resolveFactory() {
        return new WeightedOptionFactory();
    }
}
