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

import processes.discrete.filter.CompositeFilter;
import compiler.pipeline.instantiate.factory.Factory;
import processes.discrete.filter.Filter;

import java.util.stream.Stream;

public class CompositeFilterFactory implements Factory<CompositeFilter> {

    private final CompositeFilterFactoryHelper helper;

    private Stream<Filter> children;

    public CompositeFilterFactory() {
        helper = new CompositeFilterFactoryHelper();
    }

    public CompositeFilterFactory(CompositeFilterFactoryHelper helper) {
        this.helper = helper;
    }

    public void setChildren(Stream<Filter> children) {
        this.children = children;
    }

    @Override
    public CompositeFilter build() {
        return helper.build(children);
    }
}