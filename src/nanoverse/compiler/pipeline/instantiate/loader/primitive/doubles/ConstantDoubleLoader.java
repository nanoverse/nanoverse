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

package nanoverse.compiler.pipeline.instantiate.loader.primitive.doubles;

import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.runtime.control.arguments.ConstantDouble;

import java.util.Random;

/**
 * Created by dbborens on 8/14/2015.
 */
public class ConstantDoubleLoader extends DoubleArgumentLoader<ConstantDouble> {
    @Override
    public ConstantDouble instantiate(ObjectNode node, Random random) {
        Double value = getValue(node);
        return new ConstantDouble(value);
    }

    @Override
    public Double instantiateToFirst(ObjectNode node, Random random) {
        Double value = getValue(node);
        return value;
    }

    private Double getValue(ObjectNode node) {
        PrimitiveObjectNode<Double> pNode = (PrimitiveObjectNode) node;
        Double value = pNode.getValue();
        return value;
    }
}
