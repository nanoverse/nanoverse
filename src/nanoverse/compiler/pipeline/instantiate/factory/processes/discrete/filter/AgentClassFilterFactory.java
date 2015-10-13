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
package nanoverse.compiler.pipeline.instantiate.factory.processes.discrete.filter;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.processes.discrete.filter.AgentClassFilter;

public class AgentClassFilterFactory implements Factory<AgentClassFilter> {

    private final AgentClassFilterFactoryHelper helper;

    private AgentLayer layer;
    private IntegerArgument toChoose;

    public AgentClassFilterFactory() {
        helper = new AgentClassFilterFactoryHelper();
    }

    public AgentClassFilterFactory(AgentClassFilterFactoryHelper helper) {
        this.helper = helper;
    }

    public void setLayer(AgentLayer layer) {
        this.layer = layer;
    }

    public void setToChoose(IntegerArgument toChoose) {
        this.toChoose = toChoose;
    }

    @Override
    public AgentClassFilter build() {
        return helper.build(layer, toChoose);
    }
}