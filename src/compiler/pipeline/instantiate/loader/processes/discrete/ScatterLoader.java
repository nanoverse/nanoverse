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

package compiler.pipeline.instantiate.loader.processes.discrete;

import compiler.pipeline.instantiate.factory.processes.discrete.*;
import compiler.pipeline.instantiate.loader.processes.ProcessLoader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import control.arguments.*;
import layers.LayerManager;
import processes.BaseProcessArguments;
import processes.discrete.*;
import processes.discrete.cluster.ScatterClustersHelper;

/**
 * Created by dbborens on 8/3/2015.
 */
public class ScatterLoader extends ProcessLoader<Scatter> {
    private final ScatterFactory factory;
    private final ScatterInterpolator interpolator;

    public ScatterLoader() {
        factory = new ScatterFactory();
        interpolator = new ScatterInterpolator();
    }

    public ScatterLoader(ScatterFactory factory,
                                 ScatterInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public Scatter instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        BaseProcessArguments arguments = interpolator.arguments(node, lm, p);
        factory.setArguments(arguments);

        CellProcessArguments cpArguments = interpolator.cpArguments(node, lm, p);
        factory.setCpArguments(cpArguments);

        CellDescriptor description = interpolator.description(node, lm, p);
        factory.setCellDescriptor(description);

        return factory.build();
    }
}
