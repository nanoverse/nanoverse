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
package nanoverse.compiler.pipeline.instantiate.factory.io.serialize.text;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.io.serialize.text.CoordinateIndexer;
import nanoverse.compiler.pipeline.instantiate.factory.Factory;

public class CoordinateIndexerFactory implements Factory<CoordinateIndexer> {

    private final CoordinateIndexerFactoryHelper helper;

    private GeneralParameters p;
    private LayerManager lm;

    public CoordinateIndexerFactory() {
        helper = new CoordinateIndexerFactoryHelper();
    }

    public CoordinateIndexerFactory(CoordinateIndexerFactoryHelper helper) {
        this.helper = helper;
    }

    public void setP(GeneralParameters p) {
        this.p = p;
    }

    public void setLm(LayerManager lm) {
        this.lm = lm;
    }

    @Override
    public CoordinateIndexer build() {
        return helper.build(p, lm);
    }
}