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
package nanoverse.compiler.pipeline.instantiate.factory.control.identifiers;

import nanoverse.runtime.control.identifiers.Coordinate1D;
import nanoverse.compiler.pipeline.instantiate.factory.Factory;

public class Coordinate1DFactory implements Factory<Coordinate1D> {

    private final Coordinate1DFactoryHelper helper;

    private int y;
    private int flags;

    public Coordinate1DFactory() {
        helper = new Coordinate1DFactoryHelper();
    }

    public Coordinate1DFactory(Coordinate1DFactoryHelper helper) {
        this.helper = helper;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    @Override
    public Coordinate1D build() {
        return helper.build(y, flags);
    }
}