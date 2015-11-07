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
package nanoverse.compiler.pipeline.instantiate.factory.io.visual.color;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.io.visual.color.IndexedColorModel;

public class IndexedColorModelFactory implements Factory<IndexedColorModel> {

    private final IndexedColorModelFactoryHelper helper;


    public IndexedColorModelFactory() {
        helper = new IndexedColorModelFactoryHelper();
    }

    public IndexedColorModelFactory(IndexedColorModelFactoryHelper helper) {
        this.helper = helper;
    }


    @Override
    public IndexedColorModel build() {
        return helper.build();
    }
}