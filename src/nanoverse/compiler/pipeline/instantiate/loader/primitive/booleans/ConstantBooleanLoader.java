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

package nanoverse.compiler.pipeline.instantiate.loader.primitive.booleans;

import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.runtime.control.arguments.ConstantBoolean;

import java.util.Random;

/**
 * Created by dbborens on 8/14/2015.
 */
public class ConstantBooleanLoader extends BooleanArgumentLoader<ConstantBoolean> {
    @Override
    public ConstantBoolean instantiate(ObjectNode node, Random random) {
        Boolean value = getValue(node);
        return new ConstantBoolean(value);
    }

    @Override
    public Boolean instantiateToFirst(ObjectNode node, Random random) {
        Boolean value = getValue(node);
        return value;
    }

    private Boolean getValue(ObjectNode node) {
        PrimitiveObjectNode<Boolean> pNode = (PrimitiveObjectNode) node;
        Boolean value = pNode.getValue();
        return value;
    }
}
