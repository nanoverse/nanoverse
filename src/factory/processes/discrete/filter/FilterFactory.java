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

package factory.processes.discrete.filter;

import control.GeneralParameters;
import control.arguments.Argument;
import factory.control.arguments.IntegerArgumentFactory;
import layers.LayerManager;
import org.dom4j.Element;
import processes.discrete.filter.*;

/**
 * Created by dbborens on 5/5/14.
 */
public abstract class FilterFactory {

    private static Filter singleton(Element e, LayerManager layerManager, GeneralParameters p) {
        String name = e.getName();

        if (name.equalsIgnoreCase("null")) {
            return new NullFilter();
        } else if (name.equalsIgnoreCase("composite")) {
            return composite(e, layerManager, p);
        } else if (name.equalsIgnoreCase("cell-state")) {
            return cellStateFilter(e, layerManager, p);
        } else if (name.equalsIgnoreCase("depth")) {
            return depthFilter(e, layerManager, p);
        } else {
            String msg = "Unrecognized filter '" + name + "'.";
            throw new IllegalArgumentException(msg);
        }
    }

    public static Filter instantiate(Element e, LayerManager layerManager, GeneralParameters p) {
        // No arguments? No problem; we take it to mean the the user didn't
        // want any filters. Similarly, if there are no child elements, then
        // the default filter is applied.
        if (e == null || e.elements() == null) {
            return instantiateDefault();
        }

        int nChildren = e.elements().size();

        if (nChildren == 0) {
            return new NullFilter();
        } else if (nChildren == 1) {
            Object child = e.elements().iterator().next();
            return singleton((Element) child, layerManager, p);
        } else {
            return composite(e, layerManager, p);
        }
    }

    private static Filter instantiateDefault() {
        return new NullFilter();
    }

    private static Filter composite(Element e, LayerManager layerManager, GeneralParameters p) {
        int nChildren = e.elements().size();
        Filter[] children = new Filter[nChildren];

        int i = 0;
        for (Object o : e.elements()) {
            Filter child = singleton((Element) o, layerManager, p);
            children[i] = child;
            i++;
        }

        CompositeFilter ret = new CompositeFilter(children);
        return ret;
    }

    private static Filter cellStateFilter(Element e, LayerManager layerManager, GeneralParameters p) {
        Argument<Integer> toChoose = IntegerArgumentFactory.instantiate(e, "state", p.getRandom());
        CellStateFilter ret = new CellStateFilter(layerManager.getCellLayer(), toChoose);
        return ret;
    }

    private static Filter depthFilter(Element e, LayerManager layerManager, GeneralParameters p) {
        Argument<Integer> maxDepth = IntegerArgumentFactory.instantiate(e, "max-depth", 1, p.getRandom());
        DepthFilter ret = new DepthFilter(layerManager.getCellLayer(), maxDepth);
        return ret;
    }
}
