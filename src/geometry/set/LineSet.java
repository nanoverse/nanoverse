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

package geometry.set;

/**
 * Created by dbborens on 7/28/14.
 */
public class LineSet extends CoordinateSet {
    public LineSet() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // OLD CODE: DO NOT USE
//    protected Coordinate[] coordinateLine(Element siteElement) {
//        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
//
//        Element originElem = siteElement.element("origin");
//        Element displacementElem = siteElement.element("displacement");
//
//        int x0 = Integer.valueOf(originElem.attribute("x").getValue());
//        int y0 = Integer.valueOf(originElem.attribute("y").getValue());
//
//        int du = Integer.valueOf(displacementElem.attribute("u").getValue());
//        int dv = Integer.valueOf(displacementElem.attribute("v").getValue());
//
//        // Remember that 2D triangular layerManager.getCellLayer().getGeometry().tries have a third, non-orthogonal
//        // basis vector.
//        int dw = 0;
//        if (displacementElem.attribute("w") != null) {
//            dw = Integer.valueOf(displacementElem.attribute("w").getValue());
//        }
//
//
//        int length = Integer.valueOf(siteElement.element("length").getText());
//
//        Coordinate c;
//        if (siteElement.attribute("z") != null) {
//            int z0 = Integer.valueOf(originElem.element("z").getText());
//            c = new Coordinate(x0, y0, z0, 0);
//        } else {
//            c = new Coordinate(x0, y0, 0);
//        }
//
//        coordinates.add(c);
//        int[] dArr = new int[]{du, dv, dw};
//
//        Coordinate d = new Coordinate(dArr, c.flags() | Flags.VECTOR);
//
//        for (int i = 0; i < length; i++) {
//            Coordinate cNext = layerManager.getCellLayer().getGeometry().rel2abs(c, d, Geometry.APPLY_BOUNDARIES);
//            c = cNext;
//            coordinates.add(c);
//        }
//
//        return coordinates.toArray(new Coordinate[0]);
//    }
}
