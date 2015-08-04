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

package factory.agent.targets;

import agent.targets.*;
import cells.BehaviorCell;
import control.GeneralParameters;
import agent.targets.TargetDescriptor;
import factory.processes.discrete.filter.FilterFactory;
import layers.LayerManager;
import org.dom4j.Element;
import processes.discrete.filter.Filter;
import processes.discrete.filter.NullFilter;

import java.util.function.Function;

/**
 * Created by dbborens on 2/10/14.
 */
public abstract class TargetDesciptorFactory {
    public static TargetDescriptor instantiate(LayerManager layerManager, Element descriptor, GeneralParameters p) {
        String targetName = getName(descriptor);
        int maximum = getMaximum(descriptor);
        Filter filter = getFilter(descriptor, layerManager, p);
        if (targetName.equalsIgnoreCase("all-neighbors")) {
            return new TargetAllNeighborsDescriptor(layerManager, filter, maximum, p.getRandom());
        } else if (targetName.equalsIgnoreCase("occupied-neighbors")) {
            return new TargetOccupiedNeighborsDescriptor(layerManager, filter, maximum, p.getRandom());
        } else if (targetName.equalsIgnoreCase("vacant-neighbors")) {
            return new TargetVacantNeighborsDescriptor(layerManager, filter, maximum, p.getRandom());
        } else if (targetName.equalsIgnoreCase("caller")) {
            return new TargetCallerDescriptor(layerManager, filter, maximum, p.getRandom());
        } else if (targetName.equalsIgnoreCase("self")) {
            return new TargetSelfDescriptor(layerManager, filter, maximum, p.getRandom());
        } else {
            throw new IllegalArgumentException("Unrecognized target '" + targetName + "'");
        }
    }

    private static Filter getFilter(Element descriptor, LayerManager layerManager, GeneralParameters p) {
        Element filterElem = descriptor.element("filters");
        if (filterElem == null) {
            return new NullFilter();
        }

        return FilterFactory.instantiate(filterElem, layerManager, p);
    }

    private static int getMaximum(Element descriptor) {
        Element maxElement = descriptor.element("max");

        // Default is no maximum
        if (maxElement == null) {
            return -1;
        }

        String maxStr = maxElement.getTextTrim();
        int maximum = Integer.valueOf(maxStr);
        return maximum;
    }

    private static String getName(Element descriptor) {
        Element classNameElement = descriptor.element("class");
        String name = classNameElement.getTextTrim();

        return name;
    }
}
