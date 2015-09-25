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

package nanoverse.runtime.geometry.boundaries;

import nanoverse.runtime.geometry.boundary.ArenaTest;
import nanoverse.runtime.geometry.lattice.Lattice;
import nanoverse.runtime.geometry.shape.Shape;

public class HaltArenaTest extends ArenaTest {
    @Override
    protected Boundary makeBoundary(Shape shape, Lattice lattice) {
        return new HaltArena(shape, lattice);
    }
}