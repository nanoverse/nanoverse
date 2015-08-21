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

package compiler.pipeline.instantiate.loader.io.serialize.binary;

import compiler.pipeline.instantiate.loader.primitive.integers.IntegerArgumentLoader;
import compiler.pipeline.translate.nodes.*;
import compiler.pipeline.translate.symbol.InstantiableSymbolTable;
import control.GeneralParameters;
import control.arguments.IntegerArgument;

import java.util.*;
import java.util.stream.*;

/**
 * Created by dbborens on 8/21/2015.
 */
public class HighlightWriterChildLoader {

    public Stream<Integer> channels(MapObjectNode node, GeneralParameters p) {
        ListObjectNode lNode = (ListObjectNode) node.getMember("channels");

        Stream<Integer> ret = lNode.getMemberStream()
                .map(o -> (MapObjectNode) o)
                .map(cNode -> instantiate(cNode, p));

        return ret;
    }

    private Integer instantiate(MapObjectNode node, GeneralParameters p) {
        Random random = p.getRandom();
        InstantiableSymbolTable ist = node.getSymbolTable();
        IntegerArgumentLoader loader = (IntegerArgumentLoader) ist.getLoader();
        return loader.instantiateToFirst(node, random);
    }
}
