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

package factory.geometry.set;

import control.identifiers.Coordinate;
import factory.control.identifiers.CoordinateFactory;
import geometry.Geometry;
import geometry.set.CustomSet;
import org.dom4j.Element;

/**
 * Created by dbborens on 7/28/14.
 */
public abstract class CustomSetFactory {

    public static CustomSet instantiate(Element e, Geometry geom) {
        CustomSet ret = new CustomSet();
        for (Object o : e.elements()) {
            processElement(ret, geom, (Element) o);
        }

        return ret;
    }

    private static void processElement(CustomSet ret, Geometry geom, Element e) {
        if (e.getName().equalsIgnoreCase("coordinate")) {
            Coordinate c = CoordinateFactory.instantiate(e);
            ret.add(c);
        } else if (e.getName().equalsIgnoreCase("offset")) {
            Coordinate c = CoordinateFactory.offset(e, geom);
            ret.add(c);
        } else {
            throw new IllegalArgumentException("Unrecognized argument '" + e.getName() + "' in <custom-set>");
        }
    }
}
