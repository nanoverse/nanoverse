/*
 *  Copyright (c) 2014 David Bruce Borenstein and the Trustees of
 *  Princeton University. All rights reserved.
 */

/*
 *  Copyright (c) 2014 David Bruce Borenstein and the Trustees of
 *  Princeton University. All rights reserved.
 */

package nanoverse.runtime.factory.geometry.set;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.factory.control.identifiers.CoordinateFactory;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.set.HorizontalLineSet;
import org.dom4j.Element;
import nanoverse.runtime.structural.utilities.XmlUtil;

/**
 * Created by dbborens on 7/28/14.
 */
public abstract class HorizontalLineSetFactory {
    public static HorizontalLineSet instantiate(Element e, Geometry geom) {
        int length = XmlUtil.getInteger(e, "length", 0);
        Coordinate start = getStart(e, geom);
        return new HorizontalLineSet(geom, start, length);
    }

    private static Coordinate getStart(Element e, Geometry geom) {
        Element offsetElem = e.element("offset");

        if (offsetElem == null) {
            return geom.getZeroVector();
        }
        return CoordinateFactory.instantiate(offsetElem);
    }
}
