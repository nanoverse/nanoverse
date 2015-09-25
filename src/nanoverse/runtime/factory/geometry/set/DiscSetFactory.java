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

package nanoverse.runtime.factory.geometry.set;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.factory.control.arguments.IntegerArgumentFactory;
import nanoverse.runtime.factory.control.identifiers.CoordinateFactory;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.set.DiscSet;
import org.dom4j.Element;

/**
 * Created by dbborens on 7/28/14.
 */
public abstract class DiscSetFactory {
    public static DiscSet instantiate(Element e, Geometry geom, GeneralParameters p) {
        IntegerArgument radiusArg = IntegerArgumentFactory.instantiate(e, "radius", 1, p.getRandom());
        Coordinate offset = getOffset(e, geom);
        return new DiscSet(geom, radiusArg, offset);
    }

    private static Coordinate getOffset(Element e, Geometry geom) {
        Element offsetElem = e.element("offset");

        if (offsetElem == null) {
            return geom.getZeroVector();
        }
        return CoordinateFactory.instantiate(offsetElem);
    }
}
