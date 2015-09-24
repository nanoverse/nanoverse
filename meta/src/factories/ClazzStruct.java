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

package factories;

/**
 * Created by dbborens on 8/6/15.
 */
public class ClazzStruct {

    private final String canonical;
    private final String lower;
    private final String upper;
    private final String pkg;
    private final String simple;

    public ClazzStruct(String canonical, String lower, String upper, String pkg, String simple) {
        this.canonical = canonical;
        this.simple = simple;
        this.lower = lower;
        this.upper = upper;
        this.pkg = pkg;
    }

    public String getCanonical() {
        return canonical;
    }

    public String getLower() {
        return lower;
    }

    public String getUpper() {
        return upper;
    }

    public String getSimple() {
        return simple;
    }

    public String getPkg() {
        return pkg;
    }
}
