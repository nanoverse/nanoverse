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

package control.identifiers;

public class Flags {

    /********************/
    /* COORDINATE FLAGS */
    /**
     * ****************
     */

	/* Type flags */

    // If "planar" is set, geometry is 2D; otherwise it is 3D.
    public static final int PLANAR = 1;


    // Indicates that this is a vector. Affects text representation.
    public static final int VECTOR = 1 << 1;

    // Indicates that boundary conditions have been applied.
    public static final int BOUNDARY_APPLIED = 1 << 2;
    public static final int BOUNDARY_IGNORED = 1 << 3;

    /* Undefined state flag */
    public static final int UNDEFINED = 1 << 29;

    /* Boundary flags */
    public static final int END_OF_WORLD = 1 << 30;
    public static final int BEYOND_BOUNDS = 1 << 31;

    // If "triangular" is set, geometry is triangular; otherwise it is rectangular.
    public static final int RESERVED4 = 1 << 4;
    public static final int RESERVED5 = 1 << 5;
    public static final int RESERVED6 = 1 << 6;
    // ...
    public static final int RESERVED29 = 1 << 28;

}
