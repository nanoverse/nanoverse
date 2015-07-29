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

package compiler.pipeline.translate.symbol.geometry.boundary;

import compiler.pipeline.instantiate.Loader;
import compiler.pipeline.translate.nodes.ObjectNode;
import compiler.pipeline.translate.symbol.MapSymbolTable;
import geometry.boundaries.TetrisBoundary;

/**
 * Created by dbborens on 7/29/2015.
 */
public class TetrisBoundaryInstSymbolTable extends MapSymbolTable<TetrisBoundary> {
    @Override
    public String getDescription() {
        return "The Tetris boundary is a 2D boundary for AGENT layers only. " +
                "It has a hard boundary on the bottom edge, a halting " +
                "boundary on the top edge, and periodic boundaries on the " +
                "left and right edges.";
    }

    @Override
    public Loader getLoader(ObjectNode node) {
        return null;
    }
}
