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
package compiler.pipeline.instantiate.factory.processes.discrete.filter;

import control.arguments.Argument;
import control.arguments.IntegerArgument;
import layers.cell.CellLayer;
import processes.discrete.filter.CellStateFilter;
import compiler.pipeline.instantiate.factory.Factory;

public class CellStateFilterFactory implements Factory<CellStateFilter> {

    private final CellStateFilterFactoryHelper helper;

    private CellLayer layer;
    private IntegerArgument toChoose;

    public CellStateFilterFactory() {
        helper = new CellStateFilterFactoryHelper();
    }

    public CellStateFilterFactory(CellStateFilterFactoryHelper helper) {
        this.helper = helper;
    }

    public void setLayer(CellLayer layer) {
        this.layer = layer;
    }

    public void setToChoose(IntegerArgument toChoose) {
        this.toChoose = toChoose;
    }

    @Override
    public CellStateFilter build() {
        return helper.build(layer, toChoose);
    }
}