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

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.instantiate.loader.io.visual.highlight.IntegerStreamLoader;
import compiler.pipeline.translate.nodes.*;
import control.GeneralParameters;

import java.util.stream.Stream;

/**
 * Created by dbborens on 8/23/2015.
 */
public class HighlightWriterInterpolator {
    private final LoadHelper load;

    public HighlightWriterInterpolator() {
        load = new LoadHelper();
    }

    public HighlightWriterInterpolator(LoadHelper load) {
        this.load = load;
    }

    public Stream<Integer> channels(MapObjectNode node, GeneralParameters p) {
        IntegerStreamLoader loader = (IntegerStreamLoader) load.getLoader(node, "channels", true);
        ListObjectNode cNode = (ListObjectNode) node.getMember("channels");

        return loader.instantiate(cNode, p);
    }
}
