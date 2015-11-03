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

package nanoverse.runtime.geometry.boundary;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.boundaries.helpers.WrapHelper;

/**
 * Created by dbborens on 5/10/14.
 */
public class MockWrapHelper extends WrapHelper {

    private boolean allWrapped, xWrapped, yWrapped, zWrapped;

    public MockWrapHelper() {
        super(null, null);

        allWrapped = false;
        xWrapped = false;
        yWrapped = false;
        zWrapped = false;
    }

    public boolean isAllWrapped() {
        return allWrapped;
    }

    public boolean isxWrapped() {
        return xWrapped;
    }

    public boolean isyWrapped() {
        return yWrapped;
    }

    public boolean iszWrapped() {
        return zWrapped;
    }

    @Override
    public Coordinate wrapAll(Coordinate toWrap) {
        allWrapped = true;
        return null;
    }

    @Override
    public Coordinate xWrap(Coordinate toWrap) {
        xWrapped = true;
        return null;
    }

    @Override
    public Coordinate yWrap(Coordinate toWrap) {
        yWrapped = true;
        return null;
    }

    @Override
    public Coordinate zWrap(Coordinate toWrap) {
        zWrapped = true;
        return null;
    }

    @Override
    protected void checkValid(Coordinate toWrap) {
    }
}
