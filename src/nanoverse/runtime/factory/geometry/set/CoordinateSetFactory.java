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
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.set.CompleteSet;
import nanoverse.runtime.geometry.set.CoordinateSet;
import org.dom4j.Element;

import java.util.List;

/**
 * Created by dbborens on 7/28/14.
 */
public abstract class CoordinateSetFactory {
    public static CoordinateSet instantiate(Element e, Geometry geom, GeneralParameters p) {
        // Null = complete set (no restriction)
        if (e == null) {
            return new CompleteSet(geom);
        }

        Element descriptor = getDescriptor(e);
        String name = descriptor.getName();

        if (name.equalsIgnoreCase("all")) {
            return new CompleteSet(geom);
        } else if (name.equals("disc")) {
            return DiscSetFactory.instantiate(descriptor, geom, p);
        } else if (name.equals("list")) {
            return CustomSetFactory.instantiate(descriptor, geom);
        } else if (name.equals("horizontal-line")) {
            return HorizontalLineSetFactory.instantiate(descriptor, geom);
        } else if (name.equals("rectangle")) {
            throw new UnsupportedOperationException("Coordinate set 'rectangle' has been disabled until it is re-implemented in standard Factory logic and tested thoroughly.");
        } else {
            throw new IllegalArgumentException("Unrecognized coordinate set '" + name + "'");
        }
    }

    private static Element getDescriptor(Element e) {
        List<Object> children = e.elements();
        if (children.size() != 1) {
            throw new IllegalStateException("Expect single child (descriptor) for coordinate set definition, but got " + children.size() + ".");
        }

        return (Element) children.iterator().next();
    }
}
