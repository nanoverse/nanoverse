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

package nanoverse.runtime.factory.layers;

import nanoverse.runtime.control.arguments.GeometryDescriptor;
import nanoverse.runtime.factory.layers.cell.CellLayerFactory;
import nanoverse.runtime.factory.layers.continuum.ContinuumLayerFactory;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.CellLayer;
import org.dom4j.Element;

import java.util.List;

/**
 * Created by dbborens on 11/26/14.
 */
public abstract class LayerManagerFactory {

    public static LayerManager instantiate(Element root, GeometryDescriptor geometryDescriptor) {
        LayerManager ret = new LayerManager();
        // Build the Cell layer, if present
        if (hasCellElement(root)) {
            CellLayer cellLayer = buildCellLayer(root, geometryDescriptor);
            ret.setCellLayer(cellLayer);
        }

        addContinuumLayers(root, geometryDescriptor, ret);
        return ret;

    }

    private static void addContinuumLayers(Element root, GeometryDescriptor geometryDescriptor, LayerManager ret) {
        List<Object> clElems = root.elements("continuum-layer");

        clElems.stream()
                .map(o -> (Element) o)
                .map(e -> ContinuumLayerFactory.instantiate(e, geometryDescriptor))
                .forEach(ret::addContinuumLayer);
    }

    private static CellLayer buildCellLayer(Element layerRoot, GeometryDescriptor geometryDescriptor) {
        Element e = layerRoot.element("cell-layer");
        CellLayer instantiate = CellLayerFactory.instantiate(e, geometryDescriptor);
        return instantiate;
    }

    private static boolean hasCellElement(Element layerRoot) {
        List<Object> elems = layerRoot.elements("cell-layer");
        return (elems.size() > 0);
    }
}
